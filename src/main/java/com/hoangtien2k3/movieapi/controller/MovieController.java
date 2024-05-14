package com.hoangtien2k3.movieapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoangtien2k3.movieapi.dto.ApiResponse;
import com.hoangtien2k3.movieapi.dto.MovieDto;
import com.hoangtien2k3.movieapi.dto.MoviePageResponse;
import com.hoangtien2k3.movieapi.exceptions.payload.EmptyFileException;
import com.hoangtien2k3.movieapi.service.MovieService;
import com.hoangtien2k3.movieapi.utils.AppConstants;
import com.hoangtien2k3.movieapi.utils.AppMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movie")
@AllArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add-movie")
    public ResponseEntity<ApiResponse<MovieDto>> addMovieHandler(@RequestPart MultipartFile file,
                                                    @RequestPart String movieDto) throws Exception, EmptyFileException {
        if (file == null) {
            throw new EmptyFileException("File is empty, please send another file !");
        }
        MovieDto dto = convertToMovieDto(movieDto);

        int statusCode = HttpStatus.CREATED.value();
        String message = AppMessage.MOVIE_CREATION_SUCCESS.getMessage();
        return ResponseEntity.status(statusCode)
                .body(buildApiResponse(movieService.addMovie(dto, file), statusCode, message));
    }

    @GetMapping("/{movieId}")
    public ApiResponse<MovieDto> getMovieHandler(@PathVariable Integer movieId) {
        int statusCode = HttpStatus.OK.value();
        String message = AppMessage.SUCCESS.getMessage();
        return buildApiResponse(movieService.getMovie(movieId), statusCode, message);
    }

    @GetMapping("/all")
    public ApiResponse<List<MovieDto>> getAllMovieHandler() {
        int statusCode = HttpStatus.OK.value();
        String message = AppMessage.SUCCESS.getMessage();
        return buildApiResponse(movieService.getAllMovies(), statusCode, message);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{movieId}")
    public ApiResponse<MovieDto> updateMovieHandler(@PathVariable("movieId") Integer movieId,
                                                       @RequestPart MultipartFile file,
                                                       @RequestPart String movieDtoObject) throws Exception {
        if (file.isEmpty()) {
            file = null;
        }
        MovieDto dto = convertToMovieDto(movieDtoObject);

        int statusCode = HttpStatus.OK.value();
        String message = AppMessage.SUCCESS.getMessage();
        return buildApiResponse(movieService.updateMovie(movieId, dto, file), statusCode, message);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{movieId}")
    public ApiResponse<String> deleteMovieHandler(@PathVariable Integer movieId) throws IOException {
        int statusCode = HttpStatus.OK.value();
        String message = AppMessage.SUCCESS.getMessage();
        return buildApiResponse(movieService.deleteMovie(movieId), statusCode, message);
    }

    @GetMapping("/allMoviePage")
    public ApiResponse<MoviePageResponse> getAllMoviePageHandler(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE) Integer pageSize
    ) {
        int statusCode = HttpStatus.OK.value();
        String message = AppMessage.SUCCESS.getMessage();
        return buildApiResponse(movieService.getAllMovieWithPagination(pageNumber, pageSize), statusCode, message);
    }

    @GetMapping("/allMoviePageSort")
    public ApiResponse<MoviePageResponse> getAllMoviePageAndSortingHandler(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.SORT_BY, required = false) String sort,
            @RequestParam(defaultValue = AppConstants.SORT_DIR, required = false) String dir
    ) {
        int statusCode = HttpStatus.OK.value();
        String message = AppMessage.SUCCESS.getMessage();
        return buildApiResponse(movieService.getAllMovieWithPaginationAndSorting(pageNumber, pageSize, sort, dir),
                statusCode, message);
    }

    private MovieDto convertToMovieDto(String movieDtoObject) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        MovieDto movieDto = objectMapper.readValue(movieDtoObject, MovieDto.class);
        return movieDto;
    }

    public <T> ApiResponse<T> buildApiResponse(T response, int status, String message) {
        return ApiResponse.<T>builder()
                .code(status)
                .message(message)
                .result(response)
                .build();
    }

}
