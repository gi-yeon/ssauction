package com.ssafy.ssauction.web.dto.Items;

import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.domain.posts.Posts;
import com.ssafy.ssauction.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemsSaveRequestDto {
    private String itemName;
    private String itemModelNo;
    private int itemQuality;
    private int itemDealStatus;
    private int itemStartPrice;
    private Long userNo;
    @Builder
    public ItemsSaveRequestDto(Long userNo, String itemName, String itemModelNo, int itemQuality, int itemDealStatus, int itemStartPrice){
        this.itemName=itemName;
        this.itemModelNo=itemModelNo;
        this.itemQuality=itemQuality;
        this.itemDealStatus=itemDealStatus;
        this.itemStartPrice=itemStartPrice;
        this.userNo=userNo;
    }

    public Items toEntity(Users seller){
        return Items.builder()
                .itemName(itemName)
                .itemModelNo(itemModelNo)
                .itemQuality(itemQuality)
                .itemDealStatus(itemDealStatus)
                .itemStartPrice(itemStartPrice)
                .seller(seller)
                .build();
    }
}
