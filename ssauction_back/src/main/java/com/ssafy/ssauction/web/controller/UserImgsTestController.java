package com.ssafy.ssauction.web.controller;

import com.ssafy.ssauction.service.userImages.UserImgsService;
import com.ssafy.ssauction.web.dto.userImages.UserImgsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserImgsTestController {
    private final UserImgsService userImgsService;

    @PostMapping("/users/imgs")
    public Long save(@RequestBody UserImgsSaveRequestDto requestDto){
        return userImgsService.save(requestDto);
    }
}
