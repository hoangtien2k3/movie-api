package com.hoangtien2k3.movieapi.dto.request;

import com.hoangtien2k3.movieapi.entity.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    @NotBlank(message = "The name field can't be blank")
    private String name;

    @NotBlank(message = "The name field can't be blank")
    @Size(min = 5, message = "The username must have at least 5 characters")
    private String username;

    @NotBlank(message = "The name field can't be blank")
    @Email(message = "Please enter email in proper format!")
    private String email;

    @NotBlank(message = "The password field can't be blank")
    @Size(min = 6, message = "The password must have at least 5 characters")
    private String password;

    private Set<UserRole> roles;
}
