package com.study.schoollostitemfinder.service;

import com.study.schoollostitemfinder.dto.ItemRequestDto;
import com.study.schoollostitemfinder.dto.ItemResponseDto;
import com.study.schoollostitemfinder.dto.TakeItemRequestDto;
import com.study.schoollostitemfinder.entity.Item;
import com.study.schoollostitemfinder.entity.Student;
import com.study.schoollostitemfinder.repository.ItemRepository;
import com.study.schoollostitemfinder.repository.StudentRepository;
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

        return null;
    }

    // 분실물 삭제
    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
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
        return "분실물 등록 완료";
    }

    // 분실물 가져가기
    @Transactional
    public void takeItem(Long itemId, TakeItemRequestDto dto){
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 item은 존재하지 않습니다."));

        Student student = studentRepository.findByStudentNumber(dto.getStudentNumber())
                .orElseThrow(() -> new IllegalArgumentException("해당 학번의 학생은 존재하지 않습니다"));

        item.setStudent(student);
        item.setTakeAt(LocalDateTime.now());
    }

}
