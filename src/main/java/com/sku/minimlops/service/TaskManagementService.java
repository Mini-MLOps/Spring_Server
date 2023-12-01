package com.sku.minimlops.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sku.minimlops.model.domain.Model;
import com.sku.minimlops.model.domain.TaskManagement;
import com.sku.minimlops.model.dto.ModelDTO;
import com.sku.minimlops.model.dto.response.ModelResponse;
import com.sku.minimlops.model.dto.response.TaskStatusResponse;
import com.sku.minimlops.repository.ModelRepository;
import com.sku.minimlops.repository.TaskMangementRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskManagementService {
	private final ModelRepository modelRepository;
	private final TaskMangementRepository taskMangementRepository;

	@Transactional
	public void trainOn() {
		TaskManagement taskManagement = taskMangementRepository.findById(1L).orElse(null);
		assert taskManagement != null;
		taskManagement.trainOn();
	}

	@Transactional
	public void trainOff() {
		TaskManagement taskManagement = taskMangementRepository.findById(1L).orElse(null);
		assert taskManagement != null;
		taskManagement.trainOff();
	}

	@Transactional
	public void deployOn() {
		Optional<TaskManagement> taskManagement = taskMangementRepository.findById(1L);
		taskManagement.ifPresent(TaskManagement::deployOn);
	}

	@Transactional
	public void deployOff(Long modelId) {
		changeCurrentModel(modelId);
		switchCurrentTable();
		Optional<TaskManagement> taskManagement = taskMangementRepository.findById(1L);
		taskManagement.ifPresent(TaskManagement::deployOff);
	}

	@Transactional
	public void changeCurrentModel(Long modelId) {
		Model model = modelRepository.findById(modelId).orElse(null);
		Optional<TaskManagement> taskManagement = taskMangementRepository.findById(1L);
		taskManagement.ifPresent(tm -> tm.changeCurrentModel(model));
	}

	@Transactional
	public void switchCurrentTable() {
		Optional<TaskManagement> taskManagement = taskMangementRepository.findById(1L);
		taskManagement.ifPresent(TaskManagement::switchCurrentTable);
	}

	public TaskStatusResponse isTrain() {
		TaskManagement taskManagement = taskMangementRepository.findById(1L).orElse(null);
		if (taskManagement != null) {
			return TaskStatusResponse.builder()
				.status(taskManagement.isTrain())
				.build();
		}
		return null;
	}

	public TaskStatusResponse isDeploy() {
		TaskManagement taskManagement = taskMangementRepository.findById(1L).orElse(null);
		if (taskManagement != null) {
			return TaskStatusResponse.builder()
				.status(taskManagement.isDeploy())
				.build();
		}
		return null;
	}

	public ModelResponse getCurrentModel() {
		TaskManagement taskManagement = taskMangementRepository.findById(1L).orElse(null);
		if (taskManagement != null) {
			return ModelResponse.builder()
				.model(ModelDTO.fromModel(taskManagement.getCurrentModel()))
				.build();
		}
		return null;
	}
}
