package com.ssafy.ssauction.web.dto.users;

import com.ssafy.ssauction.domain.userImages.UserImgs;
import com.ssafy.ssauction.domain.users.Authority;
import com.ssafy.ssauction.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoResponseDto {

    private String userEmail;
    private String userPhoneNo;
    private String userDesc;

    private String userComment;


    @Override
    public String toString() {
        return "UserInfoResponseDto{" +
                "userEmail='" + userEmail + '\'' +
                ", userPhoneNo='" + userPhoneNo + '\'' +
                ", userDesc='" + userDesc + '\'' +
                ", userComment='" + userComment + '\'' +
                '}';
    }
    @Builder
    public UserInfoResponseDto(Users user, UserImgs img){
        this.userEmail=user.getUserEmail();
        this.userPhoneNo=user.getUserPhoneNo();
        this.userDesc=user.getUserDesc();
        this.userComment=user.getUserComment();
    }
}