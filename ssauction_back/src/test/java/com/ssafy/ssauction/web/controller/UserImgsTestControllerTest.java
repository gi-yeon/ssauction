package com.ssafy.ssauction.web.controller;

import com.ssafy.ssauction.domain.userImages.UserImgs;
import com.ssafy.ssauction.domain.userImages.UserImgsRepository;
import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.domain.users.UsersRepository;
import com.ssafy.ssauction.web.dto.userImages.UserImgsSaveRequestDto;
import org.apache.catalina.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserImgsTestControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserImgsRepository userImgsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    EntityManager em;
    @Order(1)
    @Test
    public void UserImgsRegist() throws Exception {
        //given data
        System.out.println(port);
        String userImgName="name";
        String userImgUri="uri";
        Long userNo = 5L;

        UserImgsSaveRequestDto requestDto = UserImgsSaveRequestDto.builder()
                .build();
        String url = "http://localhost:" + port + "/users/imgs";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<UserImgs> all = userImgsRepository.findAll();
        Assertions.assertThat(all.get(0).getUserImgName()).isEqualTo(userImgName);
        Assertions.assertThat(all.get(0).getUserImgUri()).isEqualTo(userImgUri);
    }

//    @Test
//    @Transactional
//    @Rollback(value = false)
//    public void save_user_images(){
//        Users user=Users.builder()
//                .userEmail("a")
//                .userPwd("b")
//                .userNickname("c")
//                .userPhoneNo("d")
//                .build();
//        usersRepository.save(user);
//        UserImgs userImgs = new UserImgs();
//        userImgs.setUser(user);
//        userImgsRepository.save(userImgs);
//        System.out.println(user.toString());
//        System.out.println(userImgs.toString());
//        System.out.println("finish");
//    }
}