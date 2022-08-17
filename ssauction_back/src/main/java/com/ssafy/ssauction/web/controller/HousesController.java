package com.ssafy.ssauction.web.controller;

import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.itemImgs.ItemImgs;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.service.Items.ItemsService;
import com.ssafy.ssauction.service.houses.HousesService;
import com.ssafy.ssauction.service.itemImg.ItemImgsService;
import com.ssafy.ssauction.service.storage.StorageService;
import com.ssafy.ssauction.service.users.UsersService;
import com.ssafy.ssauction.web.dto.Houses.HousesItemsResponseDto;
import com.ssafy.ssauction.web.dto.Houses.HousesResponseDto;
import com.ssafy.ssauction.web.dto.Houses.HousesSaveRequestDto;
import com.ssafy.ssauction.web.dto.Houses.MyHouseResponseDto;
import com.ssafy.ssauction.web.dto.Items.ItemInfoResponseDto;
import com.ssafy.ssauction.web.dto.Items.ItemsResponseDto;
import com.ssafy.ssauction.web.dto.Items.ItemsSaveRequestDto;
import com.ssafy.ssauction.web.dto.Items.SellItemResponseDto;
import com.ssafy.ssauction.web.dto.itemImg.ImgInfo;
import com.ssafy.ssauction.web.dto.itemImg.ItemImgsDeleteRequestDto;
import com.ssafy.ssauction.web.dto.itemImg.ItemImgsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/houses")
public class HousesController {
    private final UsersService usersService;
    private final HousesService housesService;
    private final ItemsService itemsService;

    private final ItemImgsService itemImgsService;
    private final StorageService storageService;

    @GetMapping("/profile/{userNo}")
    public ResponseEntity<MyHouseResponseDto> myAllHouses(@PathVariable Long userNo) {
        Users user = usersService.findEntityById(userNo);                                       //유저 정보 가져오기
        List<ItemInfoResponseDto> sellList = new ArrayList<>();                                 //판매중인 항목들 가져오기
        for (Items item : user.getSellItems()) {
            int imgSize = item.getImages().size();
            ImgInfo[] imgs=new ImgInfo[imgSize];
            for (int i = 0; i < imgSize; i++) {
                ItemImgs img = item.getImages().get(i);
                String uri = img.getItemImgUri();                              //로컬에 저장되어있는 이미지 가져오기
                byte[] transform = null;                                                              //바이트 코드로 이미지 부르기
                try {
                    File file = new File(System.getProperty("user.dir") + "/imgs/item/" + uri);  //이미지 파일 가져오기
                    FileInputStream inputStream = new FileInputStream(file);                          //파일을 바이트 값으로 저장하기 위한 스트림 생성
                    transform = new byte[(int) file.length()];                                         //바이트 배열 생성
                    inputStream.read(transform);                                                    //바이트 값으로 변환
                    inputStream.close();
                } catch (Exception e) {
                    return null;
                }
                if (transform != null) {
                    imgs[i]=ImgInfo.builder().img(transform.clone()).imgNo(img.getImgNo()).build();
                }
            }
            boolean isNull = false;
            for (int i = 0; i < imgSize; i++) {
                if (imgs[i] == null)
                    isNull = true;
            }
            if (!isNull)
                sellList.add(new ItemInfoResponseDto(SellItemResponseDto.builder().item(item).house(item.getHouse()).build(),
                        imgs));                                                            //리스트에 판매 중인 아이템 저장
        }

        //구매 중인 아이템 정보 가져오는 것도 판매랑 같음
        List<ItemInfoResponseDto> buyList = new ArrayList<>();
        for (Items item : user.getPurchaseItems()) {
            int imgSize = item.getImages().size();
            ImgInfo[] imgs=new ImgInfo[imgSize];
            for (int i = 0; i < imgSize; i++) {
                ItemImgs img = item.getImages().get(i);
                String uri = img.getItemImgUri();
                byte[] transform = null;
                try {
                    File file = new File(System.getProperty("user.dir") + "/imgs/item/" + uri);
                    FileInputStream inputStream = new FileInputStream(file);
                    transform = new byte[(int) file.length()];
                    inputStream.read(transform);
                    inputStream.close();
                } catch (Exception e) {
                    return null;
                }
                if (transform != null) {
                    imgs[i] = ImgInfo.builder().img(transform.clone()).imgNo(img.getImgNo()).build();
                }
            }
            boolean isNull = false;
            for (int i = 0; i < imgSize; i++) {
                if (imgs[i] == null)
                    isNull = true;
            }
            if (!isNull)
                buyList.add(new ItemInfoResponseDto(SellItemResponseDto.builder().item(item).house(item.getHouse()).build(),
                        imgs));
        }

        MyHouseResponseDto resDto = new MyHouseResponseDto(sellList, buyList);                  //보낼 데이터를 Dto로 감싸주기
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION).body(resDto);        //보내기
    }

    @PutMapping("/update/{houseNo}")
    public ResponseEntity<String> updateHouse(@PathVariable Long houseNo,
//                                              @RequestPart(value = "houseUpdateDto") HouseUpdateRequestDto requestDto,
                                              @RequestPart(value = "deleteDto") ItemImgsDeleteRequestDto deleteDto)
