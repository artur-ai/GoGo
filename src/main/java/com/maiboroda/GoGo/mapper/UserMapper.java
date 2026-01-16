package com.maiboroda.GoGo.mapper;

import com.maiboroda.GoGo.dto.RegisterRequest;
import com.maiboroda.GoGo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "id", ignore = true)
    User toEntity(RegisterRequest registerRequest);
}
