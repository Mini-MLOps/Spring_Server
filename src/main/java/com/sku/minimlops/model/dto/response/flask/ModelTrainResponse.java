package com.sku.minimlops.model.dto.response.flask;

import java.util.List;

import com.sku.minimlops.model.dto.MovieDTO;
import com.sku.minimlops.model.dto.request.ModelParameterRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModelTrainResponse {
    private Long modelId;
    private ModelParameterRequest parameter;
    private List<MovieDTO> movie;
}
