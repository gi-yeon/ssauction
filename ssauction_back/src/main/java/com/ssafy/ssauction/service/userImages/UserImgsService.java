package com.ssafy.ssauction.service.userImages;

import com.ssafy.ssauction.domain.userImages.UserImgsRepository;
import com.ssafy.ssauction.web.dto.userImages.UserImgsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserImgsService {
    private final UserImgsRepository userImgsRepository;

    @Transactional
    public Long save(UserImgsSaveRequestDto requestDto){
        return userImgsRepository.save(requestDto.toEntity()).getUserNo();
    }
}
