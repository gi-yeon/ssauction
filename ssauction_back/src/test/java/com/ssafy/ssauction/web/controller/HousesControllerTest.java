package com.ssafy.ssauction.web.controller;

import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.itemImgs.ItemImgs;
import com.ssafy.ssauction.domain.items.DealStatus;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.domain.items.Quality;
import com.ssafy.ssauction.domain.userImages.UserImgs;
import com.ssafy.ssauction.domain.userImages.UserImgsRepository;
import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.domain.users.UsersRepository;
import com.ssafy.ssauction.service.Items.ItemsService;
import com.ssafy.ssauction.service.houses.HousesService;
import com.ssafy.ssauction.service.itemImg.ItemImgsService;
import com.ssafy.ssauction.service.storage.StorageService;
import com.ssafy.ssauction.service.userImages.UserImgsService;
import com.ssafy.ssauction.service.users.UsersService;
import com.ssafy.ssauction.web.dto.Houses.HousesSaveRequestDto;
import com.ssafy.ssauction.web.dto.Items.ItemsSaveRequestDto;
import com.ssafy.ssauction.web.dto.userImages.UserImgsSaveRequestDto;
import com.ssafy.ssauction.web.dto.users.UsersSaveRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.Times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HousesControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserImgsRepository userImgsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private StorageService storageService;
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
    @Autowired
    EntityManager em;

    @Test
    @Transactional
    @Rollback(false)
    public void InputItems() throws Exception {
        System.out.println(port);

        //user create
        String email = "giyeon3145@gmail.com";
        String pwd = "giyeon";
        String phoneNo = "01064503145";
        String nickname = "jgy";
        System.out.println("\n\nSTART make user");
        UsersSaveRequestDto requestUserJoinDto = UsersSaveRequestDto.builder()
                .userEmail(email)
                .userPwd(pwd)
                .userPhoneNo(phoneNo)
                .userNickname(nickname)
                .build();
        Users user = usersService.save(requestUserJoinDto);
//        System.out.println(user.toString());
        for (int i = 1; i <= 300; i++) {
            ItemsSaveRequestDto itemDto = ItemsSaveRequestDto.builder()
                    .itemName("item" + i)
                    .itemModelNo("itemModel" + i)
                    .itemStartPrice(i)
                    .itemQuality(Quality.S)
                    .itemDesc("itemDesc" + i)
                    .itemDealStatus(DealStatus.SELL)
                    .build();
            HousesSaveRequestDto houseDto = HousesSaveRequestDto.builder()
                    .houseStatus(0)
                    .houseTitle("houseTitle" + i)
                    .houseAucTime(new Timestamp(System.currentTimeMillis()))
                    .houseDesc("HouseDesc" + i)
                    .build();

            Items item = itemsService.save(user, itemDto);
//            System.out.println(item.toString());
            System.out.println(user.getSellItems().toString());
            user.getSellItems().add(item);
            Houses house = housesService.save(item, houseDto);
            item.setHouse(house);
            MultipartFile[] files = new MultipartFile[2];

            Path path = Paths.get(System.getProperty("user.dir") + "/src/test/java/imgs/" + "1.png").normalize();
            String name = "1.png";
            String originalFileName = "1.png";
            String contentType = "image/png";
            byte[] content = null;
            try {
                content = Files.readAllBytes(path);
            } catch (final IOException e) {
            }
            files[0] = new MockMultipartFile(name,
                    originalFileName, contentType, content);

            path = Paths.get(System.getProperty("user.dir") + "/src/test/java/imgs/" + "2.png").normalize();
            name = "file.txt";
            originalFileName = "2.png";
            contentType = "image/png";
            content = null;
            try {
                content = Files.readAllBytes(path);
            } catch (final IOException e) {
            }
            files[1] = new MockMultipartFile(name,
                    originalFileName, contentType, content);

            for (MultipartFile file : files) {                                  //      전송받은 file마다,
//                System.out.println(Arrays.toString(file.getBytes()));
                originalFileName = file.getOriginalFilename();           //          원본 파일 이름을 알아둔다.
                if (!originalFileName.isEmpty()) {                              //          원본 파일 이름이 유효하다면,
                    String saveFileName = UUID.randomUUID().toString()          //              저장용 구분자를 생성한다.
                            + originalFileName                                  //              원본 파일 이름을 합친다.
                            .substring(originalFileName.lastIndexOf('.'));  //              원본 파일 확장자를 합친다.
                    storageService.store(file, saveFileName, "item");       //              위와 같이 생성된 이름으로 된 파일을 생성해 요청받은 file을 저장한다.
                    ItemImgs img = itemImgsService.save(item, originalFileName, saveFileName);   //  itemImgsService를 통해 DB에 ItemImg 정보를 저장한다.
                    item.getImages().add(img);
                }
            }
        }
        //Post
        String createHouseUrl = "http://localhost:" + port + "/houses";

        //Get
        String getHousesInfoUrl = "http://localhost:" + port + "/houses/profile/" + user.getUserNo();
        System.out.println(getHousesInfoUrl);
    }

    @Order(1)
    @Test
    public void UserImgsRegist() throws Exception {
        //given data
        System.out.println(port);
        String userImgName = "name";
        String userImgUri = "uri";
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