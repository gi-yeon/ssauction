package com.ssafy.ssauction.web.dto.users;

import com.ssafy.ssauction.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class UsersSaveRequestDto {
    private String userEmail;

    private String userPwd;

    private String userPhoneNo;

    private String userNickname;

    private Timestamp userRegDate;

    @Builder
    public UsersSaveRequestDto(String userEmail, String userPwd, String userPhoneNo, String userNickname) {
        this.userEmail = userEmail;
        this.userPwd = userPwd;
        this.userPhoneNo = userPhoneNo;
        this.userNickname = userNickname;
        this.userRegDate = new Timestamp(System.currentTimeMillis());
    }



    public Users toEntity(){
        return Users.builder()
                .userEmail(userEmail)
                .userPwd(userPwd)
                .userPhoneNo(userPhoneNo)
                .userNickname(userNickname)
                .userComment(null)
                .userDesc(null)
                .userGrade(4)
                .userRegDate(userRegDate)
                .userUpdateDate(null)
                .build();
    }
}
