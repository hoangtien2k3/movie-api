package com.hoangtien2k3.movieapi.service;

import com.hoangtien2k3.movieapi.dto.MovieDto;
import com.hoangtien2k3.movieapi.dto.MoviePageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {
    MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws Exception;
    MovieDto getMovie(Integer movieId);
    List<MovieDto> getAllMovies();
    MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws Exception;
    String deleteMovie(Integer movieId) throws IOException;
    MoviePageResponse getAllMovieWithPagination(Integer pageNumber, Integer pageSize);
    MoviePageResponse getAllMovieWithPaginationAndSorting(Integer pageNumber, Integer pageSize,
                                                 String sortBy, String sortDirection);
}
