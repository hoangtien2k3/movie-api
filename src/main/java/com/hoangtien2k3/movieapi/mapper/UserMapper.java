package com.hoangtien2k3.movieapi.mapper;

import com.hoangtien2k3.movieapi.dto.request.RegisterRequest;
import com.hoangtien2k3.movieapi.dto.request.UserUpdateRequest;
import com.hoangtien2k3.movieapi.dto.response.UserResponse;
import com.hoangtien2k3.movieapi.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(RegisterRequest request);
    UserResponse toUserResponse(User user);

    @Mapping(target = "role", ignore = true) // target = "roles", ignore = true: skip mapping roles
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}