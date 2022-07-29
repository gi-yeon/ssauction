package com.ssafy.ssauction.web.controller;

import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.service.Items.ItemsService;
import com.ssafy.ssauction.service.houses.HousesService;
import com.ssafy.ssauction.service.users.UsersService;
import com.ssafy.ssauction.web.dto.Houses.HousesItemsSaveRequestDto;
import com.ssafy.ssauction.web.dto.Houses.HousesSaveRequestDto;
import com.ssafy.ssauction.web.dto.Houses.HousesSearchAllResponseDto;
import com.ssafy.ssauction.web.dto.Items.ItemsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/houses")
public class HousesController_new {
    private final HousesService housesService;
    private final ItemsService itemsService;
    private final UsersService usersService;

    @PostMapping()
    public Long save(@RequestBody HousesItemsSaveRequestDto requestDto) {
        ItemsSaveRequestDto itemsSaveRequestDto = requestDto.getItem();
        HousesSaveRequestDto housesSaveRequestDto = requestDto.getHouse();
        Users user = usersService.findEntityById(itemsSaveRequestDto.getUserNo());
        Items item = itemsService.save(user, itemsSaveRequestDto);
        Houses house = housesService.save(item, housesSaveRequestDto);
        return house.getHouseNo();
    }

    @GetMapping("{sellerNo}")
    public ResponseEntity<HousesSearchAllResponseDto> searchAll(@PathVariable Long sellerNo) {
        Users user = usersService.findEntityById(sellerNo);
        List<Items> itemList=user.getSellItems();

        List<Houses> housesList = new ArrayList<>();
        for (Items i : itemList) {
            housesList.add(i.);
        }
        return new ResponseEntity<HousesSearchAllResponseDto>(housesList, HttpStatus.OK);
    }
}
