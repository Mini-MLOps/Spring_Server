package com.sku.minimlops.model.dto;

import com.sku.minimlops.model.domain.EmbeddingVector;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmbeddingVectorDTO {
	private Long movieId;
	private Long modelId;
	private String word2vec;
	private String gpt;

	public static EmbeddingVectorDTO fromEmbeddingVector(EmbeddingVector embeddingVector) {
		return EmbeddingVectorDTO.builder()
			.movieId(embeddingVector.getMovie().getId())
			.modelId(embeddingVector.getModel().getId())
			.word2vec(embeddingVector.getWord2vec())
			.gpt(embeddingVector.getGpt())
			.build();
	}
}
