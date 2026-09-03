package com.study.schoollostitemfinder.service;

import com.study.schoollostitemfinder.dto.TemporaryItemRequestDto;
import com.study.schoollostitemfinder.dto.TemporaryItemResponseDto;
import com.study.schoollostitemfinder.entity.Item;
import com.study.schoollostitemfinder.entity.TemporaryItem;
import com.study.schoollostitemfinder.repository.ItemRepository;
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
    private final ItemRepository itemRepository;

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

    // 임시 아이템 단건조회
    @Transactional
    public TemporaryItemResponseDto getItem(Long itemId) {
        TemporaryItem item = temporaryItemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 아이템은 없습니다"));

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
        TemporaryItem temporaryItem = temporaryItemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 아이템은 없습니다"));

        temporaryItem.setIsAccept(true);

        // 임시 아이템을 아이템 객체로 변경하고
        Item item = new Item(
                temporaryItem.getItemName(),
                temporaryItem.getItemDetail(),
                temporaryItem.getItemPlace(),
                temporaryItem.getItemImg(),
                temporaryItem.getSignUpAt()
        );

        // 변경된 아이템 객체를 저장
        itemRepository.save(item);

        // 임시 테이블에서 삭제
        temporaryItemRepository.deleteById(itemId);

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
