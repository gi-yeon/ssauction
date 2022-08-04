package com.ssafy.ssauction.service.categories;

import com.ssafy.ssauction.domain.categories.Categories;
import com.ssafy.ssauction.domain.categories.CategoriesRepository;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.web.dto.Items.ItemsSaveRequestDto;
import com.ssafy.ssauction.web.dto.categories.CategoriesSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoriesService {
    private final CategoriesRepository categoriesRepository;

    public Categories save(Items item, String str) {  // 새로운 DTO 만들어서 바꾸기
        Categories categories = Categories.builder()
                .ctgrName(str)
                .item(item)
                .build();
        return categoriesRepository.save(categories);
    }

//    public Categories save(Items item, CategoriesSaveRequestDto requestDto){
//        Categories ctgr = requestDto.toEntity(item);
//        return categoriesRepository.save(requestDto.toEntity(item,requestDto));
//    }
}
