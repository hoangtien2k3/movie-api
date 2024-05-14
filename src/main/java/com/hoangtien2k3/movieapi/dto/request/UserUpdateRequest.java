package com.hoangtien2k3.movieapi.dto.request;

import com.hoangtien2k3.movieapi.entity.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    private String name;
    private String username;
    private String password;
    private Set<UserRole> role;
}
