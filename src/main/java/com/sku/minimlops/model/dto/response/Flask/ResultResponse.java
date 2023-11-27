package com.sku.minimlops.model.dto.response.Flask;

import java.util.List;

import com.sku.minimlops.model.dto.EmbeddingVectorDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultResponse {
	private String input;
	private List<EmbeddingVectorDTO> embeddingVector;
}
