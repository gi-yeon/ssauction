package com.ssafy.ssauction.web.dto.categories;

import com.ssafy.ssauction.domain.categories.Categories;
import com.ssafy.ssauction.domain.items.Items;
import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;

@Getter
public class CategoriesLoadRequestDto {
    private Long itemNo;
    private String[] ctgrName;

    @Builder
    public CategoriesLoadRequestDto(Long itemNo, String[] ctgrName) {
        this.itemNo=itemNo;
        this.ctgrName=ctgrName;
    }

    @Override
    public String toString() {
        return "CategoriesSaveRequestDto{" +
                "itemNo=" + itemNo +
                ", ctgrName=" + Arrays.toString(ctgrName) +
                '}';
    }

    public Categories toEntity(Items item, String ctgrName){
        Categories temp=Categories.builder()
                .ctgrName(ctgrName)
                .item(item)
                .build();
        return temp;
    }
}
