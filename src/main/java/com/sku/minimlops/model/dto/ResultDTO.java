package com.sku.minimlops.model.dto;

import com.sku.minimlops.model.domain.Result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO {
	private Long movieId;
	private float similarity;

	public static ResultDTO fromResult(Result result) {
		return ResultDTO.builder()
			.movieId(result.getMovie().getId())
			.similarity(result.getSimilarity())
			.build();
	}
}
