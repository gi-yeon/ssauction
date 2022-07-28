package com.ssafy.ssauction.service.likes;

import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.likes.Likes;
import com.ssafy.ssauction.domain.likes.LikesRepository;
import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.web.dto.likes.LikesSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {
    private final LikesRepository likesRepository;

    @Transactional
    public Likes save(Users user, Houses house, LikesSaveDto saveDto){
        return likesRepository.save(saveDto.toEntity(user,house));
    }
}
