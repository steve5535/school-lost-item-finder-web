package com.study.schoollostitemfinder.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    // JWT가 유효한지 확인
    @GetMapping("/auth/check")
    public ResponseEntity<Void> check() {
        return ResponseEntity.ok().build();
    }
}
