package com.ssafy.ssauction.domain.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto-increment
    @Column(name = "user_no")
    private Long userNo;

    @Column(name = "user_email", nullable = false, length = 50)
    private String userEmail;

    @Column(name = "user_pwd", nullable = false)
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String userPwd;

    @Column(name = "user_phone_no", nullable = false, length = 11)
    private String userPhoneNo;

    @Column(name = "user_nickname", length = 16)
    private String userNickname;

    @Column(name = "user_desc", length = 200)
    private String userDesc;

    @Column(name = "user_comment", length = 50)
    private String userComment;

    @Column(name = "user_grade")
    private Integer userGrade;

    @Column(name = "user_reg_date", nullable = false)
    private Timestamp userRegDate;

    @Column(name = "user_update_date")
    private Timestamp userUpdateDate;

    @Builder
    public Users(String userEmail, String userPwd, String userPhoneNo, String userNickname, String userDesc, String userComment, int userGrade, Timestamp userRegDate, Timestamp userUpdateDate) {
        this.userEmail = userEmail;
        this.userPwd = userPwd;
        this.userPhoneNo = userPhoneNo;
        this.userNickname = userNickname;
        this.userDesc = userDesc;
        this.userComment = userComment;
        this.userGrade = userGrade;
        this.userRegDate = userRegDate;
        this.userUpdateDate = userUpdateDate;
    }

    public void updateProfile(String userComment,String userDesc){
        this.userComment=userComment;
        this.userDesc=userDesc;
    }
}
