package com.jabutividade.backEnd.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.jabutividade.backEnd.dto.SignUpDto;
import com.jabutividade.backEnd.dto.UserDto;
import com.jabutividade.backEnd.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);   

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);
}
