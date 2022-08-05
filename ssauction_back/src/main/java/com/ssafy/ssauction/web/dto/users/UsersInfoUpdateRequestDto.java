package com.ssafy.ssauction.web.dto.users;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsersInfoUpdateRequestDto {
    private String userComment;
    private String userDesc;

    @Override
    public String toString() {
        return "UsersInfoUpdateRequestDto{" +
                "userComment='" + userComment + '\'' +
                ", userDesc='" + userDesc + '\'' +
                '}';
    }

    @Builder
    UsersInfoUpdateRequestDto(String userComment, String userDesc){
        this.userComment=userComment;
        this.userDesc=userDesc;
    }
}
