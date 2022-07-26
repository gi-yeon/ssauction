package com.ssafy.ssauction.domain.userImages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.ssauction.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "user_images")
public class UserImgs {
    @Id
    @Column(name="user_no")
    private Long userNo;

    @Column(name = "user_img_name", length = 50)
    private String userImgName;

    @Column(name = "user_img_uri", length = 200)
    private String userImgUri;

    @Column(name = "user_img_reg_date")
    private Timestamp userImgRegDate;

    @Column(name = "user_img_update_date")
    private Timestamp userImgUpdateDate;

    @MapsId
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private Users user;

    @Override
    public String toString() {
        return "UserImgs{" +
                "userNo=" + userNo +
                ", userImgName='" + userImgName + '\'' +
                ", userImgUri='" + userImgUri + '\'' +
                ", userImgRegDate=" + userImgRegDate +
                ", userImgUpdateDate=" + userImgUpdateDate +
                ", user=" + user +
                '}';
    }
    public void update(String userImgName, String userImgUri){
        this.userImgName=userImgName;
        this.userImgUri=userImgUri;
        this.userImgUpdateDate=new Timestamp(System.currentTimeMillis());
    }
    @Builder
    public UserImgs(Users user, String userImgName, String userImgUri, Timestamp userImgRegDate, Timestamp userImgUpdateDate) {
        this.user = user;
        this.userImgName = userImgName;
        this.userImgUri = userImgUri;
        this.userImgRegDate = new Timestamp(System.currentTimeMillis());
        this.userImgUpdateDate = userImgUpdateDate;
    }
}
