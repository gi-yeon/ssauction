package com.ssafy.ssauction.service.categories;

import com.ssafy.ssauction.domain.categories.Categories;
import com.ssafy.ssauction.domain.categories.CategoriesRepository;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.web.dto.categories.CategoriesSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoriesService {
    private final CategoriesRepository categoriesRepository;

    public Categories save(Items item, CategoriesSaveRequestDto requestDto){
        return categoriesRepository.save(requestDto.toEntity(item,requestDto));
    }
}
