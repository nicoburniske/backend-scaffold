package com.codeforcommunity.dto.auth;

import com.codeforcommunity.dto.IDTO;

public class IsUserRequest implements IDTO {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}