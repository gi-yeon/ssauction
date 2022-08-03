package com.ssafy.ssauction.web.controller;

import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.itemImgs.ItemImgs;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.service.Items.ItemsService;
import com.ssafy.ssauction.service.houses.HousesService;
import com.ssafy.ssauction.service.users.UsersService;
import com.ssafy.ssauction.web.dto.Houses.*;
import com.ssafy.ssauction.service.itemImg.ItemImgsService;
import com.ssafy.ssauction.web.dto.Houses.HousesItemsSaveRequestDto;
import com.ssafy.ssauction.web.dto.Items.ItemInfoResponseDto;
import com.ssafy.ssauction.web.dto.Items.SellItemResponseDto;
import com.ssafy.ssauction.web.dto.itemImg.ItemImgsGetResponseDto;
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

    @GetMapping("/{userNo}")
    public ResponseEntity<MyHouseResponseDto> myAllHouses(@PathVariable Long userNo){
        System.out.println(userNo);
        Users user=usersService.findEntityById(userNo);
        List<ItemInfoResponseDto> sellList=new ArrayList<>();
        for(Items item:user.getSellItems()){
            sellList.add(new ItemInfoResponseDto(SellItemResponseDto.builder().item(item).build(),
                    ItemImgsGetResponseDto.builder().img(item.getImages().get(0)).build()));
        }
        System.out.println(sellList.toString());
        MyHouseResponseDto resDto=new MyHouseResponseDto(sellList,user.getPurchaseItems());
        return new ResponseEntity<>(resDto,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createHouse(@RequestBody HousesItemsSaveRequestDto requestDto) {
        Users user= usersService.findEntityById(requestDto.getItemDto().getUserNo());
        Items item = itemsService.save(user, requestDto.getItemDto());
        user.getSellItems().add(item);
        if(requestDto.getImgDto()!=null) {
            System.out.println("\n\n"+requestDto.getImgDto().toString()+"\n\n");
            ItemImgs img= itemImgsService.save(item,requestDto.getImgDto());
            item.getImages().add(img);
        }
        Houses house = housesService.save(item, requestDto.getHouseDto());
        item.setHouse(house);
        return new ResponseEntity<>("created", HttpStatus.OK);
    }
    @DeleteMapping("/{houseNo}")
    public ResponseEntity<String> deleteHouse(@PathVariable Long houseNo){
        housesService.delete(houseNo);
        return new ResponseEntity<>("deleted",HttpStatus.OK);
    }
}
