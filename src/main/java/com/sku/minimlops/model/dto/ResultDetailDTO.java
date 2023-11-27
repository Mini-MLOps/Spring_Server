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
public class ResultDetailDTO {
	private MovieDTO movie;
	private float similarity;

	public static ResultDetailDTO fromResult(Result result) {
		return ResultDetailDTO.builder()
			.movie(MovieDTO.fromMovie(result.getMovie()))
			.similarity(result.getSimilarity())
			.build();
	}
}
