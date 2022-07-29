package com.ssafy.ssauction.web.controller;

import com.ssafy.ssauction.auth.JwtTokenProvider;
import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.service.userImages.UserImgsService;
import com.ssafy.ssauction.service.users.UsersService;
import com.ssafy.ssauction.web.dto.userImages.UserImgsSaveRequestDto;
import com.ssafy.ssauction.web.dto.userImages.UserImgsUpdateRequestDto;
import com.ssafy.ssauction.web.dto.users.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UsersController {
    private static final String SUCCESS = "success"; //성공 시 메시지
    private static final String FAIL = "fail"; //실패 시 메시지
    private final UsersService usersService;
    private final UserImgsService userImgsService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/{userNo}")
    public UsersResponseDto findById(@PathVariable Long userNo) {
        return usersService.findById(userNo);
    }

    @PostMapping("/join")
    public Long save(@RequestBody UsersSaveRequestDto requestDto) {
        Users user = usersService.save(requestDto);
        return userImgsService.save(user);
    }

    @PutMapping("/img/{userNo}")
    public Long updateImg(@PathVariable Long userNo, @RequestBody UserImgsUpdateRequestDto requestDto) {
        return userImgsService.update(userNo, requestDto);
    }

    @PutMapping("/profile/{userNo}")
    public Long updateProfile(@PathVariable Long userNo, @RequestBody UsersUpdateProfileRequestDto requestDto) {
        return usersService.updateProfile(userNo, requestDto);
    }

    @DeleteMapping("/{userNo}")
    public Long delete(@PathVariable Long userNo) {
        return usersService.delete(userNo);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UsersAuthRequestDto loginInfo) {
        String userEmail = loginInfo.getUserEmail();
        String userPwd = loginInfo.getUserPwd();
        HttpStatus status = null;

        HashMap<String, Object> result = new HashMap<>();

        //userEmail로 DB에 저장된 user정보 불러옴
        UsersAuthResponseDto user = usersService.findByUserEmail(userEmail);

        //비밀번호가 올바르게 입력됐다면면
//       if (passwordEncoder.matches(userPwd, user.getUserPwd())) {
        if (userPwd.equals(user.getUserPwd())) {

            //리스트에 유저정보 담아준다.
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("userEmail", user.getUserEmail());
            userMap.put("userNickname", user.getUserNickname());
            userMap.put("authority", user.getAuthority());


            //accessToken, refreshToken 생성하고 refresh token은 DB에 저장
            String refreshToken = jwtTokenProvider.createRefreshToken(userEmail);
            result.put("accessToken", jwtTokenProvider.createAccessToken(userMap));
            result.put("refreshToken", refreshToken);
            usersService.updateRefreshToken(user.getUserNo(), refreshToken);

            result.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;

        } else {
            result.put("message", FAIL);
            status = HttpStatus.ACCEPTED;

        }
        return new ResponseEntity<Map<String, Object>>(result, status);

    }

    //refresh token으로 access token 재발급
    @PostMapping("/refresh")
    public ResponseEntity<Map<String, Object>> validateRefreshToken(@RequestBody String userEmail) {
        HashMap<String, Object> result = new HashMap<>();
        String newAccessToken = jwtTokenProvider.validateRefreshToken(userEmail);
        HttpStatus status = null;

        if (newAccessToken != null) {
            result.put("accessToken", newAccessToken);
            result.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;
        } else {
            result.put("message", FAIL);
            status = HttpStatus.ACCEPTED;
        }

        return new ResponseEntity<Map<String, Object>>(result, status);

    }
}
