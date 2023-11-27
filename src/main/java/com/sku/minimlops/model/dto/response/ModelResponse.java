package com.sku.minimlops.model.dto.response;

import java.util.List;

import com.sku.minimlops.model.dto.ModelDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModelResponse {
    private List<ModelDTO> model;
    private int totalPages;
    private int totalElements;
    private boolean first;
    private boolean last;
}
