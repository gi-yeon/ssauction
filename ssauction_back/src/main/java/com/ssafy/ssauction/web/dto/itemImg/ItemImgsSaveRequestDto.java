package com.ssafy.ssauction.web.dto.itemImg;

import com.ssafy.ssauction.domain.itemImgs.ItemImgs;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.domain.userImages.UserImgs;
import com.ssafy.ssauction.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
public class ItemImgsSaveRequestDto {
    private Long itemNo;
    private String itemImgName;
    private String itemImgUri;

    @Builder
    public ItemImgsSaveRequestDto(Long itemNo,String itemImgName, String itemImgUri) {
        this.itemNo=itemNo;
        this.itemImgName=itemImgName;
        this.itemImgUri=itemImgUri;
    }

    public ItemImgs toEntity(Items item){
        ItemImgs temp=ItemImgs.builder()
                .itemImgName(itemImgName)
                .itemImgUri(itemImgUri)
                .item(item)
                .build();
        return temp;
    }
}
