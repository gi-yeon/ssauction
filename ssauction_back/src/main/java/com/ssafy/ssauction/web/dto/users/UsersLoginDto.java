package com.ssafy.ssauction.web.dto.users;

import com.ssafy.ssauction.domain.users.Authority;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsersLoginDto {
    String loginEmail;
    String loginPwd;

    Authority authority;

    @Override
    public String toString() {
        return "UsersLoginDto{" +
                "loginEmail='" + loginEmail + '\'' +
                ", loginPwd='" + loginPwd + '\'' +
                ", authority=" + authority +
                '}';
    }

    @Builder
    public UsersLoginDto(String loginEmail, String loginPwd, Authority authority) {
        this.loginEmail = loginEmail;
        this.loginPwd = loginPwd;
        this.authority=authority;
    }
}
