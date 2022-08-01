package com.ssafy.ssauction.web.controller;


import com.ssafy.ssauction.auth.JwtTokenProvider;

import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.likes.Likes;

import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.service.houses.HousesService;
import com.ssafy.ssauction.service.likes.LikesService;
import com.ssafy.ssauction.service.userImages.UserImgsService;

import com.ssafy.ssauction.service.users.UsersService;
import com.ssafy.ssauction.web.dto.userImages.UserImgsUpdateRequestDto;
import com.ssafy.ssauction.web.dto.users.*;

import com.ssafy.ssauction.web.dto.Houses.HousesResponseDto;
import com.ssafy.ssauction.web.dto.likes.LikesSaveDto;
import com.ssafy.ssauction.web.dto.users.UsersLoginDto;
import com.ssafy.ssauction.web.dto.users.UsersResponseDto;
import com.ssafy.ssauction.web.dto.users.UsersSaveRequestDto;
import com.ssafy.ssauction.web.dto.users.UsersUpdateProfileRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UsersController {
    private static final String SUCCESS = "success";

    private static final String FAIL = "fail";
    private final UsersService usersService;
    private final UserImgsService userImgsService;

    private final JwtTokenProvider jwtTokenProvider;

//    @GetMapping("/{userNo}")


    private final HousesService housesService;
    private final LikesService likesService;
    @GetMapping("/users/{userNo}")

    public UsersResponseDto findById(@PathVariable Long userNo) {
        return usersService.findById(userNo);
    }


//    @PostMapping("/join")
//    public Long save(@RequestBody UsersSaveRequestDto requestDto) {
//        return 1L;
//    }
//

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

        Users user = usersService.save(requestDto);
        Long userImgs= userImgsService.save(user);
        System.out.println(userImgs);
        if(user==null){
            return "FAIL";
        }
        return "OK";
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
        System.out.println("\n\n"+userNo+"\n\n");
        try {
            Long delete = usersService.delete(userNo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 1L;
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UsersAuthRequestDto loginInfo) {
        String userEmail = loginInfo.getUserEmail();
        String userPwd = loginInfo.getUserPwd();
        HttpStatus status = null;


        HashMap<String, Object> result = new HashMap<>();

        UsersAuthResponseDto user = usersService.findByUserEmail(userEmail);

//        if (passwordEncoder.matches(userPwd, user.getUserPwd())) {
        if (userPwd.equals(user.getUserPwd())) {
            String refreshToken = jwtTokenProvider.createRefreshToken(userEmail);
            result.put("accessToken", jwtTokenProvider.createAccessToken("userEmail", user.getUserEmail()));
            result.put("refreshToken", refreshToken);
            //refresh token 저장
            usersService.updateRefreshToken(user.getUserNo(), refreshToken);

            result.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;

        } else {
            result.put("message", FAIL);
            status = HttpStatus.ACCEPTED;

        }
        return new ResponseEntity<Map<String, Object>>(result, status);

    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, Object>> validateRefreshToken(@RequestBody String userEmail) {
        HashMap<String, Object> result = new HashMap<>();
        String newAccessToken = jwtTokenProvider.validateRefreshToken(userEmail);
        HttpStatus status = null;

        if (newAccessToken != null) {
            result.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;
        } else {
            result.put("message", FAIL);
            status = HttpStatus.ACCEPTED;
        }

        return new ResponseEntity<Map<String, Object>>(result, status);

    }

    @PostMapping("/users/likes")
    public ResponseEntity<String> createLikes(@RequestBody LikesSaveDto saveDto){
        Users user=usersService.findEntityById(saveDto.getUserNo());
        Houses house=housesService.findEntityById(saveDto.getHouseNo());;
        Likes like=likesService.save(user,house,saveDto);
        user.getLikes().add(like);
        house.getLikes().add(like);
        return new ResponseEntity<>("created", HttpStatus.OK);
    }

    @GetMapping("/users/likes/{userNo}")
    public ResponseEntity<List<HousesResponseDto>> getLikeHouse(@PathVariable Long userNo){
        Users user=usersService.findEntityById(userNo);
        System.out.println(user.toString());
        List<Likes> likes=user.getLikes();
        List<HousesResponseDto> list=new ArrayList<>();
        System.out.println(likes.toString());
        for(Likes like:likes){
            list.add(HousesResponseDto.builder().house(like.getHouse()).build());
        }
        System.out.println("\n\n"+list.toString()+"\n\n");
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

//    @DeleteMapping("/users/likes")
//    public ResponseEntity<String>

}
