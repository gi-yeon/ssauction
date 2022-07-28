package com.ssafy.ssauction.service.Items;

import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.domain.items.ItemsRepository;
import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.domain.users.UsersRepository;
import com.ssafy.ssauction.web.dto.Items.ItemsSaveRequestDto;
import com.ssafy.ssauction.web.dto.userImages.UserImgsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ItemsService {
    private final ItemsRepository itemsRepository;

    public Items findEntityById(Long itemNo){
        return itemsRepository.findById(itemNo).get();
    }
    @Transactional
    public Items save(Users user, ItemsSaveRequestDto requestDto) {
        Items item=requestDto.toEntity(user);
        return itemsRepository.save(item);
    }

    public void delete(Long itemNo) {
        itemsRepository.deleteById(itemNo);
    }
}
