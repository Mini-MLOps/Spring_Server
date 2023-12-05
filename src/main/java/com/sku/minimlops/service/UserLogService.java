package com.sku.minimlops.service;

import com.sku.minimlops.model.dto.response.UserLogCountResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sku.minimlops.model.domain.Movie;
import com.sku.minimlops.model.domain.Result;
import com.sku.minimlops.model.domain.UserLog;
import com.sku.minimlops.model.dto.ResultDTO;
import com.sku.minimlops.model.dto.UserLogDetailDTO;
import com.sku.minimlops.model.dto.request.ResultRequest;
import com.sku.minimlops.model.dto.response.UserLogResponse;
import com.sku.minimlops.repository.MovieRepository;
import com.sku.minimlops.repository.ResultRepository;
import com.sku.minimlops.repository.UserLogRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserLogService {
    private final UserLogRepository userLogRepository;
    private final ResultRepository resultRepository;
    private final MovieRepository movieRepository;

    @Transactional
    public void addUserLog(ResultRequest resultRequest) {
        UserLog userLog = userLogRepository.save(UserLog.builder().input(resultRequest.getInput()).build());

        for (ResultDTO resultDTO : resultRequest.getOutput()) {
            Movie movie = movieRepository.findById(resultDTO.getMovieId()).orElse(null);
            Result result = Result.builder()
                    .userLog(userLog)
                    .movie(movie)
                    .similarity(resultDTO.getSimilarity())
                    .build();
            resultRepository.save(result);
        }
    }

    public UserLogResponse getAllUserLogs(Pageable pageable) {
        Page<UserLog> userLogs = userLogRepository.findAll(pageable);
        return UserLogResponse.builder()
                .userLog(userLogs.getContent().stream().map(UserLogDetailDTO::fromUserLog).toList())
                .totalElements((int) userLogs.getTotalElements())
                .first(userLogs.isFirst())
                .last(userLogs.isLast())
                .build();
    }

    public UserLogCountResponse countUserLogs() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(23, 59, 59);
        return UserLogCountResponse.builder()
                .totalElements(userLogRepository.countAllBy())
                .todayElements(userLogRepository.countAllByRequestDateBetween(startOfDay, endOfDay))
                .build();
    }
}
