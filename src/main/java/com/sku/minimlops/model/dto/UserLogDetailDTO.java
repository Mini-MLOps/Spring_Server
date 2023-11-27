package com.sku.minimlops.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.sku.minimlops.model.domain.UserLog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLogDetailDTO {
	private Long id;
	private String input;
	private List<ResultDetailDTO> output;
	private int satisfaction;
	private LocalDateTime requestDate;

	public static UserLogDetailDTO fromUserLog(UserLog userLog) {
		return UserLogDetailDTO.builder()
			.id(userLog.getId())
			.input(userLog.getInput())
			.output(userLog.getResults().stream().map(ResultDetailDTO::fromResult).toList())
			.satisfaction(userLog.getSatisfaction())
			.requestDate(userLog.getRequestDate())
			.build();
	}
}
