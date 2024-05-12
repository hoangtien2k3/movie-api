package com.hoangtien2k3.movieapi.repository;

import com.hoangtien2k3.movieapi.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

}
