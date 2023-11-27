package com.sku.minimlops.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.sku.minimlops.model.domain.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModelDTO {
    private Long id;
    private LocalDate dataStartDate;
    private LocalDate dataEndDate;
    private int vectorSize;
    private int windowSize;
    private int minCount;
    private int sg;
    private int epochs;
    private LocalDateTime creationDate;

    public static ModelDTO fromModel(Model model) {
        return ModelDTO.builder()
                .id(model.getId())
                .dataStartDate(model.getDataStartDate())
                .dataEndDate(model.getDataEndDate())
                .vectorSize(model.getVectorSize())
                .windowSize(model.getWindowSize())
                .minCount(model.getMinCount())
                .sg(model.getSg())
                .epochs(model.getEpochs())
                .creationDate(model.getCreationDate())
                .build();
    }
}