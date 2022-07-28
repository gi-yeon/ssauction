package com.ssafy.ssauction.domain.itemImgs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@Entity
public class ItemImgs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long imgNo;

    @Column(name = "item_img_name", length = 50)
    private String itemImgName;

    @Column(name = "item_img_uri", length = 200)
    private String itemImgUri;

    @Column(name = "item_img_reg_date")
    private Timestamp itemImgRegDate;

    @Column(name = "item_img_update_date")
    private Timestamp itemImgUpdateDate;
    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_no")
    private Items item;

    @Builder
    public ItemImgs(String itemImgName, String itemImgUri, Items item) {
        this.itemImgName = itemImgName;
        this.itemImgUri = itemImgUri;
        this.item = item;
        this.itemImgRegDate = new Timestamp(System.currentTimeMillis());
        this.itemImgUpdateDate = itemImgUpdateDate;
    }
}
