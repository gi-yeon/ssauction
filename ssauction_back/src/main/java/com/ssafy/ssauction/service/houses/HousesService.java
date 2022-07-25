package com.ssafy.ssauction.service.houses;

import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.houses.HousesRepoository;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.web.dto.Houses.HousesSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HousesService {
    private final HousesRepoository housesRepoository;

    public Houses save(Items item, HousesSaveRequestDto requestDto){
        Houses house=requestDto.toEntity(item);
        housesRepoository.save(house);
        return house;
    }
}
