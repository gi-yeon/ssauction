package com.ssafy.ssauction.service.itemImg;

import com.ssafy.ssauction.domain.itemImgs.ItemImgs;
import com.ssafy.ssauction.domain.itemImgs.ItemImgsRepository;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.web.dto.itemImg.ItemImgsGetResponseDto;
import com.ssafy.ssauction.web.dto.itemImg.ItemImgsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemImgsService {
    private final ItemImgsRepository itemImgsRepository;

    @Transactional
    public ItemImgs save(Items item, ItemImgsSaveRequestDto saveDto){
        ItemImgs img=saveDto.toEntity(item);
        return itemImgsRepository.save(saveDto.toEntity(item));
    }

    public ItemImgsGetResponseDto getImgs(Items item){
        return ItemImgsGetResponseDto.builder().img(itemImgsRepository.findByItem(item).get()).build();
    }
}
