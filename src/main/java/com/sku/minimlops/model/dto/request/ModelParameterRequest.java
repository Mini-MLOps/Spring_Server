package com.sku.minimlops.model.dto.request;

import java.time.LocalDate;

import com.sku.minimlops.model.domain.Model;

import lombok.Getter;

@Getter
public class ModelParameterRequest {
    private LocalDate dataStartDate;
    private LocalDate dataEndDate;
    private int vectorSize;
    private int windowSize;
    private int minCount;
    private int sg;
    private int epochs;

    public Model toEntity() {
        return Model.builder()
            .dataStartDate(dataStartDate)
            .dataEndDate(dataEndDate)
            .vectorSize(vectorSize)
            .windowSize(windowSize)
            .minCount(minCount)
            .sg(sg)
            .epochs(epochs)
            .build();
    }
}