//                                              @RequestPart(value = "file") MultipartFile[] files)
    {
        boolean isSuccess = true;

        Houses house = housesService.findEntityById(houseNo);
        Items item = house.getItem();

        itemImgsService.delete(item, deleteDto);

//        for (MultipartFile file : files) {
//            // FileUpload 관련 설정
//            if (file != null && !file.isEmpty()) {                          //  file 데이터가 유효하다면,
//                System.out.println(file.getName());
//                System.out.println(file.getContentType());
//                String originalFileName = file.getOriginalFilename();           //          원본 파일 이름을 알아둔다.
//                if (!originalFileName.isEmpty()) {                              //          원본 파일 이름이 유효하다면,
//                    String saveFileName = UUID.randomUUID().toString()          //              저장용 구분자를 생성한다.
//                            + originalFileName                                  //              원본 파일 이름을 합친다.
//                            .substring(originalFileName.lastIndexOf('.'));  //              원본 파일 확장자를 합친다.
//                    storageService.store(file, saveFileName, "item");       //              위와 같이 생성된 이름으로 된 파일을 생성해 요청받은 file을 저장한다.
//                    isSuccess = itemImgsService.update(item.getItemNo(), ItemImgsUpdateRequestDto.builder()
//                            .imgName(originalFileName)
//                            .imgUri(saveFileName)
//                            .build());   //  itemImgsService를 통해 DB에 ItemImg 정보를 저장한다.
//                }
//            } else {
//                isSuccess = false;
//            }
//        }
        return isSuccess ? new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
    }

    // swagger 2.x 버전에서는 여러 개의 파일을 한 번에 전송하는 요청을 지원하지 않는다ㅠㅠ
    @PostMapping
    public ResponseEntity<Long> createHouse(
            @RequestPart(value = "itemDto") ItemsSaveRequestDto itemDto,          //  House.vue의 item 관련 정보를 받는 객체
            @RequestPart(value = "houseDto") HousesSaveRequestDto houseDto,       //  House.vue의 house 관련 정보를 받는 객체
            @RequestPart(value = "files") MultipartFile[] files) {                //  House.vue의 files를 받는 배열
        System.out.println(itemDto);
        Users user = usersService.findEntityById(itemDto.getUserNo());          //  itemDto에서 현재 사용자의 UserNo를 통해 현재 user를 찾는다.
        //      나중에 JWT 인증 정보로 대체해야 한다.
        System.out.println(itemDto.toString());
        Items item = itemsService.save(user, itemDto);                          //  item에 유저 정보와 item 정보를 등록한다.
        user.getSellItems().add(item);                                          //  user.SellItems에도 해당 item의 정보를 추가한다.

        Houses house = housesService.save(item, houseDto);                      //  house에 item 정보와 house 정보를 등록한다.
        item.setHouse(house);                                                   //  item.house에도 해당 house의 정보를 추가한다.

        // FileUpload 관련 설정
        if (files[0] != null && !files[0].isEmpty()) {                          //  file 데이터가 유효하다면,
            for (MultipartFile file : files) {                                  //      전송받은 file마다,
                String originalFileName = file.getOriginalFilename();           //          원본 파일 이름을 알아둔다.
                if (!originalFileName.isEmpty()) {                              //          원본 파일 이름이 유효하다면,
                    String saveFileUri = UUID.randomUUID().toString()          //              저장용 구분자를 생성한다.
                            + originalFileName                                  //              원본 파일 이름을 합친다.
                            .substring(originalFileName.lastIndexOf('.'));  //              원본 파일 확장자를 합친다.
                    storageService.store(file, saveFileUri, "item");       //              위와 같이 생성된 이름으로 된 파일을 생성해 요청받은 file을 저장한다.
                    ItemImgs img = itemImgsService.save(item, originalFileName, saveFileUri);   //  itemImgsService를 통해 DB에 ItemImg 정보를 저장한다.
                    item.getImages().add(img);
                }
            }
        }

        return new ResponseEntity<>(item.getItemNo(), HttpStatus.OK);
    }

    @GetMapping("/searchAll")
    public ResponseEntity<Map<String, Object>> searchAll(@RequestParam("page") int page,
                                                                  @RequestParam("size") int size,
                                                                  @RequestParam(value = "search", required = false) String search,
                                                                @RequestParam(value="houseStatus", required=false) int houseStatus) {
        Map<String, Object> resultMap = new HashMap<>();
        System.out.println("page : " + page + " size: " + size + " search : " + search + " houseStatus : " + houseStatus);
        HttpStatus status = HttpStatus.OK;
        PageRequest pageRequest = PageRequest.of(page, size);

        resultMap.put("list", housesService.houseList(pageRequest, search, houseStatus));
        System.out.println(resultMap.get("list"));
        return new ResponseEntity<>(resultMap, status);

    }

    @GetMapping("{itemNo}")
    public ResponseEntity<HousesItemsResponseDto> search(@PathVariable Long itemNo) {
        Items item = itemsService.findEntityById(itemNo);
        ItemsResponseDto ir = new ItemsResponseDto(item);
        List<ItemImgs> itemImgs = item.getImages();
        List<ItemImgsResponseDto> iir = new ArrayList<ItemImgsResponseDto>();
        if (itemImgs.size() != 0) {
            for (ItemImgs ii : itemImgs) {
                iir.add(new ItemImgsResponseDto(ii));
            }
        }
        HousesResponseDto hr = new HousesResponseDto(item.getHouse());
        HousesItemsResponseDto result = new HousesItemsResponseDto(ir, iir, hr);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

//    @PutMapping("{houseNo}")
//    public ResponseEntity<HousesItemsResponseDto> modify(@PathVariable Long houseNo, @RequestBody HousesItemsSaveRequestDto requestDto) {
//        Houses house = housesService.modify(houseNo, requestDto.getHouseDto());
//        Items item = itemsService.modify(house.getItem().getItemNo(), requestDto.getItemDto());
//        List<ItemImgsSaveRequestDto> iir = requestDto.getImgDtoList();
//        List<ItemImgs> itemImgs = new ArrayList<>();
//        for (ItemImgsSaveRequestDto r : iir) {
//            itemImgs.add(r.toEntity(item));
//            item.getImages().add(itemImgs);
//        }
//        HousesItemsResponseDto result = new HousesItemsResponseDto(new ItemsResponseDto(item), );
//
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

    @DeleteMapping("{houseNo}")
    public ResponseEntity<String> deleteHouse(@PathVariable Long houseNo) {
        housesService.delete(houseNo);
        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }
}
