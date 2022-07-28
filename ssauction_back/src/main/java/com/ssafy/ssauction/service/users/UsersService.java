package com.ssafy.ssauction.service.users;

import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.domain.users.UsersRepository;
import com.ssafy.ssauction.web.dto.users.UsersFindIdDto;
import com.ssafy.ssauction.web.dto.users.UsersResponseDto;
import com.ssafy.ssauction.web.dto.users.UsersSaveRequestDto;
import com.ssafy.ssauction.web.dto.users.UsersUpdateProfileRequestDto;
import com.ssafy.ssauction.web.dto.users.UsersUpdatePwdDto;

import java.util.NoSuchElementException;


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

    // 아이디 찾기
    public UsersFindIdDto findByPhoneNo(String userPhoneNo) {
        Users entity;
        try {
            entity = usersRepository.findByUserPhoneNo(userPhoneNo).get();
            System.out.println(entity.toString());
        }catch(NoSuchElementException e){
            System.out.println("없음");
            return null;
        }
        return new UsersFindIdDto(entity);
    }

    // 비밀번호 재설정
    @Transactional
    public String updatePwd(String userPhoneNo, String userId, UsersUpdatePwdDto resetPwdDto){
        Users users=usersRepository.findByUserPhoneNo(userPhoneNo).orElseThrow(()->new IllegalArgumentException("해당 계정이 없습니다."));
        users.updatePwd(resetPwdDto.getUserPwd());
        return userId + "님의 비밀번호가 변경되었습니다.";
    }

}
