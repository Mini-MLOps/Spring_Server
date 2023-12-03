package com.sku.minimlops.model.dto.request;

import lombok.Getter;

@Getter
public class UserJoinRequest {
    private String username;
    private String name;
    private String password;
}
