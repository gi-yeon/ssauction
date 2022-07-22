package com.ssafy.ssauction.service.users;

import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.domain.users.UsersRepository;
import com.ssafy.ssauction.web.dto.users.UsersResponseDto;
import com.ssafy.ssauction.web.dto.users.UsersSaveRequestDto;
import com.ssafy.ssauction.web.dto.users.UsersUpdateProfileRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UsersService {
    private final UsersRepository usersRepository;

    @Transactional
    public Long save(UsersSaveRequestDto requestDto){
        return usersRepository.save(requestDto.toEntity()).getUserNo();
    }

    @Transactional
    public Long updateProfile(Long userNo, UsersUpdateProfileRequestDto requestDto){
        Users users=usersRepository.findById(userNo).orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다."));
        users.updateProfile(requestDto.getUserComment(),requestDto.getUserDesc());
        return userNo;
    }

    public UsersResponseDto findById(Long userNo) {
        Users entity=usersRepository.findById(userNo).orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다."));
        return new UsersResponseDto(entity);
    }
    @Transactional
    public Long delete(Long userNo) {
        usersRepository.deleteById(userNo);
        return userNo;
    }
}
