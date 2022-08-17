package com.ssafy.ssauction.web.controller;

import com.ssafy.ssauction.domain.categories.Categories;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.service.Items.ItemsService;
import com.ssafy.ssauction.service.categories.CategoriesService;
import com.ssafy.ssauction.web.dto.categories.CategoriesLoadRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CategoriesController {
    private final CategoriesService categoriesService;
    private final ItemsService itemsService;
    @PostMapping("/categories")
    public Long save(@RequestBody CategoriesLoadRequestDto requestDto){
        System.out.println("itemNo : " + requestDto.getItemNo());
        Items item = itemsService.findEntityById(requestDto.getItemNo());
        for(String str:requestDto.getCtgrName()){   // for문을 이용해 복수의 카테고리를 하나씩 저장해주기
            System.out.println(str);
            Categories categories = categoriesService.save(item, str);
            item.getCategories().add(categories);
        }
        System.out.println(item.toString()+"\n\n\n");
        //Categories ctgr = categoriesService.save(item,requestDto);
        //item.getCategories().add(ctgr);
//        return ctgr.getCtgrNo();
        return 1L;
    }
}
