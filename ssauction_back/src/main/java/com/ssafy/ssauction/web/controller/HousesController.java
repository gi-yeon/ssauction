package com.ssafy.ssauction.web.controller;

import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.service.Items.ItemsService;
import com.ssafy.ssauction.service.houses.HousesService;
import com.ssafy.ssauction.web.dto.Houses.HousesSaveRequestDto;
import com.ssafy.ssauction.web.dto.Items.ItemsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class HousesController {
    private final HousesService housesService;
    private final ItemsService itemsService;

    @PostMapping("/houses/")
    public Long save(@RequestBody HousesSaveRequestDto requestDto) {
        Items item = itemsService.findEntityById(requestDto.getItemNo());
        Houses house = housesService.save(item, requestDto);
        return house.getHouseNo();
    }
}
