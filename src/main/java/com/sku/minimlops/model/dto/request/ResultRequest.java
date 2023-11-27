package com.sku.minimlops.model.dto.request;

import java.util.List;

import com.sku.minimlops.model.dto.ResultDTO;

import lombok.Getter;

@Getter
public class ResultRequest {
	private String input;
	private List<ResultDTO> output;
}