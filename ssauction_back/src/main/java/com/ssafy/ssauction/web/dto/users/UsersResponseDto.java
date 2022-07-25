package com.ssafy.ssauction.web.dto.users;

import com.ssafy.ssauction.domain.users.Users;
import lombok.Getter;

@Getter
public class UsersResponseDto {
    private Long userNo;

    private String userEmail;

    private String userPhoneNo;

    private String userNickname;

    private String userDesc;

    private String userComment;

    private Integer userGrade;

    @Override
    public String toString() {
        return "UsersResponseDto{" +
                "userNo=" + userNo +
                ", userEmail='" + userEmail + '\'' +
                ", userPhoneNo='" + userPhoneNo + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", userDesc='" + userDesc + '\'' +
                ", userComment='" + userComment + '\'' +
                ", userGrade=" + userGrade +
                '}';
    }

    public UsersResponseDto(Users entity){
        this.userNo=entity.getUserNo();
        this.userEmail=entity.getUserEmail();
        this.userPhoneNo=entity.getUserPhoneNo();
        this.userNickname=entity.getUserNickname();
        this.userDesc=entity.getUserDesc();
        this.userComment=entity.getUserComment();
        this.userGrade=entity.getUserGrade();
    }
}
