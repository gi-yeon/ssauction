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

    @Builder
    public UsersSaveRequestDto(String userEmail, String userPwd, String userPhoneNo, String userNickname) {
        this.userEmail = userEmail;
        this.userPwd = userPwd;
        this.userPhoneNo = userPhoneNo;
        this.userNickname = userNickname;
    }



    public Users toEntity(){
        return Users.builder()
                .userEmail(userEmail)
                .userPwd(userPwd)
                .userPhoneNo(userPhoneNo)
                .userNickname(userNickname)
                .userGrade(4)
                .build();
    }
}
