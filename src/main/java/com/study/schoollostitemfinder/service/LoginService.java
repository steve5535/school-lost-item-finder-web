package com.study.schoollostitemfinder.service;

import com.study.schoollostitemfinder.dto.LoginRequestDto;
import com.study.schoollostitemfinder.entity.User;
import com.study.schoollostitemfinder.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    // 로그인
    public void login(LoginRequestDto dto, HttpSession session) {
        User user = userRepository.findByUsername(dto.getUserName())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 유저는 없습니다"));

        if(!user.getPassword().equals(dto.getPassword())) {
            throw new RuntimeException("로그인 실패");
        }

        session.setAttribute("LOGIN_USER", user.getUserId());
    }

    // 회원가입
    @Transactional
    public void signUp(LoginRequestDto dto) {
        User user = new User(
                dto.getUserName(),
                dto.getPassword()
        );

        userRepository.save(user);
    }
}
