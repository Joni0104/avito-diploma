package com.avito.diploma.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO для создания или обновления комментария")
public class CreateOrUpdateCommentDTO {

    @Schema(description = "текст комментария", minLength = 8, maxLength = 64)
    @Size(min = 8, max = 64)
    private String text;

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
}