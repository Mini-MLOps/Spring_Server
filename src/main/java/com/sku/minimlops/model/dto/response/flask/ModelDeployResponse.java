package com.sku.minimlops.model.dto.response.flask;

import java.util.List;

import com.sku.minimlops.model.domain.TableName;
import com.sku.minimlops.model.dto.MovieDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModelDeployResponse {
    private String modelName;
    private TableName tableName;
    private List<MovieDTO> movie;
}
