package com.hoangtien2k3.movieapi.dto.response;

import com.hoangtien2k3.movieapi.entity.user.UserRole;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    private Integer id;
    private String name;
    private String username;
    private String email;
    private Set<UserRole> role;
}
