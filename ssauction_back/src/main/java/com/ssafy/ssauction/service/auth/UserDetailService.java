package com.ssafy.ssauction.service.auth;

import com.ssafy.ssauction.domain.users.UsersRepository;
import com.ssafy.ssauction.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        System.out.println("this is userEmail : " + userEmail);
        return usersRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException());
    }
}
