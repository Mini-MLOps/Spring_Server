package com.sku.minimlops.model.dto;

import com.sku.minimlops.model.domain.Word2vecEmb01;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Word2vecEmb01DTO {
	private Long movieId;
	private String vector;

	public static Word2vecEmb01DTO fromWord2vecEmb(Word2vecEmb01 word2vecEmb01) {
		return Word2vecEmb01DTO.builder()
			.movieId(word2vecEmb01.getMovie().getId())
			.vector(word2vecEmb01.getVector())
			.build();
	}
}
