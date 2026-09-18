package com.study.schoollostitemfinder.controller;

import com.study.schoollostitemfinder.dto.TemporaryItemRequestDto;
import com.study.schoollostitemfinder.dto.TemporaryItemResponseDto;
import com.study.schoollostitemfinder.service.TemporaryItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TemporaryItemController {

    private final TemporaryItemService temporaryItemService;

    // 조회
    @GetMapping("/temporary-item")
    public List<TemporaryItemResponseDto> getItems() {
        return temporaryItemService.getItems();
    }

    // 단건조회
    @GetMapping("/temporary-item/{itemId}")
    public TemporaryItemResponseDto getItem(@PathVariable Long itemId) {
        return temporaryItemService.getItem(itemId);
    }

    // 등록
    @PostMapping("/temporary-item")
    public TemporaryItemResponseDto singUp(
            @RequestPart("request") TemporaryItemRequestDto requestDto,
            @RequestPart(value = "file", required = false) MultipartFile file
            ) {
        return temporaryItemService.singUpItem(requestDto, file);
    }

    // 수락
    @PatchMapping("/temporary-item/accept/{itemId}")
    public TemporaryItemResponseDto acceptState(@PathVariable Long itemId) {
        return temporaryItemService.acceptState(itemId);
    }

    // 거절
    @PatchMapping("/temporary-item/decline/{itemId}")
    public TemporaryItemResponseDto declineState(@PathVariable Long itemId) {
        return temporaryItemService.declineState(itemId);
    }

    // 삭제
    @DeleteMapping("/temporary-item/{itemId}")
    public void deleteItem(@PathVariable Long itemId) {
        temporaryItemService.deleteItem(itemId);
    }
}
