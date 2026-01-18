package com.avito.diploma.controller;

import com.avito.diploma.dto.CommentDTO;
import com.avito.diploma.dto.CommentsDTO;
import com.avito.diploma.dto.CreateOrUpdateCommentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@Tag(name = "Комментарии")
public class CommentController {

        @Operation(
                summary = "Получение комментариев объявления",
                responses = {
                        @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(schema = @Schema(implementation = CommentsDTO.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Not found")
            }
    )
    @GetMapping("/ads/{id}/comments")
    public ResponseEntity<CommentsDTO> getComments(
            @Parameter(description = "ID объявления", required = true)
            @PathVariable Integer id) {
        // Заглушка - пустой список комментариев
        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setCount(0);
        commentsDTO.setResults(Collections.emptyList());
        return ResponseEntity.ok(commentsDTO);
    }

    @Operation(
            summary = "Добавление комментария к объявлению",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(schema = @Schema(implementation = CommentDTO.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Not found")
            }
    )
    @PostMapping("/ads/{id}/comments")
    public ResponseEntity<CommentDTO> addComment(
            @Parameter(description = "ID объявления", required = true)
            @PathVariable Integer id,
            @RequestBody CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        // Заглушка
        System.out.println("Add comment to ad " + id + ": " + createOrUpdateCommentDTO.getText());

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setPk(1);
        commentDTO.setAuthor(1);
        commentDTO.setAuthorFirstName("Иван");
        commentDTO.setAuthorImage("/images/user1.jpg");
        commentDTO.setCreatedAt(System.currentTimeMillis());
        commentDTO.setText(createOrUpdateCommentDTO.getText());

        return ResponseEntity.ok(commentDTO);
    }

    @Operation(
            summary = "Удаление комментария",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not found")
            }
    )
    @DeleteMapping("/ads/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @Parameter(description = "ID объявления", required = true)
            @PathVariable Integer adId,
            @Parameter(description = "ID комментария", required = true)
            @PathVariable Integer commentId) {
        // Заглушка
        System.out.println("Delete comment " + commentId + " from ad " + adId);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Обновление комментария",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(schema = @Schema(implementation = CommentDTO.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not found")
            }
    )
    @PatchMapping("/ads/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(
            @Parameter(description = "ID объявления", required = true)
            @PathVariable Integer adId,
            @Parameter(description = "ID комментария", required = true)
            @PathVariable Integer commentId,
            @RequestBody CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        // Заглушка
        System.out.println("Update comment " + commentId + " in ad " + adId);

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setPk(commentId);
        commentDTO.setAuthor(1);
        commentDTO.setAuthorFirstName("Иван");
        commentDTO.setAuthorImage("/images/user1.jpg");
        commentDTO.setCreatedAt(System.currentTimeMillis());
        commentDTO.setText(createOrUpdateCommentDTO.getText());

        return ResponseEntity.ok(commentDTO);
    }
}