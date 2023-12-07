package com.sku.minimlops.model.dto.response;

import com.sku.minimlops.model.dto.MovieDailyDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieChartResponse {
    private String id;
    private String color;
    private List<MovieDailyDTO> data;
}
