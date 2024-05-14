package com.hoangtien2k3.movieapi.auth.mapper;

import com.hoangtien2k3.movieapi.auth.dto.request.RegisterRequest;
import com.hoangtien2k3.movieapi.auth.dto.response.UserResponse;
import com.hoangtien2k3.movieapi.auth.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(RegisterRequest request);
    UserResponse toUserResponse(User user);

//    @Mapping(target = "roles", ignore = true)
//    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}