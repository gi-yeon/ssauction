package com.ssafy.ssauction.web.controller;


import com.ssafy.ssauction.auth.JwtTokenProvider;

import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.likes.Likes;

import com.ssafy.ssauction.domain.userImages.UserImgs;
import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.service.houses.HousesService;
import com.ssafy.ssauction.service.likes.LikesService;
import com.ssafy.ssauction.service.userImages.UserImgsService;

import com.ssafy.ssauction.service.users.UsersService;
import com.ssafy.ssauction.web.dto.likes.LikesSaveRequestDto;
import com.ssafy.ssauction.web.dto.userImages.UserImgsUpdateRequestDto;

import com.ssafy.ssauction.web.dto.Houses.HousesResponseDto;
import com.ssafy.ssauction.web.dto.users.*;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UsersController {
    private static final String SUCCESS = "success";

    private static final String FAIL = "fail";
    private final UsersService usersService;
    private final UserImgsService userImgsService;
    private final JwtTokenProvider jwtTokenProvider;


    private final HousesService housesService;
    private final LikesService likesService;

    @GetMapping("/profile/{userNo}")
    public UserInfoResponseDto findById(@PathVariable Long userNo) {
        return usersService.getInfo(userNo);
    }


//    @PostMapping("/join")
//    public Long save(@RequestBody UsersSaveRequestDto requestDto) {
//        return 1L;
//    }

    @PostMapping("/login")
    public UsersResponseDto login(@RequestBody UsersLoginDto requestDto) {
        UsersResponseDto responseDto = usersService.findUser(requestDto);
        System.out.println(responseDto.toString());
        if (responseDto == null) {
            return null;
        }
        System.out.println(responseDto.toString());
        return responseDto;
    }

    @PostMapping("/join")
    public String save(@RequestBody UsersSaveRequestDto requestDto) {
        System.out.println(requestDto.toString());
        Users user = usersService.save(requestDto);
        Long userImgs = userImgsService.save(user);
        System.out.println(userImgs);
        if (user == null) {
            return "FAIL";
        }
        return "OK";
    }

    @PutMapping("/profile/img/{userNo}")
    public Long updateImg(@PathVariable Long userNo, @RequestBody UserImgsUpdateRequestDto requestDto) {
        System.out.println(userNo);
        System.out.println(requestDto.toString());
        return userImgsService.update(userNo, requestDto);
    }

    // 아이디 찾기
    // 전화번호를 이용해 아이디(이메일) 찾기 구현
    @GetMapping("/findId/{userPhoneNo}")
    public UsersFindIdDto findByPhoneNo(@PathVariable String userPhoneNo) {
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
    @PutMapping("/resetPwd/{userPhoneNo}/{userId}")
    public String update(@PathVariable String userPhoneNo, @PathVariable String userId, @RequestBody UsersUpdatePwdDto resetPwdDto) {
        return usersService.updatePwd(userPhoneNo, userId, resetPwdDto);
    }

    @PutMapping("/profile/info/{userNo}")
    public ResponseEntity<String> updateInfo(@PathVariable Long userNo, @RequestBody UsersInfoUpdateRequestDto requestDto) {
        System.out.println(userNo);
        System.out.println(requestDto.toString());
        try {
            usersService.updateInfo(userNo, requestDto);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @PutMapping("/profile/name/{userNo}")
    public ResponseEntity<UsersNameResponseDto> updateNickname(@PathVariable Long userNo, @RequestBody UsersNameUpdateRequestDto requestDto) {
        System.out.println(requestDto.getUserNickname());
        String result = usersService.updateNickname(userNo, requestDto);
        return new ResponseEntity<>(UsersNameResponseDto.builder().userNickname(requestDto.getUserNickname()).build(), HttpStatus.OK);
    }

    @DeleteMapping("/{userNo}")
    public Long delete(@PathVariable Long userNo) {
        System.out.println("\n\n" + userNo + "\n\n");
        try {
            Long delete = usersService.delete(userNo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 1L;
    }

    //로그인
//    @PostMapping("/login")
//    public ResponseEntity<Map<String, Object>> login(@RequestBody UsersAuthRequestDto loginInfo) {
//        String userEmail = loginInfo.getUserEmail();
//        String userPwd = loginInfo.getUserPwd();
//        System.out.println("\n\n\n-----------------------"+userEmail+"------------------\n\n\nr");
//        HttpStatus status = null;
//
//        HashMap<String, Object> result = new HashMap<>();
//
//        //userEmail로 DB에 저장된 user정보 불러옴
//        UsersAuthResponseDto user = usersService.findByUserEmail(userEmail);
//
//        //비밀번호가 올바르게 입력됐다면면
////       if (passwordEncoder.matches(userPwd, user.getUserPwd())) {
//        if (userPwd.equals(user.getUserPwd())) {
//
//            //리스트에 유저정보 담아준다.
//            Map<String, Object> userMap = new HashMap<>();
//            userMap.put("userEmail", user.getUserEmail());
//            userMap.put("userNickname", user.getUserNickname());
//            userMap.put("authority", user.getAuthority());
//
//
//            //accessToken, refreshToken 생성하고 refresh token은 DB에 저장
//            String refreshToken = jwtTokenProvider.createRefreshToken(userEmail);
//            result.put("accessToken", jwtTokenProvider.createAccessToken(userMap));
//            result.put("refreshToken", refreshToken);
//            usersService.updateRefreshToken(user.getUserNo(), refreshToken);
//
//            result.put("message", SUCCESS);
//            status = HttpStatus.ACCEPTED;
//
//        } else {
//            result.put("message", FAIL);
//            status = HttpStatus.ACCEPTED;
//
//        }
//        return new ResponseEntity<Map<String, Object>>(result, status);
//
//    }

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

    @PostMapping("/likes")
    public ResponseEntity<String> createLikes(@RequestBody LikesSaveRequestDto saveDto) {
        Users user = usersService.findEntityById(saveDto.getUserNo());
        Houses house = housesService.findEntityById(saveDto.getHouseNo());
        ;
        Likes like = likesService.save(user, house);
        user.getLikes().add(like);
        house.getLikes().add(like);
        return new ResponseEntity<>("created", HttpStatus.OK);
    }

    @GetMapping("/likes/{userNo}")
    public ResponseEntity<List<HousesResponseDto>> getLikeHouse(@PathVariable Long userNo) {
        Users user = usersService.findEntityById(userNo);
        System.out.println(user.toString());
        List<Likes> likes = user.getLikes();
        List<HousesResponseDto> list = new ArrayList<>();
        System.out.println(likes.toString());
        for (Likes like : likes) {
            list.add(HousesResponseDto.builder().house(like.getHouse()).build());
        }
        System.out.println("\n\n" + list.toString() + "\n\n");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

//    @DeleteMapping("/users/likes")
//    public ResponseEntity<String>
}
