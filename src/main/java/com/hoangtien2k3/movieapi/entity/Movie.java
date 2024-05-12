package com.hoangtien2k3.movieapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "Please provide movie's title.")
    private String title;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "Please provide movie's directory.")
    private String  directory;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "Please provide movie's studio.")
    private String studio;

    @ElementCollection
    @CollectionTable(name = "movie_cast")
    private Set<String> movieCast;

    @Column(nullable = false, length = 200)
    @NotNull
    private Integer releaseYear;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "Please provide movie's porter.")
    private String poster;
}
