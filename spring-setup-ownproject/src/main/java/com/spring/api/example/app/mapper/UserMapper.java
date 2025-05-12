package com.spring.api.example.app.mapper;

import com.spring.api.example.app.dto.UserRequestDto;
import com.spring.api.example.app.dto.UserResponseDto;
import com.spring.api.example.app.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "username", target = "fullName")
    User toEntity(UserRequestDto userRequestDto);

    @Mapping(source = "fullName", target = "fullName")
    UserResponseDto toUserResponseDto(User user);

    @Mapping(source = "username", target = "fullName")
    void updateUserFromDto(UserRequestDto dto, @MappingTarget User entity);

}
