package com.ssafy.ssauction.web.controller;

import com.ssafy.ssauction.service.users.UsersService;
import com.ssafy.ssauction.web.dto.users.UsersResponseDto;
import com.ssafy.ssauction.web.dto.users.UsersSaveRequestDto;
import com.ssafy.ssauction.web.dto.users.UsersUpdateProfileRequestDto;
import com.ssafy.ssauction.web.dto.users.UsersFindIdDto;
import com.ssafy.ssauction.web.dto.users.UsersUpdatePwdDto;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.Path;

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

    // 아이디 찾기
    // 전화번호를 이용해 아이디(이메일) 찾기 구현
    @GetMapping("/users/findId/{userPhoneNo}")
    public UsersFindIdDto findByPhoneNo(@PathVariable String userPhoneNo){
        return usersService.findByPhoneNo(userPhoneNo);
    }
//    @GetMapping("/users/findId/{userPhoneNo}")
//    public UsersFindIdDto findByPhoneNo(@PathVariable String userPhoneNo){
//        UsersFindIdDto findIdDto= usersService.findByPhoneNo(userPhoneNo);
//        if(findIdDto==null)
//            return null;
//        return findIdDto;
//    }

    // 비밀번호 재설정
    // 아이디(이메일) + 전화번호를 이용해 비밀번호 재설정 구현
    @PutMapping("/users/resetPwd/{userPhoneNo}/{userId}")
    public String update(@PathVariable String userPhoneNo, @PathVariable String userId, @RequestBody UsersUpdatePwdDto resetPwdDto){
        return usersService.updatePwd(userPhoneNo, userId, resetPwdDto);
    }

}
