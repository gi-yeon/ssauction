package com.ssafy.ssauction.web.dto.users;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsersUpdateProfileRequestDto {
    private String userComment;
    private String userDesc;

    @Builder UsersUpdateProfileRequestDto(String userComment, String userDesc){
        this.userComment=userComment;
        this.userDesc=userDesc;
    }
}
