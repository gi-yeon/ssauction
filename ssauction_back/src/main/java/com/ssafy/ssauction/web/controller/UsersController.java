package com.ssafy.ssauction.web.controller;

import com.ssafy.ssauction.service.userImages.UserImgsService;
import com.ssafy.ssauction.service.users.UsersService;
import com.ssafy.ssauction.web.dto.users.UsersResponseDto;
import com.ssafy.ssauction.web.dto.users.UsersSaveRequestDto;
import com.ssafy.ssauction.web.dto.users.UsersUpdateProfileRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UsersController {
    private final UsersService usersService;
    @GetMapping("/users/{userNo}")
    public UsersResponseDto findById(@PathVariable Long userNo){
        return usersService.findById(userNo);
    }
    @PostMapping("/users/")
    public Long save(@RequestBody UsersSaveRequestDto requestDto){
        return usersService.save(requestDto);
    }

    @PutMapping("/users/profile/{userNo}")
    public Long update(@PathVariable Long userNo, @RequestBody UsersUpdateProfileRequestDto requestDto){
        return usersService.updateProfile(userNo,requestDto);
    }

    @DeleteMapping("/users/{userNo}")
    public Long delete(@PathVariable Long userNo){
        return usersService.delete(userNo);
    }
}
