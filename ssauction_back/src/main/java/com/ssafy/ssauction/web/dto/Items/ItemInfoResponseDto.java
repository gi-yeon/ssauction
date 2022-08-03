package com.ssafy.ssauction.web.dto.Items;

import com.ssafy.ssauction.domain.itemImgs.ItemImgs;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.web.dto.itemImg.ItemImgsGetResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemInfoResponseDto {
    private SellItemResponseDto item;
    private ItemImgsGetResponseDto itemMainImg;
}
