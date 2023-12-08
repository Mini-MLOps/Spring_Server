package com.sku.minimlops.model.dto.response.flask;

import java.util.List;

import com.sku.minimlops.model.dto.Word2vecEmb01DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultWord2Vec01Response {
	private String input;
	private List<Word2vecEmb01DTO> embeddingVector;
	private String modelName;
}
