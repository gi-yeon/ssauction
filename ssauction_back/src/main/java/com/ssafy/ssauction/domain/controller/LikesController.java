package com.ssafy.ssauction.domain.controller;

import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.likes.Likes;
import com.ssafy.ssauction.domain.resultOrders.ResultOrders;
import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.service.houses.HousesService;
import com.ssafy.ssauction.service.likes.LikesService;
import com.ssafy.ssauction.service.users.UsersService;
import com.ssafy.ssauction.web.dto.likes.LikesSaveDto;
import com.ssafy.ssauction.web.dto.resultOrders.ResultOrdersSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LikesController {
    private final UsersService usersService;
    private final HousesService housesService;
    private final LikesService likesService;

    @PostMapping("/likes")
    public Long save(@RequestBody LikesSaveDto requestDto){
        Users user =usersService.findEntityById(requestDto.getUserNo());
        Houses house=housesService.findEntityById(requestDto.getHouseNo());
        Likes like=likesService.save(user,house,requestDto);
        user.getLikes().add(like);
        house.getLikes().add(like);
        return like.getLikeNo();
    }
}
