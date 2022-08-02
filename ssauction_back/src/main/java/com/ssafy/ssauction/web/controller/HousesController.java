package com.ssafy.ssauction.web.controller;

import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.itemImgs.ItemImgs;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.service.Items.ItemsService;
import com.ssafy.ssauction.service.houses.HousesService;
import com.ssafy.ssauction.service.users.UsersService;
import com.ssafy.ssauction.web.dto.Houses.*;
import com.ssafy.ssauction.web.dto.Items.ItemsResponseDto;
import com.ssafy.ssauction.web.dto.Items.ItemsSaveRequestDto;
import com.ssafy.ssauction.service.itemImg.ItemImgsService;
import com.ssafy.ssauction.service.users.UsersService;
import com.ssafy.ssauction.web.dto.Houses.HousesItemsSaveRequestDto;
import com.ssafy.ssauction.web.dto.itemImg.ItemImgsResponseDto;
import com.ssafy.ssauction.web.dto.itemImg.ItemImgsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/houses")
public class HousesController {
    private final UsersService usersService;
    private final HousesService housesService;
    private final ItemsService itemsService;


    private final ItemImgsService itemImgsService;
    @PostMapping
    public ResponseEntity<String> createHouse(@RequestBody HousesItemsSaveRequestDto requestDto) {
        Users user= usersService.findEntityById(requestDto.getItemDto().getUserNo());
        Items item = itemsService.save(user, requestDto.getItemDto());
        user.getSellItems().add(item);
        List<ItemImgsSaveRequestDto> iir = requestDto.getImgDtoList();
        if(iir != null && iir.size() > 0) {
            for (ItemImgsSaveRequestDto img : iir) {
                item.getImages().add(img.toEntity(item));
            }
        }
        Houses house = housesService.save(item, requestDto.getHouseDto());
        item.setHouse(house);
        return new ResponseEntity<>("created", HttpStatus.OK);
    }

    @GetMapping("/searchAll/{sellerNo}")
    public ResponseEntity<List<HousesItemsResponseDto>> searchAllBySeller(@PathVariable Long sellerNo) {
        Users user = usersService.findEntityById(sellerNo);
        List<Items> itemList=user.getSellItems();

        List<HousesItemsResponseDto> result = new ArrayList<>();
        for (Items item : itemList) {
            ItemsResponseDto ir = new ItemsResponseDto(item);
            Houses house = item.getHouse();
            HousesResponseDto hr = new HousesResponseDto(house);
            List<ItemImgs> itemImgs = item.getImages();
            List<ItemImgsResponseDto> iir = new ArrayList<>();
            for (ItemImgs i : itemImgs) {
                iir.add(new ItemImgsResponseDto(i));
            }
            result.add(new HousesItemsResponseDto(ir, iir, hr));
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
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
