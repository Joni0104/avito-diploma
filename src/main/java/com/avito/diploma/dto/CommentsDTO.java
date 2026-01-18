package com.avito.diploma.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "DTO для списка комментариев")
public class CommentsDTO {

    @Schema(description = "общее количество комментариев")
    private Integer count = 0;

    @Schema(description = "список комментариев")
    private List<CommentDTO> results;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<CommentDTO> getResults() {
        return results;
    }

    public void setResults(List<CommentDTO> results) {
        this.results = results;
        this.count = results != null ? results.size() : 0;
    }
}