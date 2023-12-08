package com.sku.minimlops.model.dto.response.flask;

import com.sku.minimlops.model.dto.GptEmb01DTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultGpt01Response {
    private String input;
    private List<GptEmb01DTO> embeddingVector;
}
