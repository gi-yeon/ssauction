package com.ssafy.ssauction.web.dto.categories;

import com.ssafy.ssauction.domain.categories.Categories;
import com.ssafy.ssauction.domain.itemImgs.ItemImgs;
import com.ssafy.ssauction.domain.items.Items;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoriesSaveRequestDto {
    private Long itemNo;
    private String ctgrName;

    @Builder
    public CategoriesSaveRequestDto(Long itemNo, String ctgrName) {
        this.itemNo=itemNo;
        this.ctgrName=ctgrName;
    }

    public Categories toEntity(Items item, CategoriesSaveRequestDto dto){
        Categories temp=Categories.builder()
                .ctgrName(dto.getCtgrName())
                .item(item)
                .build();
        return temp;
    }
}
