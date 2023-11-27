package com.sku.minimlops.model.dto;

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
public class UserLogDTO {
	private String input;
	private List<ResultDTO> output;

	public static UserLogDTO fromUserLog(UserLog userLog) {
		return UserLogDTO.builder()
			.input(userLog.getInput())
			.output(userLog.getResults().stream().map(ResultDTO::fromResult).toList())
			.build();
	}
}
