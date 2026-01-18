package com.avito.diploma.mapper;

import com.avito.diploma.config.GlobalMapperConfig;
import com.avito.diploma.dto.CommentDTO;
import com.avito.diploma.dto.CreateOrUpdateCommentDTO;
import com.avito.diploma.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Mapper(config = GlobalMapperConfig.class)
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "ad", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Comment toEntity(CreateOrUpdateCommentDTO createOrUpdateCommentDTO);

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorImage", expression = "java(getAuthorImagePath(comment))")
    @Mapping(target = "createdAt", expression = "java(toEpochMilli(comment.getCreatedAt()))")
    CommentDTO toDTO(Comment comment);

    default String getAuthorImagePath(Comment comment) {
        return comment.getAuthor().getImage() != null ?
                "/images/" + comment.getAuthor().getImage().getId() : null;
    }

    default Long toEpochMilli(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.toInstant(ZoneOffset.UTC).toEpochMilli() : null;
    }
}