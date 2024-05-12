//package com.hoangtien2k3.movieapi.auth.config;
//
//import com.hoangtien2k3.movieapi.auth.entities.User;
//import com.hoangtien2k3.movieapi.auth.entities.UserRole;
//import com.hoangtien2k3.movieapi.auth.repositories.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@RequiredArgsConstructor
//public class ApplicationInitConfig {
//
//    private final PasswordEncoder passwordEncoder;
//
//    @Bean
//    ApplicationRunner applicationRunner(UserRepository repository) {
//        return args -> {
//            if (repository.findByUsername("admin").isEmpty()) {
//                UserRole roles = UserRole.ADMIN;
//
//                User user = User.builder()
//                        .name("ADMIN")
//                        .username("admin")
//                        .email("admin@gmail.com")
//                        .password(passwordEncoder.encode("admin"))
//                        .role(roles)
//                        .refreshToken(null)
//                        .build();
//
//                repository.save(user);
//            }
//        };
//    }
//}
