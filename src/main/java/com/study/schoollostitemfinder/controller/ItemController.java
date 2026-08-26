package com.study.schoollostitemfinder.controller;

import com.study.schoollostitemfinder.dto.ItemRequestDto;
import com.study.schoollostitemfinder.dto.ItemResponseDto;
import com.study.schoollostitemfinder.dto.TakeItemRequestDto;
import com.study.schoollostitemfinder.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;

    // 전체조회
    @GetMapping("/items")
    public List<ItemResponseDto> getItems() {
        log.info("전체조회 완료");
        return itemService.getItems();
    }

    // 단건조회
    @GetMapping("/items/{itemId}")
    private ItemResponseDto getItem(@PathVariable Long itemId) {
        log.info("단건조회 완료");
        return itemService.getItem(itemId);
    }

    // 수정
    @PatchMapping("/items/{itemId}")
    private ItemResponseDto updateItem(@PathVariable Long itemId, @RequestBody ItemRequestDto requestDto) {
        log.info("분실물 수정 완료");
        return itemService.updateItem(itemId, requestDto);
    }

    // 삭제
    @DeleteMapping("items/{itemId}")
    private void deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        log.info("분실물 삭제 완료");
    }

    // 분실물 가져가기
    @PatchMapping("/items/take/{itemId}")
    public ItemResponseDto takeItem(@PathVariable Long itemId, @RequestBody TakeItemRequestDto requestDto) {
        log.info("분실물 가져가기 완료");
        return itemService.takeItem(itemId, requestDto);
    }


}
