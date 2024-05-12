package com.hoangtien2k3.movieapi.auth.repositories;

import com.hoangtien2k3.movieapi.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
