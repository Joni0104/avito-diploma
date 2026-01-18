package com.avito.diploma.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO для смены пароля")
public class NewPasswordDTO {

    @Schema(description = "текущий пароль", minLength = 8, maxLength = 16)
    @Size(min = 8, max = 16)
    private String currentPassword;

    @Schema(description = "новый пароль", minLength = 8, maxLength = 16)
    @Size(min = 8, max = 16)
    private String newPassword;

    public String getCurrentPassword() { return currentPassword; }
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}