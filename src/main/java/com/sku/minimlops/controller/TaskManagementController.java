package com.sku.minimlops.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sku.minimlops.exception.dto.DataResponse;
import com.sku.minimlops.model.dto.response.ModelResponse;
import com.sku.minimlops.model.dto.response.TaskStatusResponse;
import com.sku.minimlops.service.TaskManagementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskManagementController {
	private final TaskManagementService taskManagementService;

	@GetMapping("/train")
	public DataResponse<TaskStatusResponse> isTrain() {
		return DataResponse.of(taskManagementService.isTrain());
	}

	@GetMapping("/deploy")
	public DataResponse<TaskStatusResponse> isDeploy() {
		return DataResponse.of(taskManagementService.isDeploy());
	}

	@GetMapping("/current-model")
	public DataResponse<ModelResponse> getCurrentModel() {
		return DataResponse.of(taskManagementService.getCurrentModel());
	}
}
