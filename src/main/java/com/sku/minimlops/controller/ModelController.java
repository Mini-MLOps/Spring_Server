package com.sku.minimlops.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sku.minimlops.exception.Code;
import com.sku.minimlops.exception.dto.DataResponse;
import com.sku.minimlops.exception.dto.Response;
import com.sku.minimlops.model.dto.request.ModelParameterRequest;
import com.sku.minimlops.model.dto.request.UserInputRequest;
import com.sku.minimlops.model.dto.response.ModelResponse;
import com.sku.minimlops.service.ModelService;
import com.sku.minimlops.service.UserLogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/models")
@RequiredArgsConstructor
public class ModelController {
	private final ModelService modelService;
	private final UserLogService userLogService;

	@PostMapping("/train")
	public Response trainModel(@RequestBody ModelParameterRequest modelParameterRequest) {
		modelService.trainModel(modelParameterRequest);
		return Response.of(true, Code.OK);
	}

	@PostMapping("/train-complete")
	public void handleTrainingComplete(@RequestBody ModelParameterRequest modelParameterRequest) {
		modelService.saveModel(modelParameterRequest);
	}

	@PostMapping("/{modelId}/deploy")
	public Response deployModel(@PathVariable Long modelId) {
		modelService.deployModel(modelId);
		return Response.of(true, Code.OK);
	}

	@PostMapping("/deploy-complete")
	public void handleDeployingComplete(@RequestBody String result) {
		System.out.println("Deploying completed: " + result);
	}

	@GetMapping
	public DataResponse<ModelResponse> getAllModels(
		@PageableDefault(sort = {"creationDate"}, direction = Sort.Direction.DESC) Pageable pageable) {
		return DataResponse.of(modelService.getAllModels(pageable));
	}

	@GetMapping("/result")
	public DataResponse getResult(@RequestBody UserInputRequest userInputRequest) throws JsonProcessingException {
		return DataResponse.of(modelService.getResultByUserInput(userInputRequest));
	}
}
