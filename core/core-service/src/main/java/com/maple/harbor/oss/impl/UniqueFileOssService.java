package com.maple.harbor.oss.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Maps;
import com.maple.harbor.authentication.core.mock.MockUserInfo;
import com.maple.harbor.authentication.core.mock.MockUserInfoContext;
import com.maple.harbor.dao.MoAccountFileRelationDao;
import com.maple.harbor.dao.MoFileDetailsDao;
import com.maple.harbor.entity.MoAccountFileRelation;
import com.maple.harbor.entity.MoFileDetails;
import com.maple.harbor.exception.BizException;
import com.maple.harbor.log.AroundLog;
import com.maple.harbor.oss.AbstractOssService;
import io.minio.ObjectWriteResponse;
import io.minio.StatObjectResponse;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 唯一文件的OssService
 * 秒传
 */
@Service("uniqueFileOssService")
public class UniqueFileOssService extends AbstractOssService {

    @Autowired
    private MoAccountFileRelationDao accountFileRelationDao;

    @Autowired
    private MoFileDetailsDao fileDetailsDao;

    @Override
    @AroundLog
    @Transactional
    public Boolean putObject(String abilityBasedFolder, MultipartFile multipartFile) {
        String ossFilePath = filename2OssPath(abilityBasedFolder, multipartFile.getOriginalFilename());
        MockUserInfo userInfo = MockUserInfoContext.getUserInfo();
        try {
            // 计算文件的md5
            String md5 = MD5.create().digestHex16(multipartFile.getInputStream());
            AtomicBoolean fileDetailsExists = new AtomicBoolean(true);
            MoFileDetails fileDetails = Optional.ofNullable(
                            fileDetailsDao.selectOne(new LambdaQueryWrapper<MoFileDetails>().eq(MoFileDetails::getFileMd5, md5)))
                                                .orElseGet(() -> {
                                                    fileDetailsExists.set(false);
                                                    return new MoFileDetails();
                                                });
            if (!fileDetailsExists.get()) {
                // 没有记录，上传 + 添加file表记录 + 添加用户文件表记录
                // 1.上传
                ObjectWriteResponse response = null;
                String etag = null;
                String versionId = null;
                StatObjectResponse metadata = ossTemplate.getObjectMetadata(getBucketName(), ossFilePath);
                // 判断oss服务器上是否有数据: 有数据则判断md5是否相等, 不相等则说明不是同一个文件, 进行上传
                if (Objects.isNull(metadata) || !md5.equals(metadata.userMetadata().get("md5"))) {
                    HashMap<String, String> userMetadata = Maps.newHashMap();
                    userMetadata.put("md5", md5);
                    response = ossTemplate.putObject(getBucketName(), ossFilePath, multipartFile.getInputStream(), userMetadata);
                    Assert.notNull(response, () -> BizException.commonFail("上传文件失败"));
                    etag = response.etag();
                    versionId = response.versionId();
                } else {
                    etag = metadata.etag();
                    versionId = metadata.versionId();
                }
                // 2.添加file表记录
                fileDetails = fileDetailsDao.selectOne(new LambdaQueryWrapper<MoFileDetails>().eq(MoFileDetails::getFileMd5, md5));
                if (Objects.isNull(fileDetails)) {
                    fileDetails.setFileEtag(etag);
                    fileDetails.setFileSize(multipartFile.getSize());
                    fileDetails.setFileMd5(md5);
                    fileDetails.setFilePath(ossFilePath);
                    fileDetails.setFileVersionId(versionId);
                    fileDetails.setBucketName(getBucketName());
                    fileDetails.setContentType(multipartFile.getContentType());
                    Assert.isTrue(fileDetailsDao.insert(fileDetails) > 0, () -> BizException.commonFail("插入 [fileDetails] 表失败"));
                } else {
                    throw BizException.commonFail("fileDetails 表已存在与 [" + fileDetails.getFilePath() + "] 相同的记录");
                }
            }
            // 用户文件表记录
            Long fileId = fileDetails.getId();
            Long accountId = userInfo.getAccountId();
            MoAccountFileRelation relation = accountFileRelationDao.selectOne(new LambdaQueryWrapper<MoAccountFileRelation>()
                                                                                      .eq(MoAccountFileRelation::getAccountId, accountId)
                                                                                      .eq(MoAccountFileRelation::getFileId, fileId));
            if (Objects.isNull(relation)) {
                relation = new MoAccountFileRelation();
                relation.setOssFilePath(ossFilePath);
                relation.setAccountId(accountId);
                relation.setFileId(fileId);
                relation.setUserDefineFilename(multipartFile.getName());
                relation.setOriginFilename(multipartFile.getOriginalFilename());
                relation.setModifierId(accountId);
                // todo 短链生成服务
                // relation.setShortLink();
                Assert.isTrue(accountFileRelationDao.insert(relation) > 0,
                        () -> BizException.commonFail("插入 [accountFileRelation] 表失败"));
            } else {
                throw BizException.commonFail("accountFileRelation 表已存在与 [" + relation.getOriginFilename() + "] 相同的记录");
            }
            return true;
        } catch (IOException e) {
            throw new BizException(e);
        }
    }

