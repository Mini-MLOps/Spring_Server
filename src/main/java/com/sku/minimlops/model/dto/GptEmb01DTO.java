package com.sku.minimlops.model.dto;

import com.sku.minimlops.model.domain.GPTEmb01;
import com.sku.minimlops.model.domain.Word2vecEmb02;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GptEmb01DTO {
    private Long movieId;
    private String vector;

    public static GptEmb01DTO fromGptEmb01(GPTEmb01 gptEmb01) {
        return GptEmb01DTO.builder()
                .movieId(gptEmb01.getMovie().getId())
                .vector(gptEmb01.getVector())
                .build();
    }
}

