package com.avito.diploma.service;

import com.avito.diploma.dto.UpdateUserDTO;
import com.avito.diploma.dto.UserDTO;
import com.avito.diploma.entity.Image;
import com.avito.diploma.entity.User;
import com.avito.diploma.exception.UserNotFoundException;
import com.avito.diploma.mapper.UserMapper;
import com.avito.diploma.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ImageService imageService;

    public UserDTO getCurrentUser() {
        User user = getCurrentUserEntity();
        return userMapper.toDTO(user);
    }

    @Transactional
    public UserDTO updateCurrentUser(UpdateUserDTO updateUserDTO) {
        User user = getCurrentUserEntity();

        if (updateUserDTO.getFirstName() != null) {
            user.setFirstName(updateUserDTO.getFirstName());
        }
        if (updateUserDTO.getLastName() != null) {
            user.setLastName(updateUserDTO.getLastName());
        }
        if (updateUserDTO.getPhone() != null) {
            user.setPhone(updateUserDTO.getPhone());
        }

        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Transactional
    public UserDTO updateUserImage(MultipartFile image) throws IOException {
        User user = getCurrentUserEntity();

        // Удаляем старое изображение если есть
        if (user.getImage() != null) {
            imageService.deleteImage(user.getImage().getId());
        }

        // Сохраняем новое изображение
        Image newImage = imageService.uploadImage(image);
        user.setImage(newImage);

        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    public User getCurrentUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден: " + email));
    }
}