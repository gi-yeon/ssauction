package com.ssafy.ssauction.web.controller;

import com.ssafy.ssauction.domain.userImages.UserImgs;
import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.service.userImages.UserImgsService;
import com.ssafy.ssauction.service.users.UsersService;
import com.ssafy.ssauction.web.dto.userImages.UserImgsSaveRequestDto;
import com.ssafy.ssauction.web.dto.userImages.UserImgsUpdateRequestDto;
import com.ssafy.ssauction.web.dto.users.UsersLoginDto;
import com.ssafy.ssauction.web.dto.users.UsersResponseDto;
import com.ssafy.ssauction.web.dto.users.UsersSaveRequestDto;
import com.ssafy.ssauction.web.dto.users.UsersUpdateProfileRequestDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UsersController {
    private final UsersService usersService;
    private final UserImgsService userImgsService;

    @GetMapping("/users/{userNo}")
    public UsersResponseDto findById(@PathVariable Long userNo) {
        return usersService.findById(userNo);
    }

    @PostMapping("/users/login")
    public UsersResponseDto login(@RequestBody UsersLoginDto requestDto){
        UsersResponseDto responseDto=usersService.findUser(requestDto);
        if (responseDto==null){
            return null;
        }
        System.out.println(responseDto.toString());
        return responseDto;
    }

    @PostMapping("/users")
    public String save(@RequestBody UsersSaveRequestDto requestDto) {
        Long userImgs=-1L;
        Users user = usersService.save(requestDto);
        userImgs= userImgsService.save(user);
        System.out.println(userImgs);
        if(user==null){
            return "FAIL";
        }
        return "OK";
    }

    @PutMapping("users/img/{userNo}")
    public Long updateImg(@PathVariable Long userNo, @RequestBody UserImgsUpdateRequestDto requestDto) {
        return userImgsService.update(userNo, requestDto);
    }

    @PutMapping("/users/profile/{userNo}")
    public Long updateProfile(@PathVariable Long userNo, @RequestBody UsersUpdateProfileRequestDto requestDto) {
        return usersService.updateProfile(userNo, requestDto);
    }

    @DeleteMapping("/users/{userNo}")
    public Long delete(@PathVariable Long userNo) {
        System.out.println("\n\n"+userNo+"\n\n");
        try {
            Long delete = usersService.delete(userNo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 1L;
    }
}
