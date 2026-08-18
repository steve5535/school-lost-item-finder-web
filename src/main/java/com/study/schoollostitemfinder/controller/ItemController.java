package com.study.schoollostitemfinder.controller;

import com.study.schoollostitemfinder.dto.ItemRequestDto;
import com.study.schoollostitemfinder.dto.ItemResponseDto;
import com.study.schoollostitemfinder.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    public final ItemService itemService;

    // 전체조회
    @GetMapping("/items")
    public String getItems() {
        return "d";
    }

    // 단건조회
    @GetMapping("/items/{itemId}")
    private String getItem(@PathVariable Long itemId) {
        return "단건조회";
    }

    // 등록
    @PostMapping("/items")
    public String signUp(@RequestBody ItemRequestDto requestDto) {
        return itemService.singUpItem(requestDto);
    }


}
