package com.ssafy.ssauction.domain.items;

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
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_no")
    private Long itemNo;

    @Column(name = "item_name", nullable = false, length = 50)
    private String itemName;

    @Column(name = "item_model_no", length = 100)
    private String itemModelNo;

    @Column(name = "item_quality", nullable = false)
    private int itemQuality;

    @Column(name = "item_deal_status", nullable = false)
    private int itemDealStatus;

    @Column(name = "item_desc", length = 200)
    private String itemDesc;

    @Column(name = "item_start_price", nullable = false)
    private int itemStartPrice;

    @Column(name = "item_final_price")
    private int itemFinalPrice;

    @Column(name = "item_reg_time")
    private Timestamp itemRegTime;

    @Column(name = "item_update_time")
    private Timestamp itemUpdateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_seller_no")
    private Users seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_buyer_no")
    private Users buyer;

    @Override
    public String toString() {
        return "Items{" +
                "itemNo=" + itemNo +
                ", itemName='" + itemName + '\'' +
                ", itemModelNo='" + itemModelNo + '\'' +
                ", itemQuality=" + itemQuality +
                ", itemDealStatus=" + itemDealStatus +
                ", itemDesc='" + itemDesc + '\'' +
                ", itemStartPrice=" + itemStartPrice +
                ", itemFinalPrice=" + itemFinalPrice +
                ", itemRegTime=" + itemRegTime +
                ", itemUpdateTime=" + itemUpdateTime +
                ", seller=" + seller +
                ", buyer=" + buyer +
                '}';
    }

    @Builder
    public Items(String itemName, String itemModelNo, int itemQuality, int itemDealStatus, String itemDesc, int itemStartPrice, int itemFinalPrice, Users seller, Users buyer) {
        this.itemName = itemName;
        this.itemModelNo = itemModelNo;
        this.itemQuality = itemQuality;
        this.itemDealStatus = itemDealStatus;
        this.itemDesc = itemDesc;
        this.itemStartPrice = itemStartPrice;
        this.itemFinalPrice = itemFinalPrice;
        this.itemRegTime = new Timestamp(System.currentTimeMillis());
        this.seller=seller;
        this.buyer=buyer;
    }
}
