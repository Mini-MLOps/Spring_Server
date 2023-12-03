package com.sku.minimlops.model.dto.request;

import lombok.Getter;

@Getter
public class UserLoginRequest {
    private String username;
    private String password;
}
