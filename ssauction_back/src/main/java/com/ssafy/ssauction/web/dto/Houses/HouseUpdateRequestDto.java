package com.ssafy.ssauction.web.dto.Houses;

import com.ssafy.ssauction.domain.categories.Categories;
import com.ssafy.ssauction.domain.items.DealStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class HouseUpdateRequestDto {
    String houseName;
    Timestamp date;
    String itemName;
    String modelNo;
    Categories[] categories;
    DealStatus dealStatus;
    String describe;
    int startPrice;

    @Builder
    public HouseUpdateRequestDto(String houseName, Timestamp date, String itemName, String modelNo, Categories[] categories, DealStatus dealStatus, String describe, int startPrice) {
        this.houseName = houseName;
        this.date = date;
        this.itemName = itemName;
        this.modelNo = modelNo;
        this.categories = categories;
        this.dealStatus = dealStatus;
        this.describe = describe;
        this.startPrice = startPrice;
    }
}
