package com.sku.minimlops.service;

import com.sku.minimlops.model.domain.Movie;
import com.sku.minimlops.model.dto.GptEmb01DTO;
import com.sku.minimlops.model.dto.response.flask.ResultGpt01Response;
import com.sku.minimlops.repository.GptEmb01Repository;
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
import com.sku.minimlops.model.domain.TableName;
import com.sku.minimlops.model.domain.TaskManagement;
import com.sku.minimlops.model.dto.ModelDTO;
import com.sku.minimlops.model.dto.MovieDTO;
import com.sku.minimlops.model.dto.ResultDTO;
import com.sku.minimlops.model.dto.ResultDetailDTO;
import com.sku.minimlops.model.dto.UserLogDTO;
import com.sku.minimlops.model.dto.Word2vecEmb01DTO;
import com.sku.minimlops.model.dto.Word2vecEmb02DTO;
import com.sku.minimlops.model.dto.request.ModelParameterRequest;
import com.sku.minimlops.model.dto.request.UserInputRequest;
import com.sku.minimlops.model.dto.response.ModelListResponse;
import com.sku.minimlops.model.dto.response.ResultDetailResponse;
import com.sku.minimlops.model.dto.response.flask.ModelDeployResponse;
import com.sku.minimlops.model.dto.response.flask.ModelTrainResponse;
import com.sku.minimlops.model.dto.response.flask.ResultWord2Vec01Response;
import com.sku.minimlops.model.dto.response.flask.ResultWord2Vec02Response;
import com.sku.minimlops.repository.ModelRepository;
import com.sku.minimlops.repository.MovieRepository;
import com.sku.minimlops.repository.TaskMangementRepository;
import com.sku.minimlops.repository.Word2vecEmb01Repository;
import com.sku.minimlops.repository.Word2vecEmb02Repository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ModelService {
    //    private final String BASE_URL = "http://211.62.99.58:5020";
    private final String BASE_URL = "http://localhost:5000";

    private final ModelRepository modelRepository;
    private final MovieRepository movieRepository;
    private final TaskMangementRepository taskMangementRepository;
    private final Word2vecEmb01Repository word2vecEmb01Repository;
    private final Word2vecEmb02Repository word2vecEmb02Repository;
    private final GptEmb01Repository gptEmb01Repository;

    public void trainModel(ModelParameterRequest modelParameterRequest) {
        String uri = BASE_URL + "/train";

        List<MovieDTO> movies = movieRepository.findByReleaseDateBetweenOrderByReleaseDateDesc(
                        modelParameterRequest.getDataStartDate(), modelParameterRequest.getDataEndDate())
                .stream().map(MovieDTO::fromMovie).toList();
        ModelTrainResponse modelTrainResponse = ModelTrainResponse.builder()
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
        String uri = String.format(BASE_URL + "/%s/deploy", modelId);

        TableName nextTable = taskMangementRepository.findAll().get(0).nextTable();
        Optional<Model> model = modelRepository.findById(modelId);
        model.ifPresent(m -> {
            List<MovieDTO> movies = movieRepository.findByReleaseDateBetweenOrderByReleaseDateDesc(
                            m.getDataStartDate(), m.getDataEndDate())
                    .stream().map(MovieDTO::fromMovie).toList();
            ModelDeployResponse modelDeployResponse = ModelDeployResponse.builder()
                    .modelName(m.getName())
                    .tableName(nextTable)
                    .movie(movies)
                    .build();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<ModelDeployResponse> request = new HttpEntity<>(modelDeployResponse, headers);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.exchange(uri, HttpMethod.POST, request, String.class);
        });
    }

    public ModelListResponse getAllModels(Pageable pageable) {
        Page<Model> models = modelRepository.findAll(pageable);
        return ModelListResponse.builder()
                .model(models.getContent().stream().map(ModelDTO::fromModel).toList())
                .totalPages(models.getTotalPages())
                .totalElements((int) models.getTotalElements())
                .first(models.isFirst())
                .last(models.isLast())
                .build();
    }

    public ResultDetailResponse getResultWord2Vec(UserInputRequest userInputRequest) throws JsonProcessingException {
        String uri = BASE_URL + "/result";

        TaskManagement taskManagement = taskMangementRepository.findAll().get(0);
        String modelName = taskManagement.getCurrentModel().getName();
        if (taskManagement.getCurrentTable().equals(TableName.word2vec_emb_01)) {
            List<Word2vecEmb01DTO> vectors = word2vecEmb01Repository.findAll()
                    .stream()
                    .map(Word2vecEmb01DTO::fromWord2vecEmb)
                    .toList();
            ResultWord2Vec01Response resultResponse = ResultWord2Vec01Response.builder()
                    .input(userInputRequest.getInput())
                    .embeddingVector(vectors)
                    .modelName(modelName)
                    .build();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<ResultWord2Vec01Response> request = new HttpEntity<>(resultResponse, headers);

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
        } else {
            List<Word2vecEmb02DTO> vectors = word2vecEmb02Repository.findAll()
                    .stream()
                    .map(Word2vecEmb02DTO::fromWord2vecEmb)
                    .toList();
            ResultWord2Vec02Response resultResponse = ResultWord2Vec02Response.builder()
                    .input(userInputRequest.getInput())
                    .embeddingVector(vectors)
                    .modelName(modelName)
                    .build();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<ResultWord2Vec02Response> request = new HttpEntity<>(resultResponse, headers);

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

    public ResultDetailResponse getResultGpt(UserInputRequest userInputRequest) throws JsonProcessingException {
        String uri = BASE_URL + "/result";

        TaskManagement taskManagement = taskMangementRepository.findById(1L).orElse(null);
        assert taskManagement != null;
        List<Movie> movies = movieRepository.findByReleaseDateBetweenOrderByReleaseDateDesc(
                taskManagement.getCurrentModel().getDataStartDate(), taskManagement.getCurrentModel().getDataEndDate());
        List<GptEmb01DTO> vectors = movies.stream()
                .map(movie -> gptEmb01Repository.findByMovieId(movie.getId()))
                .map(GptEmb01DTO::fromGptEmb01)
                .toList();
        ResultGpt01Response resultResponse = ResultGpt01Response.builder()
                .input(userInputRequest.getInput())
                .embeddingVector(vectors)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ResultGpt01Response> request = new HttpEntity<>(resultResponse, headers);

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
