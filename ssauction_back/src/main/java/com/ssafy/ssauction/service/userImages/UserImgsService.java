package com.ssafy.ssauction.service.userImages;

import com.ssafy.ssauction.domain.userImages.UserImgs;
import com.ssafy.ssauction.domain.userImages.UserImgsRepository;
import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.domain.users.UsersRepository;
import com.ssafy.ssauction.web.dto.userImages.UserImgsSaveRequestDto;
import com.ssafy.ssauction.web.dto.userImages.UserImgsUpdateRequestDto;
import com.ssafy.ssauction.web.dto.users.UsersUpdateProfileRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserImgsService {
    private final UserImgsRepository userImgsRepository;
    private final UsersRepository usersRepository;

    @Transactional
    public Long save(Users user) {
        UserImgsSaveRequestDto requestDto = UserImgsSaveRequestDto.builder().build();
        return userImgsRepository.save(requestDto.toEntity(user)).getUserNo();
    }

    @Transactional
    public Long update(Long userNo, UserImgsUpdateRequestDto requestDto){
        UserImgs img=userImgsRepository.findById(userNo).orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다."));
        img.update(requestDto.getImgName(),requestDto.getImgUri());
        return userNo;
    }

    public UserImgs findById(Long userNo) {
        return userImgsRepository.findById(userNo).get();
    }
}
