package com.avito.diploma.mapper;

import com.avito.diploma.config.GlobalMapperConfig;
import com.avito.diploma.dto.AdDTO;
import com.avito.diploma.dto.CreateOrUpdateAdDTO;
import com.avito.diploma.dto.ExtendedAdDTO;
import com.avito.diploma.entity.Ad;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = GlobalMapperConfig.class, uses = {UserMapper.class})
public interface AdMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "comments", ignore = true)
    Ad toEntity(CreateOrUpdateAdDTO createOrUpdateAdDTO);

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "image", expression = "java(getImagePath(ad))")
    AdDTO toDTO(Ad ad);

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorLastName", source = "author.lastName")
    @Mapping(target = "email", source = "author.email")
    @Mapping(target = "phone", source = "author.phone")
    @Mapping(target = "image", expression = "java(getImagePath(ad))")
    ExtendedAdDTO toExtendedDTO(Ad ad);

    default String getImagePath(Ad ad) {
        return ad.getImage() != null ? "/images/" + ad.getImage().getId() : null;
    }
}