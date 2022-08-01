package com.ssafy.ssauction.web.controller;

import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.likes.Likes;
import com.ssafy.ssauction.domain.userImages.UserImgs;
import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.service.houses.HousesService;
import com.ssafy.ssauction.service.likes.LikesService;
import com.ssafy.ssauction.service.userImages.UserImgsService;
import com.ssafy.ssauction.service.users.UsersService;
import com.ssafy.ssauction.web.dto.Houses.HousesResponseDto;
import com.ssafy.ssauction.web.dto.likes.LikesSaveDto;
import com.ssafy.ssauction.web.dto.userImages.UserImgsSaveRequestDto;
import com.ssafy.ssauction.web.dto.userImages.UserImgsUpdateRequestDto;
import com.ssafy.ssauction.web.dto.users.UsersLoginDto;
import com.ssafy.ssauction.web.dto.users.UsersResponseDto;
import com.ssafy.ssauction.web.dto.users.UsersSaveRequestDto;
import com.ssafy.ssauction.web.dto.users.UsersUpdateProfileRequestDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UsersController {
    private final UsersService usersService;
    private final UserImgsService userImgsService;
    private final HousesService housesService;
    private final LikesService likesService;
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
        Users user = usersService.save(requestDto);
        Long userImgs= userImgsService.save(user);
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
