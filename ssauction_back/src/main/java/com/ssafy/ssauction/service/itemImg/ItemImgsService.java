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
    public ItemImgs save(Items item,String originalUri, String saveUri){
        ItemImgs itemImg =
                ItemImgs.builder()
                        .itemImgName(originalUri)
                        .itemImgUri(saveUri)
                        .isMain(false)
                        .item(item)
                        .build();
        itemImg.setItem(item);
        return itemImgsRepository.save(itemImg);
    }

    public ItemImgsGetResponseDto getImgs(Items item){
        return ItemImgsGetResponseDto.builder().img(itemImgsRepository.findByItem(item).get()).build();
    }
}
