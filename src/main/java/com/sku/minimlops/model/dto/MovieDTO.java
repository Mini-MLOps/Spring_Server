package com.sku.minimlops.model.dto;

import java.time.LocalDate;

import com.sku.minimlops.model.domain.Movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {
    private Long id;
    private String code;
    private String title;
    private String plot;
    private LocalDate releaseDate;
    private LocalDate collectionDate;

    public static MovieDTO fromMovie(Movie movie) {
        return MovieDTO.builder()
            .id(movie.getId())
            .code(movie.getCode())
            .title(movie.getTitle())
            .plot(movie.getPlot())
            .releaseDate(movie.getReleaseDate())
            .collectionDate(movie.getCollectionDate())
            .build();
    }
}
