package com.avito.diploma.service;

import com.avito.diploma.dto.LoginDTO;
import com.avito.diploma.dto.NewPasswordDTO;
import com.avito.diploma.dto.RegisterDTO;
import com.avito.diploma.entity.User;
import com.avito.diploma.exception.BadCredentialsException;
import com.avito.diploma.exception.UserAlreadyExistsException;
import com.avito.diploma.mapper.UserMapper;
import com.avito.diploma.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(RegisterDTO registerDTO) {
        if (userRepository.existsByEmail(registerDTO.getUsername())) {
            throw new UserAlreadyExistsException("Пользователь уже существует: " + registerDTO.getUsername());
        }

        User user = userMapper.toEntity(registerDTO);
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        userRepository.save(user);
    }

    @Transactional
    public void changePassword(NewPasswordDTO newPasswordDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Пользователь не найден"));

        if (!passwordEncoder.matches(newPasswordDTO.getCurrentPassword(), user.getPassword())) {
            throw new BadCredentialsException("Неверный текущий пароль");
        }

        user.setPassword(passwordEncoder.encode(newPasswordDTO.getNewPassword()));
        userRepository.save(user);
    }
}