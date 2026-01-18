package com.avito.diploma.controller;

import com.avito.diploma.dto.NewPasswordDTO;
import com.avito.diploma.dto.UpdateUserDTO;
import com.avito.diploma.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
@Tag(name = "Пользователи")
public class UserController {

    @Operation(
            summary = "Обновление пароля",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @PostMapping("/set_password")
    public ResponseEntity<Void> setPassword(@RequestBody NewPasswordDTO newPasswordDTO) {
        // Заглушка
        System.out.println("Password change attempt");
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Получение информации об авторизованном пользователе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(schema = @Schema(implementation = UserDTO.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getUser() {
        // Заглушка - возвращаем пустого пользователя
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setEmail("user@example.com");
        userDTO.setFirstName("Иван");
        userDTO.setLastName("Иванов");
        userDTO.setPhone("+79991234567");
        userDTO.setRole(com.avito.diploma.dto.RegisterDTO.Role.USER);
        userDTO.setImage("/images/user1.jpg");
        return ResponseEntity.ok(userDTO);
    }

    @Operation(
            summary = "Обновление информации об авторизованном пользователе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(schema = @Schema(implementation = UpdateUserDTO.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @PatchMapping("/me")
    public ResponseEntity<UpdateUserDTO> updateUser(@RequestBody UpdateUserDTO updateUserDTO) {
        // Заглушка - возвращаем то же самое DTO
        System.out.println("Update user: " + updateUserDTO.getFirstName() + " " + updateUserDTO.getLastName());
        return ResponseEntity.ok(updateUserDTO);
    }

    @Operation(
            summary = "Обновление аватара авторизованного пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateUserImage(@RequestParam("image") MultipartFile image) {
        // Заглушка
        System.out.println("Update user image: " + image.getOriginalFilename());
        return ResponseEntity.ok().build();
    }
}