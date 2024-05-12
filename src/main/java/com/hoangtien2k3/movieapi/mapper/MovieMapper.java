package com.hoangtien2k3.movieapi.mapper;

import com.hoangtien2k3.movieapi.dto.MovieDto;
import com.hoangtien2k3.movieapi.entity.Movie;

public interface MovieMapper {
    static Movie map(MovieDto movieDto) {
        if (movieDto == null) {
            return null;
        }
        return Movie.builder()
                .movieId(movieDto.getMovieId())
                .title(movieDto.getTitle())
                .directory(movieDto.getDirectory())
                .studio(movieDto.getStudio())
                .movieCast(movieDto.getMovieCast())
                .releaseYear(movieDto.getReleaseYear())
                .poster(movieDto.getPoster())
                .build();
    }

    static MovieDto map(Movie movie, String posterUrl) {
        if (movie == null) {
            return null;
        }
        return MovieDto.builder()
                .movieId(movie.getMovieId())
                .title(movie.getTitle())
                .directory(movie.getDirectory())
                .studio(movie.getStudio())
                .movieCast(movie.getMovieCast())
                .releaseYear(movie.getReleaseYear())
                .poster(movie.getPoster())
                .posterUrl(posterUrl)
                .build();
    }
}
