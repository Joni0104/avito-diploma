package com.avito.diploma.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO для авторизации пользователя")
public class LoginDTO {

    @Schema(description = "логин", minLength = 4, maxLength = 32)
    @Size(min = 4, max = 32)
    private String username;

    @Schema(description = "пароль", minLength = 8, maxLength = 16)
    @Size(min = 8, max = 16)
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}