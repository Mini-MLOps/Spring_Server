package com.sku.minimlops.model.dto.response;

import java.util.List;

import com.sku.minimlops.model.dto.ResultDetailDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultDetailResponse {
	private List<ResultDetailDTO> result;
}
