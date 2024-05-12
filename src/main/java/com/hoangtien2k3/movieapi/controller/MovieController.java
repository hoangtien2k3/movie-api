package com.hoangtien2k3.movieapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoangtien2k3.movieapi.dto.MovieDto;
import com.hoangtien2k3.movieapi.dto.MoviePageResponse;
import com.hoangtien2k3.movieapi.exceptions.payload.EmptyFileException;
import com.hoangtien2k3.movieapi.service.MovieService;
import com.hoangtien2k3.movieapi.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/add-movie")
    public ResponseEntity<MovieDto> addMovieHandler(@RequestPart MultipartFile file,
                                                    @RequestPart String movieDto) throws Exception, EmptyFileException {
        if (file == null) {
            throw new EmptyFileException("File is empty, please send another file !");
        }
        MovieDto dto = convertToMovieDto(movieDto);
        return new ResponseEntity<>(movieService.addMovie(dto, file), HttpStatus.CREATED);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDto> getMovieHandler(@PathVariable Integer movieId) {
        return ResponseEntity.ok(movieService.getMovie(movieId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<MovieDto>> getAllMovieHandler() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @PutMapping("/update/{movieId}")
    public ResponseEntity<MovieDto> updateMovieHandler(@PathVariable("movieId") Integer movieId,
                                                       @RequestPart MultipartFile file,
                                                       @RequestPart String movieDtoObject) throws Exception {
        if (file.isEmpty()) {
            file = null;
        }
        MovieDto dto = convertToMovieDto(movieDtoObject);
        return ResponseEntity.ok(movieService.updateMovie(movieId, dto, file));
    }

    @DeleteMapping("/delete/{movieId}")
    public ResponseEntity<String> deleteMovieHandler(@PathVariable Integer movieId) throws IOException {
        return ResponseEntity.ok(movieService.deleteMovie(movieId));
    }

    @GetMapping("/allMoviePage")
    public ResponseEntity<MoviePageResponse> getAllMoviePageHandler(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE) Integer pageSize
    ) {
        return ResponseEntity.ok(movieService.getAllMovieWithPagination(pageNumber, pageSize));
    }

    @GetMapping("/allMoviePageSort")
    public ResponseEntity<MoviePageResponse> getAllMoviePageAndSortingHandler(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.SORT_BY, required = false) String sort,
            @RequestParam(defaultValue = AppConstants.SORT_DIR, required = false) String dir
    ) {
        return ResponseEntity.ok(movieService.getAllMovieWithPaginationAndSorting(pageNumber, pageSize, sort, dir));
    }

    private MovieDto convertToMovieDto(String movieDtoObject) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        MovieDto movieDto = objectMapper.readValue(movieDtoObject, MovieDto.class);
        return movieDto;
    }

}
