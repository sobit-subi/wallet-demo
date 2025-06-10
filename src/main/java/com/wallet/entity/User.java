package com.wallet.entity;

import lombok.Data;

@Data
public class User {
    private String id;
    private String userId;
    private String password;
    private String name;
    private String phone;
    private String email;
    private String token;
}
