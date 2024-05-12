package com.hoangtien2k3.movieapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDto {
    private Integer movieId;

    @NotBlank(message = "Please provide movie's title.")
    private String title;

    @NotBlank(message = "Please provide movie's directory.")
    private String  directory;

    @NotBlank(message = "Please provide movie's studio.")
    private String studio;

    private Set<String> movieCast;
    private Integer releaseYear;

    @NotBlank(message = "Please provide movie's porter.")
    private String poster;

    @NotBlank(message = "Please provide movie's porterUrl.")
    private String posterUrl;
}
