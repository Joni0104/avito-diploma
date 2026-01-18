package com.avito.diploma.mapper;

import com.avito.diploma.config.GlobalMapperConfig;
import com.avito.diploma.dto.RegisterDTO;
import com.avito.diploma.dto.UserDTO;
import com.avito.diploma.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = GlobalMapperConfig.class)
public interface UserMapper {

    @Mapping(target = "email", source = "username")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ads", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "image", ignore = true)
    User toEntity(RegisterDTO registerDTO);

    @Mapping(target = "email", source = "email")
    @Mapping(target = "image", expression = "java(getImagePath(user))")
    UserDTO toDTO(User user);

    default String getImagePath(User user) {
        return user.getImage() != null ? "/images/" + user.getImage().getId() : null;
    }
}