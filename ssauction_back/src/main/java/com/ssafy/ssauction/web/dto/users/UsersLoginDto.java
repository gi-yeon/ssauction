package com.ssafy.ssauction.web.dto.users;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsersLoginDto {
    String loginEmail;
    String loginPwd;

    @Override
    public String toString() {
        return "UsersLoginDto{" +
                "loginEmail='" + loginEmail + '\'' +
                ", loginPwd='" + loginPwd + '\'' +
                '}';
    }

    @Builder
    public UsersLoginDto(String loginEmail, String loginPwd) {
        this.loginEmail = loginEmail;
        this.loginPwd = loginPwd;
    }
}
