package com.sku.minimlops.service;

import org.springframework.stereotype.Service;

import com.sku.minimlops.model.domain.Model;
import com.sku.minimlops.model.domain.TaskManagement;
import com.sku.minimlops.model.dto.ModelDTO;
import com.sku.minimlops.model.dto.response.ModelResponse;
import com.sku.minimlops.model.dto.response.TaskStatusResponse;
import com.sku.minimlops.repository.ModelRepository;
import com.sku.minimlops.repository.TaskMangementRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskManagementService {
	private final ModelRepository modelRepository;
	private final TaskMangementRepository taskMangementRepository;

	public void trainOn() {
		TaskManagement taskManagement = taskMangementRepository.findAll().get(0);
		taskManagement.trainOn();
	}

	public void trainOff() {
		TaskManagement taskManagement = taskMangementRepository.findAll().get(0);
		taskManagement.trainOff();
	}

	public void deployOn() {
		TaskManagement taskManagement = taskMangementRepository.findAll().get(0);
		taskManagement.deployOn();
	}

	public void deployOff(Long modelId) {
		TaskManagement taskManagement = taskMangementRepository.findAll().get(0);
		changeCurrentModel(modelId);
		switchCurrentTable();
		taskManagement.deployOff();
	}

	public void changeCurrentModel(Long modelId) {
		TaskManagement taskManagement = taskMangementRepository.findAll().get(0);
		Model model = modelRepository.findById(modelId).orElse(null);
		taskManagement.changeCurrentModel(model);
	}

	public void switchCurrentTable() {
		TaskManagement taskManagement = taskMangementRepository.findAll().get(0);
		taskManagement.switchCurrentTable();
	}

	public TaskStatusResponse isTrain() {
		TaskManagement taskManagement = taskMangementRepository.findAll().get(0);
		return TaskStatusResponse.builder()
			.status(taskManagement.isTrain())
			.build();
	}

	public TaskStatusResponse isDeploy() {
		TaskManagement taskManagement = taskMangementRepository.findAll().get(0);
		return TaskStatusResponse.builder()
			.status(taskManagement.isDeploy())
			.build();
	}

	public ModelResponse getCurrentModel() {
		TaskManagement taskManagement = taskMangementRepository.findAll().get(0);
		return ModelResponse.builder()
			.model(ModelDTO.fromModel(taskManagement.getCurrentModel()))
			.build();
	}
}
