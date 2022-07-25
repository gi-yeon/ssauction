package com.ssafy.ssauction.web.dto.users;

import com.ssafy.ssauction.domain.users.Users;
import lombok.Getter;

@Getter
public class UsersFindIdDto {
    private String userPhoneNo;

    private String userEmail;

    @Override
    public String toString() {
        return "UsersFindIdDto{" +
                "userPhoneNo='" + userPhoneNo +
                ", userEmail='" + userEmail +
                '}';
    }

    public UsersFindIdDto(Users entity){
        this.userPhoneNo=entity.getUserPhoneNo();
        this.userEmail=entity.getUserEmail();
    }
}
