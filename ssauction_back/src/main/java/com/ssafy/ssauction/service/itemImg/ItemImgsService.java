package com.ssafy.ssauction.service.itemImg;

import com.ssafy.ssauction.domain.itemImgs.ItemImgs;
import com.ssafy.ssauction.domain.itemImgs.ItemImgsRepository;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.web.dto.itemImg.ItemImgsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemImgsService {
    private final ItemImgsRepository itemImgsRepository;

    public ItemImgs save(Items item, ItemImgsSaveRequestDto saveDto){
        return itemImgsRepository.save(saveDto.toEntity(item));
    }
}
