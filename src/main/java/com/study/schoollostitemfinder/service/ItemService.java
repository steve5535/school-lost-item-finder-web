package com.study.schoollostitemfinder.service;

import com.study.schoollostitemfinder.dto.*;
import com.study.schoollostitemfinder.entity.Item;
import com.study.schoollostitemfinder.entity.Student;
import com.study.schoollostitemfinder.exception.BadRequestException;
import com.study.schoollostitemfinder.exception.ImageProcessingException;
import com.study.schoollostitemfinder.exception.NotFoundException;
import com.study.schoollostitemfinder.repository.ItemRepository;
import com.study.schoollostitemfinder.repository.StudentRepository;
import com.study.schoollostitemfinder.repository.TemporaryItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final StudentRepository studentRepository;
    private final ImageService imageService;

    // 분실물 전체 조회
    public List<ItemResponseDto> getItems() {
        List<Item> items = itemRepository.findAll();
        List<ItemResponseDto> responseDtos = new ArrayList<>();
        for(Item item : items) {
            TakeStudentResponseDto studentDto = null;

            if (item.getStudent() != null) {
                studentDto = new TakeStudentResponseDto(
                        item.getStudent().getStudentNumber(),
                        maskName(item.getStudent().getStudentName())
                );
            }

            ItemResponseDto responseDto = new ItemResponseDto(
                    item.getItemId(),
                    item.getItemName(),
                    item.getItemDetail(),
                    item.getItemPlace(),
                    item.getItemImg(),
                    item.getSignUpAt(),
                    item.getTakeAt(),
                    studentDto
            );
            responseDtos.add(responseDto);
        };
        return responseDtos;
    }

    // 단건 조회
    public ItemResponseDto getItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("해당하는 아이템은 없습니다"));

        TakeStudentResponseDto studentDto = null;
        if (item.getStudent() != null) {
            studentDto = new TakeStudentResponseDto(
                    item.getStudent().getStudentNumber(),
                    maskName(item.getStudent().getStudentName())
            );
        }

        ItemResponseDto responseDto = new ItemResponseDto(
                item.getItemId(),
                item.getItemName(),
                item.getItemDetail(),
                item.getItemPlace(),
                item.getItemImg(),
                item.getSignUpAt(),
                item.getTakeAt(),
                studentDto
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

        TakeStudentResponseDto studentDto = null;
        if (item.getStudent() != null) {
            studentDto = new TakeStudentResponseDto(
                    item.getStudent().getStudentNumber(),
                    maskName(item.getStudent().getStudentName())
            );
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
                studentDto
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

        log.info("분실물 삭제 완료 - itemId: {}, itemName: {}", item.getItemId(), item.getItemName());
    }

    // 분실물 가져가기
    @Transactional
    public ItemResponseDto takeItem(Long itemId, TakeStudentRequestDto dto){
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

        TakeStudentResponseDto studentDto = new TakeStudentResponseDto(
                student.getStudentNumber(),
                maskName(student.getStudentName())
        );

        ItemResponseDto responseDto = new ItemResponseDto(
                item.getItemId(),
                item.getItemName(),
                item.getItemDetail(),
                item.getItemPlace(),
                item.getItemImg(),
                item.getSignUpAt(),
                item.getTakeAt(),
                studentDto
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
                null
        );

        return responseDto;
    }

    // 이름 마스킹 함수
    private String maskName(String name) {
        if (name == null || name.length() <= 1) {
            return name;
        }

        if (name.length() == 2) {
            return name.charAt(0) + "*";
        }

        return name.charAt(0)
                + "*".repeat(name.length() - 2)
                + name.charAt(name.length() - 1);
    }
}
