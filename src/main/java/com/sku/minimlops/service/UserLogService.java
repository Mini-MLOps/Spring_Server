package com.sku.minimlops.service;

import com.sku.minimlops.model.dto.response.UserLogCountResponse;
import com.sku.minimlops.model.dto.response.UserLogRatioResponse;
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

    public UserLogResponse getAllUserLogs(Pageable pageable) {
        Page<UserLog> userLogs = userLogRepository.findAll(pageable);
        return UserLogResponse.builder()
                .userLog(userLogs.getContent().stream().map(UserLogDetailDTO::fromUserLog).toList())
                .totalPages(userLogs.getTotalPages())
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

    public UserLogRatioResponse ratioOfUserLogs() {
        float good = userLogRepository.countAllBySatisfactionIsTrue();
        float bad = userLogRepository.countAllBySatisfactionIsFalse();
        float none = userLogRepository.countAllBySatisfactionIsNull();
        float total = good + bad + none;

        float goodPercentage = (good / total) * 100;
        float badPercentage = (bad / total) * 100;
        float nonePercentage = (none / total) * 100;

        return UserLogRatioResponse.builder()
                .good(Float.parseFloat(String.format("%.1f", goodPercentage)))
                .bad(Float.parseFloat(String.format("%.1f", badPercentage)))
                .none(Float.parseFloat(String.format("%.1f", nonePercentage)))
                .build();
    }

    @Transactional
    public void selectGood(Long userLogId) {
        UserLog userLog = userLogRepository.findById(userLogId).orElse(null);
        assert userLog != null;
        userLog.setGood();
    }

    @Transactional
    public void selectBad(Long userLogId) {
        UserLog userLog = userLogRepository.findById(userLogId).orElse(null);
        assert userLog != null;
        userLog.setBad();
    }
}
