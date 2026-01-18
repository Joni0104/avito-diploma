package com.avito.diploma.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO для создания или обновления объявления")
public class CreateOrUpdateAdDTO {

    @Schema(description = "заголовок объявления", minLength = 4, maxLength = 32)
    @Size(min = 4, max = 32)
    private String title;

    @Schema(description = "цена объявления", minimum = "0", maximum = "10000000")
    @Min(0) @Max(10000000)
    private Integer price;

    @Schema(description = "описание объявления", minLength = 8, maxLength = 64)
    @Size(min = 8, max = 64)
    private String description;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}