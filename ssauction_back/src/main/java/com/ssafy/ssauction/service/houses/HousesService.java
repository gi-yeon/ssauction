package com.ssafy.ssauction.service.houses;

import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.houses.HousesRepository;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.web.dto.Houses.HousesSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HousesService {
    private final HousesRepository housesRepository;

    public Houses save(Items item, HousesSaveRequestDto requestDto){
        Houses house=requestDto.toEntity(item);
        housesRepository.save(house);
        return house;
    }
    public Long delete(Long houseNo){
        housesRepository.deleteById(houseNo);
        return houseNo;
    }
    public Houses findEntityById(Long houseNo){
        return housesRepository.findById(houseNo).get();
    }
}