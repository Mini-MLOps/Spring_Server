package com.sku.minimlops.model.dto.response;

import java.util.List;

import com.sku.minimlops.model.dto.UserLogDetailDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLogResponse {
	private List<UserLogDetailDTO> userLog;
	private int totalPages;
	private int totalElements;
	private boolean first;
	private boolean last;
}
