package com.avito.diploma.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "DTO для списка объявлений")
public class AdsDTO {

    @Schema(description = "общее количество объявлений")
    private Integer count = 0;

    @Schema(description = "список объявлений")
    private List<AdDTO> results;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<AdDTO> getResults() {
        return results;
    }

    public void setResults(List<AdDTO> results) {
        this.results = results;
        this.count = results != null ? results.size() : 0;
    }
}