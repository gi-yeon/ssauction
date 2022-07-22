package com.ssafy.ssauction.web.controller;

import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.domain.users.UsersRepository;
import com.ssafy.ssauction.web.dto.users.UsersSaveRequestDto;
import com.ssafy.ssauction.web.dto.users.UsersUpdateProfileRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UsersControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UsersRepository usersRepository;
    @Order(1)
    @Test
    public void UsersRegist() throws Exception {
        //given data
        System.out.println(port);
        String email="giyeon3145@gmail.com";
        String pwd="giyeon";
        String phoneNo="01064503145";
        String nickname="jgy";
        UsersSaveRequestDto requestDto = UsersSaveRequestDto.builder()
                .userEmail(email)
                .userNickname(nickname)
                .userPwd(pwd)
                .userPhoneNo(phoneNo)
                .build();
        String url = "http://localhost:" + port + "/users/";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Users> all = usersRepository.findAll();
        Assertions.assertThat(all.get(0).getUserEmail()).isEqualTo(email);
        Assertions.assertThat(all.get(0).getUserNickname()).isEqualTo(nickname);
        Assertions.assertThat(all.get(0).getUserPwd()).isEqualTo(pwd);
        Assertions.assertThat(all.get(0).getUserPhoneNo()).isEqualTo(phoneNo);
    }

    @Order(2)
    @Test
    public void UsersUpdate() throws Exception{
        //init data
        usersRepository.deleteAll();

        //given data
        System.out.println(port);
        String email="giyeon3145@gmail.com";
        String pwd="giyeon";
        String phoneNo="01064503145";
        String nickname="jgy";
        UsersSaveRequestDto saveDto=UsersSaveRequestDto.builder()
                .userEmail(email)
                .userNickname(nickname)
                .userPwd(pwd)
                .userPhoneNo(phoneNo)
                .build();
        Users savedUser = usersRepository.save(saveDto.toEntity());
        Long updateId=savedUser.getUserNo();
        String expectedComment="hi";
        String expectedDesc="nice to meet you";
        UsersUpdateProfileRequestDto requestDto=UsersUpdateProfileRequestDto.builder()
                .userComment(expectedComment)
                .userDesc(expectedDesc)
                .build();

        String url = "http://localhost:" + port + "/users/profile/"+updateId;
        HttpEntity<UsersUpdateProfileRequestDto> requestEntity=new HttpEntity<>(requestDto);
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Users> all = usersRepository.findAll();
        Assertions.assertThat(all.get(0).getUserComment()).isEqualTo(expectedComment);
        Assertions.assertThat(all.get(0).getUserDesc()).isEqualTo(expectedDesc);
    }

}