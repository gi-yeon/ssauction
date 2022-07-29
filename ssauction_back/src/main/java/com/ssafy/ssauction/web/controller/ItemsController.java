package com.ssafy.ssauction.web.controller;

import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.service.Items.ItemsService;
import com.ssafy.ssauction.service.userImages.users.UsersService;
import com.ssafy.ssauction.web.dto.Items.ItemsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ItemsController {
    private final ItemsService itemsService;
    private final UsersService usersService;

    @PostMapping("/items/")
    public Long save(@RequestBody ItemsSaveRequestDto requestDto) {
        Users user = usersService.findEntityById(requestDto.getUserNo());
        Items item = itemsService.save(user, requestDto);
        user.getSellItems().add(item);
        return user.getUserNo();
    }
}
