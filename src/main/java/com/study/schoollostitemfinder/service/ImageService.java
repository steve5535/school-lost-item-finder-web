package com.study.schoollostitemfinder.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService {
    private String uploadDir = "C:/server/uploads";

    // 이미지 저장
    public String save(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        String originalFilename = file.getOriginalFilename();
        String extension = "";

        if(originalFilename != null && originalFilename.contains(".")){
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String fileName = UUID.randomUUID() + extension;

        Path resolve = uploadPath.resolve(fileName);
        file.transferTo(resolve);

        return "/image/" + fileName;
    }

    // 이미지 삭제
    public void delete(String imageUrl) throws IOException {

        String originFileName = Paths.get(imageUrl).getFileName().toString();

        Path filePath = Paths.get(uploadDir).resolve(originFileName);

        Files.deleteIfExists(filePath);
    }

    // 이미지 수정
    public String update(MultipartFile file, String imageUrl) throws IOException {
        String originFileName = Paths.get(imageUrl).getFileName().toString();

        Path filePath = Paths.get(uploadDir).resolve(originFileName);

        Files.deleteIfExists(filePath);

        Path uploadPath = Paths.get(uploadDir);

        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        String originalFilename = file.getOriginalFilename();
        String extension = "";

        if(originalFilename != null && originalFilename.contains(".")){
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String fileName = UUID.randomUUID() + extension;

        Path resolve = uploadPath.resolve(fileName);
        file.transferTo(resolve);

        return "/image/" + fileName;
    }
}
