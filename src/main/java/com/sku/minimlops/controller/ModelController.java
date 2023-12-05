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
import com.sku.minimlops.model.dto.response.ModelListResponse;
import com.sku.minimlops.model.dto.response.ResultDetailResponse;
import com.sku.minimlops.service.ModelService;
import com.sku.minimlops.service.TaskManagementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/models")
@RequiredArgsConstructor
public class ModelController {
	private final ModelService modelService;
	private final TaskManagementService taskManagementService;

	@PostMapping("/train")
	public Response trainModel(@RequestBody ModelParameterRequest modelParameterRequest) {
		taskManagementService.trainOn();
		modelService.trainModel(modelParameterRequest);
		return Response.of(true, Code.OK);
	}

	@PostMapping("/train-complete")
	public void handleTrainingComplete(@RequestBody ModelParameterRequest modelParameterRequest) {
		taskManagementService.trainOff();
		modelService.saveModel(modelParameterRequest);
	}

	@PostMapping("/{modelId}/deploy")
	public Response deployModel(@PathVariable Long modelId) {
		taskManagementService.deployOn();
		modelService.deployModel(modelId);
		return Response.of(true, Code.OK);
	}

	@PostMapping("/{modelId}/deploy-complete")
	public void handleDeployingComplete(@PathVariable Long modelId) {
		taskManagementService.deployOff(modelId);
	}

	@GetMapping
	public DataResponse<ModelListResponse> getAllModels(
		@PageableDefault(sort = {"creationDate"}, direction = Sort.Direction.DESC) Pageable pageable) {
		return DataResponse.of(modelService.getAllModels(pageable));
	}

	@PostMapping("/result/word2vec")
	public DataResponse<ResultDetailResponse> getResult(@RequestBody UserInputRequest userInputRequest) throws JsonProcessingException {
		return DataResponse.of(modelService.getResultByUserInput(userInputRequest));
	}
}
