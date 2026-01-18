package com.avito.diploma.service;

import com.avito.diploma.entity.Image;
import com.avito.diploma.mapper.ImageMapper;
import com.avito.diploma.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    @Transactional
    public Image uploadImage(MultipartFile file) throws IOException {
        Image image = imageMapper.toEntity(file);
        return imageRepository.save(image);
    }

    public byte[] getImageData(Integer imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Изображение не найдено"));
        return image.getData();
    }

    public String getImageContentType(Integer imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Изображение не найдено"));
        return image.getMediaType();
    }

    @Transactional
    public void deleteImage(Integer imageId) {
        if (imageRepository.existsById(imageId)) {
            imageRepository.deleteById(imageId);
        }
    }
}