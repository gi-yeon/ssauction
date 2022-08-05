package com.ssafy.ssauction.web.controller;

import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.itemImgs.ItemImgs;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.service.Items.ItemsService;
import com.ssafy.ssauction.service.houses.HousesService;
import com.ssafy.ssauction.service.storage.StorageService;
import com.ssafy.ssauction.service.users.UsersService;
import com.ssafy.ssauction.web.dto.Houses.*;
import com.ssafy.ssauction.web.dto.Items.ItemsResponseDto;
import com.ssafy.ssauction.web.dto.Items.ItemsSaveRequestDto;
import com.ssafy.ssauction.service.itemImg.ItemImgsService;
import com.ssafy.ssauction.web.dto.Houses.HousesItemsSaveRequestDto;
import com.ssafy.ssauction.web.dto.Items.ItemInfoResponseDto;
import com.ssafy.ssauction.web.dto.Items.SellItemResponseDto;
import com.ssafy.ssauction.web.dto.itemImg.ItemImgsGetResponseDto;
import com.ssafy.ssauction.web.dto.itemImg.ItemImgsResponseDto;
import com.ssafy.ssauction.web.dto.itemImg.ItemImgsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONValue;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<MyHouseResponseDto> myAllHouses(@PathVariable Long userNo){
        System.out.println(userNo);
        Users user=usersService.findEntityById(userNo);
        List<ItemInfoResponseDto> sellList=new ArrayList<>();
        for(Items item:user.getSellItems()){
            String uri=item.getImages().get(0).getItemImgUri();
            sellList.add(new ItemInfoResponseDto(SellItemResponseDto.builder().item(item).build(),
                    serveFile(uri,"item")));
        }
        System.out.println(sellList.toString());
        MyHouseResponseDto resDto=null;
        if(sellList!=null)
            resDto=new MyHouseResponseDto(sellList,user.getPurchaseItems());
        if(resDto==null)
            return null;
        return new ResponseEntity<>(resDto,HttpStatus.OK);
    }

    public Resource serveFile(String filename, String type) {
        Resource file = storageService.loadAsResource(filename, type);
        return file;
    }

    // swagger 2.x 버전에서는 여러 개의 파일을 한 번에 전송하는 요청을 지원하지 않는다ㅠㅠ
    @PostMapping
    public ResponseEntity<String> createHouse(
            @RequestPart(value="itemDto") ItemsSaveRequestDto itemDto,          //  House.vue의 item 관련 정보를 받는 객체
            @RequestPart(value="houseDto") HousesSaveRequestDto houseDto,       //  House.vue의 house 관련 정보를 받는 객체
            @RequestPart(value="files") MultipartFile[] files) {                //  House.vue의 files를 받는 배열
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
                    String saveFileName = UUID.randomUUID().toString()          //              저장용 구분자를 생성한다.
                            + originalFileName                                  //              원본 파일 이름을 합친다.
                            .substring(originalFileName.lastIndexOf('.'));  //              원본 파일 확장자를 합친다.
                    storageService.store(file, saveFileName, "item");       //              위와 같이 생성된 이름으로 된 파일을 생성해 요청받은 file을 저장한다.
                    ItemImgs img=itemImgsService.save(item, originalFileName, saveFileName);   //  itemImgsService를 통해 DB에 ItemImg 정보를 저장한다.
                    item.getImages().add(img);
                }
            }
        }

        return new ResponseEntity<>("created", HttpStatus.OK);
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
    public ResponseEntity<String> deleteHouse(@PathVariable Long houseNo){
        housesService.delete(houseNo);
        return new ResponseEntity<>("deleted",HttpStatus.OK);
    }
}
