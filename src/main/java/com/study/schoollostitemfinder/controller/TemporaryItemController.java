package com.study.schoollostitemfinder.controller;

import com.study.schoollostitemfinder.dto.TemporaryItemRequestDto;
import com.study.schoollostitemfinder.dto.TemporaryItemResponseDto;
import com.study.schoollostitemfinder.service.TemporaryItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TemporaryItemController {

    private final TemporaryItemService temporaryItemService;

    // 조회
    @GetMapping("/temporary-item")
    public List<TemporaryItemResponseDto> getItems() {
        return temporaryItemService.getItems();
    }

    // 등록
    @PostMapping("/temporary-item")
    public TemporaryItemResponseDto singUp(@RequestBody TemporaryItemRequestDto requestDto) {
        log.info("임시 아이템 등록 완료");
        return temporaryItemService.singUpItem(requestDto);
    }

    // 수락
    @PatchMapping("/temporary-item/accept/{itemId}")
    public TemporaryItemResponseDto acceptState(@PathVariable Long itemId) {
        log.info("임시 아이템 수락 완료");
        return temporaryItemService.acceptState(itemId);
    }

    // 거절
    @PatchMapping("/temporary-item/decline/{itemId}")
    public TemporaryItemResponseDto declineState(@PathVariable Long itemId) {
        log.info("임시 아이템 거절 완료");
        return temporaryItemService.declineState(itemId);
    }

    // 삭제
    @DeleteMapping("/temporary-item/{itemId}")
    public void deleteItem(@PathVariable Long itemId) {
        log.info("임시 아이템 삭제 완료");
        temporaryItemService.deleteItem(itemId);
    }
}
