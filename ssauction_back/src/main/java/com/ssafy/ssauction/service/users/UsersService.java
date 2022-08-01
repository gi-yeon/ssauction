package com.ssafy.ssauction.service.users;

import com.ssafy.ssauction.domain.users.Authority;
import com.ssafy.ssauction.domain.users.Users;

import com.ssafy.ssauction.domain.users.UsersRepository;
import com.ssafy.ssauction.web.dto.users.UsersAuthResponseDto;
import com.ssafy.ssauction.web.dto.users.UsersLoginDto;
import com.ssafy.ssauction.web.dto.users.UsersResponseDto;
import com.ssafy.ssauction.web.dto.users.UsersSaveRequestDto;
import com.ssafy.ssauction.web.dto.users.UsersUpdateProfileRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class UsersService {
    private final UsersRepository usersRepository;

    @Transactional
    public Users save(UsersSaveRequestDto requestDto) {
        return usersRepository.save(requestDto.toEntity());
    }

    @Transactional
    public Long updateProfile(Long userNo, UsersUpdateProfileRequestDto requestDto) {
        Users users = usersRepository.findById(userNo).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        users.updateProfile(requestDto.getUserComment(), requestDto.getUserDesc());
        return userNo;
    }
    @Transactional
    public Long updateRefreshToken(Long userNo, String refreshToken) {
        Users users = usersRepository.findById(userNo).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        users.setRefreshToken(refreshToken);
        return userNo;
    }
    @Transactional
    public Long updateRefreshToken(Long userNo, String refreshToken) {
        Users users = usersRepository.findById(userNo).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        users.setRefreshToken(refreshToken);
        return userNo;
    }

    public UsersResponseDto findById(Long userNo) {
        Users entity = usersRepository.findById(userNo).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        return new UsersResponseDto(entity);
    }

    public UsersAuthResponseDto findByUserEmail(String userEmail) {
        System.out.println(userEmail + " here");
        Users entity = usersRepository.findByUserEmail(userEmail).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        return new UsersAuthResponseDto(entity);
    }

    public Users findEntityById(Long userNo) {

        return usersRepository.findById(userNo).get();
    }

    @Transactional
    public Long delete(Long userNo) {
        usersRepository.deleteById(userNo);
        return userNo;
    }

    public UsersResponseDto findUser(UsersLoginDto requestDto) {
        System.out.println(requestDto.toString());
        Users user = null;
        try {
            if (requestDto.getAuthority() == Authority.ROLE_USER) {
                user = usersRepository.findByUserEmailAndUserPwd(requestDto.getLoginEmail(), requestDto.getLoginPwd()).get();
            }
        } catch (NoSuchElementException e) {
            System.out.println("없음");
            e.getMessage();
        }
        UsersResponseDto responseDto = null;

        if (user != null) {
            responseDto=UsersResponseDto.builder()
                    .entity(user)
                    .build();
        }
        return responseDto;
    }
}
