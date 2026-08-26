package com.study.schoollostitemfinder.service;

import com.study.schoollostitemfinder.dto.LoginRequestDto;
import com.study.schoollostitemfinder.entity.User;
import com.study.schoollostitemfinder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    @Transactional
    public void signUp(LoginRequestDto dto) {
        User user = new User(
                dto.getUserName(),
                dto.getPassword()
        );

        userRepository.save(user);
    }
}
