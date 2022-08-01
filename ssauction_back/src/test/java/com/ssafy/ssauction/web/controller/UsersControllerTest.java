package com.ssafy.ssauction.web.controller;

import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.itemImgs.ItemImgs;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.domain.userImages.UserImgs;
import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.domain.users.UsersRepository;
import com.ssafy.ssauction.service.Items.ItemsService;
import com.ssafy.ssauction.service.houses.HousesService;
import com.ssafy.ssauction.service.itemImg.ItemImgsService;
import com.ssafy.ssauction.service.userImages.UserImgsService;
import com.ssafy.ssauction.service.users.UsersService;
import com.ssafy.ssauction.web.dto.Houses.HousesItemsSaveRequestDto;
import com.ssafy.ssauction.web.dto.Houses.HousesSaveRequestDto;
import com.ssafy.ssauction.web.dto.Items.ItemsSaveRequestDto;
import com.ssafy.ssauction.web.dto.itemImg.ItemImgsSaveRequestDto;
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
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UsersControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersService usersService;
    @Autowired
    private UserImgsService userImgsService;
    @Autowired
    private ItemsService itemsService;
    @Autowired
    private ItemImgsService itemImgsService;
    @Autowired
    private HousesService housesService;
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

    @Test
    @Transactional
    public void UserMakeHouses() throws Exception{
        String email="giyeon3145@gmail.com";
        String pwd="giyeon";
        String phoneNo="01064503145";
        String nickname="jgy";

        System.out.println("\n\nSTART make user");
        UsersSaveRequestDto requestDto= UsersSaveRequestDto.builder()
                .userEmail(email)
                .userPwd(pwd)
                .userPhoneNo(phoneNo)
                .userNickname(nickname)
                .build();
        Users user = usersService.save(requestDto);
        System.out.println(user.toString());
        System.out.println(user.getUserNo());
        userImgsService.save(user);
        if(user==null){
            System.out.println("FAIL make user");
        }
        System.out.println("SUCCESS make user");
        System.out.println("FINISH makeuser\n\n");

        System.out.println("\n\nSTART make house");
        HousesSaveRequestDto houseDto=HousesSaveRequestDto.builder()
                .houseStatus(0)
                .houseTitle("new house")
                .houseAucTime(new Timestamp(System.currentTimeMillis()))
                .build();
        ItemsSaveRequestDto itemDto=ItemsSaveRequestDto.builder()
                .itemName("item")
                .itemModelNo("item123")
                .itemQuality(3)
                .itemDealStatus(3)
                .itemStartPrice(500000)
                .build();
        ItemImgsSaveRequestDto imgDto=ItemImgsSaveRequestDto.builder()
                .itemImgName("img")
                .itemImgUri("uri")
                .build();
//        Items item = itemsService.save(user, itemDto);
//        user.getSellItems().add(item);
//        if(imgDto!=null) {
//            System.out.println(imgDto.toString());
//            ItemImgs img=itemImgsService.save(item, imgDto);
//            item.getImgs().add(img);
//        }
//        Houses house = housesService.save(item, houseDto);
//        item.setHouse(house);

    }
}