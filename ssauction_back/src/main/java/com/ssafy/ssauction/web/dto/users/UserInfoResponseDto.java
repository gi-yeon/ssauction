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

    private String userDesc;

    private String userComment;

    private String userImgUri;
    @Override
    public String toString() {
        return "UserInfoResponseDto{" +
                "userDesc='" + userDesc + '\'' +
                ", userComment='" + userComment + '\'' +
                ", userImgUri='" + userImgUri + '\'' +
                '}';
    }
    @Builder
    public UserInfoResponseDto(Users user, UserImgs img){
        this.userDesc=user.getUserDesc();
        this.userComment=user.getUserComment();
        this.userImgUri=img.getUserImgUri();
    }
}