package com.ssafy.ssauction.domain.userImages;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@Entity
@Table(name="user_images")
public class UserImgs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_img_no")
    private Long userImgNo;

    @Column(name="user_img_name", nullable = false,length = 50)
    private String userImgName;

    @Column(name = "user_img_uri", nullable = false,length = 200)
    private String userImgUri;

    @Column(name="user_no",nullable = false)
    private Long userNo;

    @Column(name="user_img_reg_date")
    private Timestamp userImgRegDate;

    @Column(name="user_img_update_date")
    private Timestamp userImgUpdateDate;

    @Builder

    public UserImgs(Long userImgsNo, String userImgName, String userImgUri, Long userNo, Timestamp userImgRegDate, Timestamp userImgUpdateDate) {
        this.userImgNo = userImgsNo;
        this.userImgName = userImgName;
        this.userImgUri = userImgUri;
        this.userNo = userNo;
        this.userImgRegDate = userImgRegDate;
        this.userImgUpdateDate = userImgUpdateDate;
    }
}
