package com.study.schoollostitemfinder.service;

import com.study.schoollostitemfinder.dto.TemporaryItemRequestDto;
import com.study.schoollostitemfinder.dto.TemporaryItemResponseDto;
import com.study.schoollostitemfinder.entity.TemporaryItem;
import com.study.schoollostitemfinder.repository.TemporaryItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TemporaryItemService {

    private final TemporaryItemRepository temporaryItemRepository;

    // 임시 아이템 전체 조회
    public List<TemporaryItemResponseDto> getItems() {
        List<TemporaryItem> items = temporaryItemRepository.findAll();
        List<TemporaryItemResponseDto> responseDtos = new ArrayList<>();
        for(TemporaryItem item : items) {
            TemporaryItemResponseDto responseDto = new TemporaryItemResponseDto(
                    item.getTemporaryItemId(),
                    item.getItemName(),
                    item.getItemDetail(),
                    item.getItemPlace(),
                    item.getItemImg(),
                    item.getIsAccept(),
                    item.getSignUpAt()
            );
            responseDtos.add(responseDto);
        }
        return responseDtos;
    }

    // 임시 아이템 등록
    @Transactional
    public TemporaryItemResponseDto singUpItem(TemporaryItemRequestDto dto) {
        TemporaryItem temporaryItem = new TemporaryItem(
                dto.getItemName(),
                dto.getItemDetail(),
                dto.getItemPlace(),
                dto.getItemImg(),
                dto.getIsAccept()
        );

        temporaryItemRepository.save(temporaryItem);

        TemporaryItemResponseDto responseDto = new TemporaryItemResponseDto(
                temporaryItem.getTemporaryItemId(),
                temporaryItem.getItemName(),
                temporaryItem.getItemDetail(),
                temporaryItem.getItemPlace(),
                temporaryItem.getItemImg(),
                temporaryItem.getIsAccept(),
                temporaryItem.getSignUpAt()
        );
        return responseDto;
    }

    // 아이템 수락
    @Transactional
    public TemporaryItemResponseDto acceptState(Long itemId) {
        TemporaryItem item = temporaryItemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 아이템은 없습니다"));

        item.setIsAccept(true);

        TemporaryItemResponseDto responseDto = new TemporaryItemResponseDto(
                item.getTemporaryItemId(),
                item.getItemName(),
                item.getItemDetail(),
                item.getItemPlace(),
                item.getItemImg(),
                item.getIsAccept(),
                item.getSignUpAt()
        );
        return responseDto;
    };

    // 아이템 거절
    @Transactional
    public TemporaryItemResponseDto declineState(Long itemId) {
        TemporaryItem item = temporaryItemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 아이템은 없습니다"));

        item.setIsAccept(false);

        TemporaryItemResponseDto responseDto = new TemporaryItemResponseDto(
                item.getTemporaryItemId(),
                item.getItemName(),
                item.getItemDetail(),
                item.getItemPlace(),
                item.getItemImg(),
                item.getIsAccept(),
                item.getSignUpAt()
        );
        return responseDto;
    }

    // 아이템 삭제
    @Transactional
    public void deleteItem(Long itemId) {
        temporaryItemRepository.deleteById(itemId);
    }
}
