package com.study.schoollostitemfinder.service;

import com.study.schoollostitemfinder.dto.ItemRequestDto;
import com.study.schoollostitemfinder.dto.ItemResponseDto;
import com.study.schoollostitemfinder.dto.TakeItemRequestDto;
import com.study.schoollostitemfinder.entity.Item;
import com.study.schoollostitemfinder.entity.Student;
import com.study.schoollostitemfinder.entity.TemporaryItem;
import com.study.schoollostitemfinder.repository.ItemRepository;
import com.study.schoollostitemfinder.repository.StudentRepository;
import com.study.schoollostitemfinder.repository.TemporaryItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final StudentRepository studentRepository;
    private final TemporaryItemRepository temporaryItemRepository;

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
    public ItemResponseDto getItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 item은 존재하지 않습니다."));
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
        return responseDto;
    }

    // 분실물 수정
    @Transactional
    public ItemResponseDto updateItem(Long itemId, ItemRequestDto dto) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 item은 존재하지 않습니다."));

        item.setItemName(dto.getItemName());
        item.setItemDetail(dto.getItemDetail());
        item.setItemPlace(dto.getItemPlace());
        item.setItemImg(dto.getItemImg());

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

        return responseDto;
    }

    // 분실물 삭제
    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    // 임시 아이템에서 아이템으로 변경한 분실물 등록
    @Transactional
    public ItemResponseDto singUpItem(Long temporaryItem) {
        TemporaryItem TemporaryItem = temporaryItemRepository.findById(temporaryItem)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이템은 없습니다."));

        Item item = new Item(
                TemporaryItem.getItemName(),
                TemporaryItem.getItemDetail(),
                TemporaryItem.getItemPlace(),
                TemporaryItem.getItemImg(),
                TemporaryItem.getSignUpAt()
        );
        itemRepository.save(item);

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

        return responseDto;
    }

    // 분실물 가져가기
    @Transactional
    public ItemResponseDto takeItem(Long itemId, TakeItemRequestDto dto){
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 item은 존재하지 않습니다."));

        Student student = studentRepository.findByStudentNumber(dto.getStudentNumber())
                .orElseThrow(() -> new IllegalArgumentException("해당 학번의 학생은 존재하지 않습니다"));

        item.setStudent(student);
        item.setTakeAt(LocalDateTime.now());

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

        return responseDto;
    }

}
