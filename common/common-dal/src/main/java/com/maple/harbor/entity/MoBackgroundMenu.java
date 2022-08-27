package com.maple.harbor.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 后台菜单表(MoBackgroundMenu)表实体类
 * @author makejava
 * @since 2022-08-27 14:11:36
 */
@SuppressWarnings("serial")
public class MoBackgroundMenu extends Model<MoBackgroundMenu> {
    //主键id
    private Long    id;
    //创建时间
    private Date    createTime;
    //修改时间
    private Date    modifyTime;
    //菜单code
    private String  menuCode;
    //菜单标题
    private String  menuTitle;
    //菜单id
    private Long    menuId;
    //父菜单id，根菜单为 -1
    private Long    menuParentId;
    //菜单url
    private String  url;
    //图标路径
    private String  iconPath;
    //菜单排序
    private Integer sort;
    //权限code的集合, 任意一个权限code都可以显示 关联：mo_system_enum_authority#authority_code
    private String  authorityCodes;
    //状态
    private String  status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getMenuParentId() {
        return menuParentId;
    }

    public void setMenuParentId(Long menuParentId) {
        this.menuParentId = menuParentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getAuthorityCodes() {
        return authorityCodes;
    }

    public void setAuthorityCodes(String authorityCodes) {
        this.authorityCodes = authorityCodes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取主键值
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.id;
    }

}

