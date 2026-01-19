package com.avito.diploma.service;

import com.avito.diploma.entity.Image;
import com.avito.diploma.exception.ImageNotFoundException;
import com.avito.diploma.mapper.ImageMapper;
import com.avito.diploma.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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
        image.setFilePath(file.getOriginalFilename());
        return imageRepository.save(image);
    }

    public byte[] getImageData(Integer imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ImageNotFoundException("Изображение не найдено с ID: " + imageId));
        return image.getData();
    }

    public String getImageContentType(Integer imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ImageNotFoundException("Изображение не найдено с ID: " + imageId));

        // Если mediaType не сохранен, определяем по расширению
        if (image.getMediaType() == null || image.getMediaType().isEmpty()) {
            String fileName = image.getFilePath();
            if (fileName != null) {
                if (fileName.toLowerCase().endsWith(".png")) {
                    return MediaType.IMAGE_PNG_VALUE;
                } else if (fileName.toLowerCase().endsWith(".gif")) {
                    return MediaType.IMAGE_GIF_VALUE;
                }
            }
            return MediaType.IMAGE_JPEG_VALUE; // По умолчанию
        }

        return image.getMediaType();
    }

    @Transactional
    public void deleteImage(Integer imageId) {
        if (!imageRepository.existsById(imageId)) {
            throw new ImageNotFoundException("Изображение не найдено с ID: " + imageId);
        }
        imageRepository.deleteById(imageId);
    }
}