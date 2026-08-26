package com.study.schoollostitemfinder.controller;

import com.study.schoollostitemfinder.dto.LoginRequestDto;
import com.study.schoollostitemfinder.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/sign-up")
    private String signUp(@RequestBody LoginRequestDto requestDto){
        loginService.signUp(requestDto);

        return "회원가입 완료";
    }

    
}
