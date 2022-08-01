package com.ssafy.ssauction.web.controller;

import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.domain.items.ItemsRepository;
import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.service.Items.ItemsService;
import com.ssafy.ssauction.service.houses.HousesService;
import com.ssafy.ssauction.service.users.UsersService;
import com.ssafy.ssauction.web.dto.Items.ItemsSaveRequestDto;
import com.ssafy.ssauction.web.dto.users.UsersResponseDto;
import com.ssafy.ssauction.web.dto.users.UsersSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ItemsController {
    private final ItemsService itemsService;
    private final UsersService usersService;
    private final HousesService housesService;

    @PostMapping("/items")
    public Long save(@RequestBody ItemsSaveRequestDto requestDto) {
        Users user = usersService.findEntityById(requestDto.getUserNo());
        Items item = itemsService.save(user, requestDto);
        user.getSellItems().add(item);
        return user.getUserNo();
    }
    @DeleteMapping("/items/{itemNo}")
    public Long delete(@PathVariable Long itemNo){
        itemsService.delete(itemNo);
        return 77L;
    }
}
