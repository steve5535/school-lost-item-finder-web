package com.study.schoollostitemfinder.controller;

import com.study.schoollostitemfinder.dto.LoginRequestDto;
import com.study.schoollostitemfinder.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    // 로그인
    @PostMapping("/login")
    private String login(@RequestBody LoginRequestDto requestDto) {
        String token = loginService.login(requestDto);

        return token;
    };

    // 회원가입
    @PostMapping("/sign-up")
    private void signUp(@RequestBody LoginRequestDto requestDto){
        loginService.signUp(requestDto);
    }

    
}
