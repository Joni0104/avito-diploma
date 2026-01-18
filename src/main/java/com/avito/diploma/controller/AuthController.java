package com.avito.diploma.controller;

import com.avito.diploma.dto.LoginDTO;
import com.avito.diploma.dto.RegisterDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@Tag(name = "Регистрация и авторизация")
public class AuthController {

    @Operation(
            summary = "Регистрация пользователя",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created"),
                    @ApiResponse(responseCode = "400", description = "Bad Request")
            }
    )
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> register(@RequestBody RegisterDTO registerDTO) {
        // Заглушка - просто возвращаем 201 Created
        System.out.println("Register attempt: " + registerDTO.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Авторизация пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> login(@RequestBody LoginDTO loginDTO) {
        // Заглушка - просто возвращаем 200 OK
        System.out.println("Login attempt: " + loginDTO.getUsername());
        return ResponseEntity.ok().build();
    }
}