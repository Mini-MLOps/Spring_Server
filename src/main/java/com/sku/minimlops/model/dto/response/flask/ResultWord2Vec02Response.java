package com.sku.minimlops.model.dto.response.flask;

import java.util.List;

import com.sku.minimlops.model.dto.Word2vecEmb02DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultWord2Vec02Response {
	private String input;
	private List<Word2vecEmb02DTO> embeddingVector;
	private String modelName;
}