    @Override
    public OutputStream getObject() {
        return null;
    }

    @AroundLog
    @Override
    public String getObjectUrl(String abilityBasedFolder, String originFilename) {
        MoAccountFileRelation relation = accountFileRelationDao.selectOne(new LambdaQueryWrapper<MoAccountFileRelation>()
                                                                                  .eq(MoAccountFileRelation::getAccountId, MockUserInfoContext.getUserInfo().getAccountId())
                                                                                  .eq(MoAccountFileRelation::getOriginFilename, originFilename));
        MoFileDetails fileDetails = fileDetailsDao.selectById(relation.getFileId());
        String generateLink = fileDetails.getGenerateLink();
        if (StringUtils.isNotBlank(generateLink)) {
            return generateLink;
        }
        String url = ossTemplate.getObjectUrl(getBucketName(), fileDetails.getFilePath(), 7, TimeUnit.DAYS, fileDetails.getFileVersionId());
        fileDetails.setGenerateLink(url);
        fileDetails.setLinkDeadline(Date.from(LocalDateTime.now().plusDays(7).atZone(ZoneOffset.systemDefault()).toInstant()));
        Assert.isTrue(fileDetailsDao.updateById(fileDetails) > 0, () -> BizException.commonFail("刷新日期到期时间失败"));
        return url;
    }

    @AroundLog
    @Override
    public String getObjectMatedata() {
        return null;
    }

    @AroundLog
    @Override
    public List getObjectList() {
        return null;
    }

    @AroundLog
    @Override
    public Boolean deleteObject() {
        return null;
    }

    /**
     * filename 转变成 path
     * @param abilityBasedFolder 文件上传的路径
     * @param originFilename     原始文件名称
     * @return 合成后的路径，为 abilityBasedFolder + "/" +originFilename
     */
    private String filename2OssPath(String abilityBasedFolder, String originFilename) {
        if (StringUtils.isBlank(originFilename)) {
            return "NOT_DEFINE_FILE_" + System.currentTimeMillis();
        }
        if (StringUtils.isBlank(abilityBasedFolder)) {
            return originFilename;
        }
        Assert.isFalse(originFilename.contains("/"), () -> BizException.commonFail("上传的文件不能包含\"/\"!"));
        // 最后一位补斜杠
        if (abilityBasedFolder.lastIndexOf(abilityBasedFolder) != '/') {
            abilityBasedFolder += '/';
        }
        return abilityBasedFolder + originFilename;
    }

    /**
     * 存储路径转化为filename
     * @param ossFilePath 存储在oss中的文件名称
     * @return
     */
    private String ossPath2Filename(String ossFilePath) {
        if (StringUtils.isBlank(ossFilePath)) {
            return "NOT_DEFINE_FILE_" + System.currentTimeMillis();
        }
        if (!ossFilePath.contains("/")) {
            return ossFilePath;
        }
        return ossFilePath.substring(ossFilePath.lastIndexOf("/"), ossFilePath.length());
    }

    /**
     * 存储路径转化为filename
     * @param ossFilePath 存储在oss中的文件名称
     * @return
     */
    private String ossPath2ClientPath(String abilityBasedFolder, String ossFilePath) {
        if (StringUtils.isBlank(abilityBasedFolder)) {
            abilityBasedFolder = "UNCATEGORIZED_" + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        }
        if (StringUtils.isBlank(ossFilePath)) {
            return "NOT_DEFINE_FILE_" + System.currentTimeMillis();
        }
        // 最后一位补斜杠
        if (abilityBasedFolder.lastIndexOf(abilityBasedFolder) != '/') {
            abilityBasedFolder += '/';
        }
        return abilityBasedFolder + ossFilePath;
    }

    @Value("${oss.bucket.name.unique}")
    private String bucketName;

    @Override
    protected String getBucketName() {
        return bucketName;
    }

}
