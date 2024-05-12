package com.hoangtien2k3.movieapi.exceptions;

import com.hoangtien2k3.movieapi.exceptions.payload.EmptyFileException;
import com.hoangtien2k3.movieapi.exceptions.payload.FileExistsException;
import com.hoangtien2k3.movieapi.exceptions.payload.MovieNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = { MovieNotFoundException.class })
    public ProblemDetail handlerMovieNotFoundException(MovieNotFoundException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(value = {FileExistsException.class })
    public ProblemDetail handlerFileExistsException(FileExistsException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(value = {EmptyFileException.class })
    public ProblemDetail handlerEmptyFileException(EmptyFileException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }
}
