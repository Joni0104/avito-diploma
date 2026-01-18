package com.avito.diploma.mapper;

import com.avito.diploma.config.GlobalMapperConfig;
import com.avito.diploma.entity.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Mapper(config = GlobalMapperConfig.class)
public interface ImageMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "filePath", ignore = true)
    @Mapping(target = "fileSize", expression = "java(file.getSize())")
    @Mapping(target = "mediaType", expression = "java(file.getContentType())")
    @Mapping(target = "data", expression = "java(getBytes(file))")
    @Mapping(target = "ad", ignore = true)
    @Mapping(target = "user", ignore = true)
    Image toEntity(MultipartFile file) throws IOException;

    default byte[] getBytes(MultipartFile file) throws IOException {
        return file.getBytes();
    }
}