package com.study.schoollostitemfinder.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 처리(분실물 없음, 임시 분실물 없음, 학생없음)
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(NotFoundException e) {
        return e.getMessage();
    }

    // 401 처리(로그인 실패)
    @ExceptionHandler(LoginFailedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleLoginFailed(LoginFailedException e) {
        return e.getMessage();
    }

    // 400 처리(학번과 이름이 일치하지 않음)
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequest(BadRequestException e) {
        return e.getMessage();
    }

    // 500(이미지 처리 실패)
    @ExceptionHandler(ImageProcessingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleImageProcession(ImageProcessingException e){
        log.error("이미지 처리 중 오류가 발생했습니다", e);
        return e.getMessage();
    }

    // 500(예상하지 못한 오류들)
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e) {
        log.error("예상하지 못한 서버 오류", e);
        return "서버에서 오류가 발생했습니다.";
    }
}