package com.avito.diploma.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO для обновления информации о пользователе")
public class UpdateUserDTO {

    @Schema(description = "имя пользователя", minLength = 3, maxLength = 10)
    @Size(min = 3, max = 10)
    private String firstName;

    @Schema(description = "фамилия пользователя", minLength = 3, maxLength = 10)
    @Size(min = 3, max = 10)
    private String lastName;

    @Schema(description = "телефон пользователя", pattern = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    private String phone;

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
