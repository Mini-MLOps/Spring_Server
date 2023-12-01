package com.sku.minimlops.model.dto;

import com.sku.minimlops.model.domain.Word2vecEmb02;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Word2vecEmb02DTO {
	private Long movieId;
	private String vector;

	public static Word2vecEmb02DTO fromWord2vecEmb(Word2vecEmb02 word2vecEmb02) {
		return Word2vecEmb02DTO.builder()
			.movieId(word2vecEmb02.getMovie().getId())
			.vector(word2vecEmb02.getVector())
			.build();
	}
}
