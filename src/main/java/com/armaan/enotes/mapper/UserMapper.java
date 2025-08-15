package com.armaan.enotes.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.armaan.enotes.dto.UserDto;
import com.armaan.enotes.dto.UserResponse;
import com.armaan.enotes.entity.User;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toEntity(UserDto userDto);

    UserDto toDto(User user);

    UserResponse toResponse(User user);

    List<UserResponse> toResponseList(List<User> users);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    void updateEntityFromDto(UserDto userDto,@MappingTarget User user);
}
