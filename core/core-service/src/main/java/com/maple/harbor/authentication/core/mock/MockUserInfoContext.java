package com.maple.harbor.authentication.core.mock;

import com.google.common.collect.Sets;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class MockUserInfoContext implements ApplicationListener<ContextRefreshedEvent> {

    // private final static InheritableThreadLocal<MockUserInfo> MOCK_USER_INFO = new InheritableThreadLocal<>();

    private static MockUserInfo MOCK_USER_INFO;

    public static MockUserInfo getUserInfo() {
        return MOCK_USER_INFO;
    }

    public static void setUserInfo(MockUserInfo userInfo) {
        MOCK_USER_INFO = userInfo;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        MockUserInfo userInfo = new MockUserInfo();
        userInfo.setAccountId(12321L);
        userInfo.setUserName("Leonardo Watch");
        userInfo.setRoles(Sets.newHashSet("SYSTEM_ADMIN", "REPORTOR"));
        MOCK_USER_INFO = (userInfo);
    }

}
