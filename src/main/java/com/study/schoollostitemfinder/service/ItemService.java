package com.study.schoollostitemfinder.service;

import com.study.schoollostitemfinder.dto.ItemRequestDto;
import com.study.schoollostitemfinder.dto.ItemResponseDto;
import com.study.schoollostitemfinder.dto.TakeItemRequestDto;
import com.study.schoollostitemfinder.entity.Item;
import com.study.schoollostitemfinder.entity.Student;
import com.study.schoollostitemfinder.entity.TemporaryItem;
import com.study.schoollostitemfinder.exception.BadRequestException;
import com.study.schoollostitemfinder.exception.ImageProcessingException;
import com.study.schoollostitemfinder.exception.NotFoundException;
import com.study.schoollostitemfinder.repository.ItemRepository;
import com.study.schoollostitemfinder.repository.StudentRepository;
import com.study.schoollostitemfinder.repository.TemporaryItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final StudentRepository studentRepository;
    private final TemporaryItemRepository temporaryItemRepository;
    private final ImageService imageService;

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
                .orElseThrow(() -> new NotFoundException("해당하는 아이템은 없습니다"));
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

    // 분실물 수정(관리자)
    @Transactional
    public ItemResponseDto updateItem(Long itemId, ItemRequestDto dto, MultipartFile file) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("해당하는 아이템은 없습니다"));

        String imageUrl = item.getItemImg();

        if (file != null && !file.isEmpty()) {
            try {
                imageUrl = imageService.update(file, item.getItemImg());
            } catch (IOException e) {
                throw new ImageProcessingException("이미지 처리에 실패했습니다.", e);
            }
        }

        item.setItemName(dto.getItemName());
        item.setItemDetail(dto.getItemDetail());
        item.setItemPlace(dto.getItemPlace());
        item.setItemImg(imageUrl);

        ItemResponseDto responseDto = new ItemResponseDto(
                item.getItemId(),
                item.getItemName(),
                item.getItemDetail(),
                item.getItemPlace(),
                imageUrl,
                item.getSignUpAt(),
                item.getTakeAt(),
                item.getStudent()
        );

        return responseDto;
    }

    // 분실물 삭제(관리자)
    @Transactional
    public void deleteItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("해당하는 아이템은 없습니다"));

        try {
            imageService.delete(item.getItemImg());
        } catch (IOException e) {
            throw new ImageProcessingException("이미지 처리에 실패했습니다.", e);
        }

        itemRepository.deleteById(itemId);
    }

    // 분실물 가져가기
    @Transactional
    public ItemResponseDto takeItem(Long itemId, TakeItemRequestDto dto){
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("해당하는 아이템은 없습니다"));

        if(dto.getStudentNumber() < 10000 || dto.getStudentNumber() > 99999) {
            throw new BadRequestException("학번은 5자리 숫자여야 합니다.");
        }

        // 미리 등록된 학생의 학번이 일치하는지 확인
        Student student = studentRepository.findByStudentNumber(dto.getStudentNumber())
                .orElseThrow(() -> new NotFoundException("해당 학번의 학생은 존재하지 않습니다"));
        if (!student.getStudentName().equals(dto.getStudentName())) {
            throw new BadRequestException("학번과 이름이 일치하지 않습니다");
        }

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

    // 가져간 분실물 취소
    @Transactional
    public ItemResponseDto cancelTakeItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("해당하는 아이템은 없습니다"));

        item.setTakeAt(null);
        item.setStudent(null);

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
