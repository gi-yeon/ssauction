package com.ssafy.ssauction.web.dto.Houses;

import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.items.Items;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HousesSearchAllResponseDto {
    private List<Items> itemsList;
    private List<Houses> housesList;
}
