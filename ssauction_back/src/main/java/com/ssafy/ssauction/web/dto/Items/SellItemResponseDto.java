package com.ssafy.ssauction.web.dto.Items;

import com.ssafy.ssauction.domain.items.Items;
import lombok.*;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SellItemResponseDto {
    private Long itemNo;
    private String itemName;
    private int startPrice;
    private String modelNo;
    private Timestamp regTime;

    @Builder
    public SellItemResponseDto(Items item){
        this.itemNo=item.getItemNo();
        this.itemName=item.getItemName();
        this.startPrice=item.getItemStartPrice();
        this.modelNo=item.getItemModelNo();
        this.regTime=item.getItemRegTime();
    }
}
