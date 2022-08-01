package com.ssafy.ssauction.web.controller;

import com.ssafy.ssauction.domain.categories.Categories;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.service.Items.ItemsService;
import com.ssafy.ssauction.service.categories.CategoriesService;
import com.ssafy.ssauction.web.dto.categories.CategoriesSaveRequestDto;
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
    public Long save(@RequestBody CategoriesSaveRequestDto requestDto){
        Items item=itemsService.findEntityById(requestDto.getItemNo());
        Categories ctgr=categoriesService.save(item,requestDto);
        item.getCategories().add(ctgr);
        return ctgr.getCtgrNo();
    }
}
