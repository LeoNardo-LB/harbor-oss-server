package com.maple.harbor.authentication.core.mock;

import lombok.Data;

import java.util.Set;
@Data
public class MockUserInfo {

    private String id;

    private Long accountId;

    private String userName;

    private Set<String> roles;
}
