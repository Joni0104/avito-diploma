package com.avito.diploma.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO для регистрации пользователя")
public class RegisterDTO {

    @Schema(description = "логин", minLength = 4, maxLength = 32)
    @Size(min = 4, max = 32)
    private String username;

    @Schema(description = "пароль", minLength = 8, maxLength = 16)
    @Size(min = 8, max = 16)
    private String password;

    @Schema(description = "имя пользователя", minLength = 2, maxLength = 16)
    @Size(min = 2, max = 16)
    private String firstName;

    @Schema(description = "фамилия пользователя", minLength = 2, maxLength = 16)
    @Size(min = 2, max = 16)
    private String lastName;

    @Schema(description = "телефон пользователя", pattern = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    private String phone;

    @Schema(description = "роль пользователя")
    private Role role = Role.USER;

    public enum Role {
        USER, ADMIN
    }

    // Геттеры и сеттеры для всех полей
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}