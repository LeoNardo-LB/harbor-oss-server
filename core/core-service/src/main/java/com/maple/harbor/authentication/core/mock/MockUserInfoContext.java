package com.maple.harbor.authentication.core.mock;

import com.google.common.collect.Sets;
import com.maple.harbor.dao.MoAccountBasicDao;
import com.maple.harbor.entity.MoAccountBasic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Objects;

@Component
public class MockUserInfoContext implements ApplicationListener<ContextRefreshedEvent> {

    // private final static InheritableThreadLocal<MockUserInfo> MOCK_USER_INFO = new InheritableThreadLocal<>();

    private static MockUserInfo MOCK_USER_INFO;

    @Autowired
    private MoAccountBasicDao accountBasicDao;

    public static MockUserInfo getUserInfo() {
        return MOCK_USER_INFO;
    }

    public static void setUserInfo(MockUserInfo userInfo) {
        MOCK_USER_INFO = userInfo;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (Objects.isNull(MOCK_USER_INFO)) {
            MoAccountBasic moAccountBasic = accountBasicDao.selectById(1563201927433895938L);
            MockUserInfo mockUserInfo = new MockUserInfo();
            mockUserInfo.setId(mockUserInfo.getId());
            mockUserInfo.setUserName(moAccountBasic.getAccountName());
            mockUserInfo.setAccountId(moAccountBasic.getId());
            mockUserInfo.setRoles(Sets.newHashSet("ADMIN"));
            MOCK_USER_INFO = mockUserInfo;
        }
    }

}
