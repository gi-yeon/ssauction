package com.ssafy.ssauction.web.dto.Houses;

import com.ssafy.ssauction.web.dto.Items.ItemsSaveRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HousesItemsSaveRequestDto {
    HousesSaveRequestDto house;
    ItemsSaveRequestDto item;
}
