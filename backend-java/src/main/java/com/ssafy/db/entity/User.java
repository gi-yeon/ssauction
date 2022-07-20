package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 유저 모델 정의.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto-increment
    @Column(name = "user_no")
    private Long userNo;

    @Column(name = "user_email", nullable = false, length = 50)
    private String userId;

    @Column(name = "user_pwd", nullable = false)
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "user_phone_no",nullable = false, length = 11)
    private String userPhoneNo;

    @Column(name = "user_nickname", length = 16)
    private String userNickname;

    @Column(name = "user_desc", length = 200)
    private String userDesc;

    @Column(name = "user_comment", length = 50)
    private String userComment;

    @Column(name = "user_grade")
    private int userGrade;

    @Column(name = "user_reg_date", nullable = false)
    private Timestamp userRegDate;

    @Column(name = "user_update_date")
    private Timestamp userUpdateDate;


}