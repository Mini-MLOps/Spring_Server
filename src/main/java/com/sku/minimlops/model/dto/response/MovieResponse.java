package com.sku.minimlops.model.dto.response;

import com.sku.minimlops.model.dto.MovieDailyDTO;
import java.util.List;

import com.sku.minimlops.model.dto.MovieDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponse {
    private List<MovieDTO> movie;
    private int totalPages;
    private int totalElements;
    private boolean first;
    private boolean last;
}
