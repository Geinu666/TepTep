package com.example.splashdemo.entity;

import com.example.splashdemo.utils.RSAEncryptedUtil;

public class Login {
    String password;
    String username;

    public Login(String username, String password) {
        this.password = password;
        this.username = username;
    }

}
