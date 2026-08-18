package com.study.schoollostitemfinder.service;

import com.study.schoollostitemfinder.dto.ItemRequestDto;
import com.study.schoollostitemfinder.dto.ItemResponseDto;
import com.study.schoollostitemfinder.entity.Item;
import com.study.schoollostitemfinder.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    // 분실물 전체 조회
    public List<ItemResponseDto> getItems() {
        List<Item> items = itemRepository.findAll();
        List<ItemResponseDto> responseDtos = new ArrayList<>();
        for(Item item : items) {
            ItemResponseDto responseDto = new ItemResponseDto(
                    item.getItemId(),
                    item.getItemName(),
                    item.getItemDetail(),
                    item.getItemPlace(),
                    item.getItemImg(),
                    item.getSignUpAt(),
                    item.getTakeAt(),
                    item.getStudent()
            );
            responseDtos.add(responseDto);
        };
        return responseDtos;
    }

    // 단건 조회
    public List<ItemResponseDto> getItem(Long itemId) {
        List<Item> items = itemRepository.findById(itemId);

    }

    // 분실물 등록
    @Transactional
    public String singUpItem(ItemRequestDto dto) {
        Item item = new Item(
                dto.getItemName(),
                dto.getItemDetail(),
                dto.getItemPlace(),
                dto.getItemImg()
        );
        itemRepository.save(item);
        return "완료";
    }

}
