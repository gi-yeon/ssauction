package com.ssafy.ssauction.domain.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.domain.likes.Likes;
import com.ssafy.ssauction.domain.resultOrders.ResultOrders;
import com.ssafy.ssauction.domain.userImages.UserImgs;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Users {
    @Id
    @JsonIgnore
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
    private int userGrade;

    @Column(name = "user_reg_date", nullable = false)
    private Timestamp userRegDate;

    @Column(name = "user_update_date")
    private Timestamp userUpdateDate;

    @Column(name = "user_role")
    private Authority authority;
    @Setter
    @Column(name = "refresh_token")
    private String refreshToken;

    @JsonIgnore
    @OneToOne(mappedBy = "user", fetch = LAZY, cascade = CascadeType.ALL)
    private UserImgs userImgs;

    @Setter
    @JsonIgnore
    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true, fetch = LAZY)
    private List<Items> sellItems = new ArrayList<>();

    @Setter
    @JsonIgnore
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = LAZY)
    private List<Items> purchaseItems = new ArrayList<>();

    @Setter
    @JsonIgnore
    @OneToMany(mappedBy = "bidder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = LAZY)
    private List<ResultOrders> results = new ArrayList<>();

    @Setter
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = LAZY)
    private List<Likes> likes = new ArrayList<>();

    @Builder
    public Users(String userEmail, String userPwd,
                 String userPhoneNo, String userNickname,
                 String userDesc, String userComment,
                 int userGrade, Timestamp userUpdateDate,
                 List<Items> sellItems,
                 List<Items> purchaseItems,
                 List<ResultOrders> results,
                 List<Likes> likes, String refreshToken,
                 Authority authority) {
        this.userEmail = userEmail;
        this.userPwd = userPwd;
        this.userPhoneNo = userPhoneNo;
        this.userNickname = userNickname;
        this.userDesc = userDesc;
        this.userComment = userComment;
        this.userGrade = userGrade;
        this.userRegDate = new Timestamp(System.currentTimeMillis());
        this.userUpdateDate = userUpdateDate;
        this.sellItems = sellItems;
        this.purchaseItems = purchaseItems;
        this.results = results;
        this.likes = likes;
        this.refreshToken = refreshToken;
        this.authority = authority;
    }

    public void updateProfile(String userComment, String userDesc) {
        this.userComment = userComment;
        this.userDesc = userDesc;
        this.userUpdateDate = new Timestamp(System.currentTimeMillis());
    }
}

