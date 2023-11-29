package com.sku.minimlops.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sku.minimlops.model.domain.Model;
import com.sku.minimlops.model.dto.EmbeddingVectorDTO;
import com.sku.minimlops.model.dto.ModelDTO;
import com.sku.minimlops.model.dto.MovieDTO;
import com.sku.minimlops.model.dto.ResultDTO;
import com.sku.minimlops.model.dto.ResultDetailDTO;
import com.sku.minimlops.model.dto.UserLogDTO;
import com.sku.minimlops.model.dto.request.ModelParameterRequest;
import com.sku.minimlops.model.dto.request.UserInputRequest;
import com.sku.minimlops.model.dto.response.Flask.ModelDeployResponse;
import com.sku.minimlops.model.dto.response.Flask.ModelTrainResponse;
import com.sku.minimlops.model.dto.response.Flask.ResultResponse;
import com.sku.minimlops.model.dto.response.ModelResponse;
import com.sku.minimlops.model.dto.response.ResultDetailResponse;
import com.sku.minimlops.repository.EmbeddingVectorRepository;
import com.sku.minimlops.repository.ModelRepository;
import com.sku.minimlops.repository.MovieRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ModelService {
	private final String BASIC_URL = "http://211.62.99.58:5020";

	private final ModelRepository modelRepository;
	private final MovieRepository movieRepository;
	private final EmbeddingVectorRepository embeddingVectorRepository;

	public void trainModel(ModelParameterRequest modelParameterRequest) {
		String uri = BASIC_URL + "/train";

		List<MovieDTO> movies = movieRepository.findByCollectionDateBetweenOrderByCollectionDateDesc(
				modelParameterRequest.getDataStartDate(), modelParameterRequest.getDataEndDate())
			.stream().map(MovieDTO::fromMovie).toList();
		ModelTrainResponse modelTrainResponse = ModelTrainResponse.builder()
			.modelId(modelRepository.getAutoIncrementValue())
			.parameter(modelParameterRequest)
			.movie(movies)
			.build();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<ModelTrainResponse> request = new HttpEntity<>(modelTrainResponse, headers);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.exchange(uri, HttpMethod.POST, request, String.class);
	}

	@Transactional
	public void saveModel(ModelParameterRequest modelParameterRequest) {
		modelRepository.save(modelParameterRequest.toEntity());
	}

	public void deployModel(Long modelId) {
		String uri = String.format(BASIC_URL + "/%s/deploy", modelId);

		Optional<Model> model = modelRepository.findById(modelId);
		model.ifPresent(m -> {
			List<MovieDTO> movies = movieRepository.findByCollectionDateBetweenOrderByCollectionDateDesc(
					m.getDataStartDate(), m.getDataEndDate())
				.stream().map(MovieDTO::fromMovie).toList();
			ModelDeployResponse modelDeployResponse = ModelDeployResponse.builder()
				.movie(movies)
				.build();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<ModelDeployResponse> request = new HttpEntity<>(modelDeployResponse, headers);

			RestTemplate restTemplate = new RestTemplate();
			restTemplate.exchange(uri, HttpMethod.POST, request, String.class);
		});
	}

	public ModelResponse getAllModels(Pageable pageable) {
		Page<Model> models = modelRepository.findAll(pageable);
		return ModelResponse.builder()
			.model(models.getContent().stream().map(ModelDTO::fromModel).toList())
			.totalPages(models.getTotalPages())
			.totalElements((int)models.getTotalElements())
			.first(models.isFirst())
			.last(models.isLast())
			.build();
	}

	public ResultDetailResponse getResultByUserInput(UserInputRequest userInputRequest) throws JsonProcessingException {
		String uri = BASIC_URL + "/result";

		List<EmbeddingVectorDTO> embeddingVectors = embeddingVectorRepository.findAll()
			.stream()
			.map(EmbeddingVectorDTO::fromEmbeddingVector)
			.toList();
		ResultResponse resultResponse = ResultResponse.builder()
			.input(userInputRequest.getInput())
			.embeddingVector(embeddingVectors)
			.build();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<ResultResponse> request = new HttpEntity<>(resultResponse, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, request, String.class);

		String jsonResponse = responseEntity.getBody();
		ObjectMapper objectMapper = new ObjectMapper();

		List<ResultDetailDTO> resultDetailDTOS = new ArrayList<>();
		UserLogDTO userLogDTO = objectMapper.readValue(jsonResponse, UserLogDTO.class);
		for (ResultDTO output : userLogDTO.getOutput()) {
			movieRepository.findById(output.getMovieId())
				.ifPresent(movie -> resultDetailDTOS.add(ResultDetailDTO.builder()
					.movie(MovieDTO.fromMovie(movie))
					.similarity(output.getSimilarity())
					.build()));
		}

		return ResultDetailResponse.builder().result(resultDetailDTOS).build();
	}
}
