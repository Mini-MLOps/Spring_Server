package com.sku.minimlops.controller;

import com.sku.minimlops.exception.Code;
import com.sku.minimlops.exception.dto.DataResponse;
import com.sku.minimlops.exception.dto.Response;
import com.sku.minimlops.model.dto.response.UserLogCountResponse;
import com.sku.minimlops.model.dto.response.UserLogRatioResponse;
import com.sku.minimlops.model.dto.response.UserLogResponse;
import com.sku.minimlops.service.UserLogService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-logs")
@RequiredArgsConstructor
public class UserLogController {
	private final UserLogService userLogService;

	@GetMapping
	public DataResponse<UserLogResponse> getAllUserLogs(
		@PageableDefault(sort = {"requestDate"}, direction = Sort.Direction.DESC) Pageable pageable) {
		return DataResponse.of(userLogService.getAllUserLogs(pageable));
	}

	@GetMapping("/count")
	public DataResponse<UserLogCountResponse> countUserLogs() {
		return DataResponse.of(userLogService.countUserLogs());
	}

	@GetMapping("/ratio")
	public DataResponse<UserLogRatioResponse> ratioOfUserLogs() {
		return DataResponse.of(userLogService.ratioOfUserLogs());
	}

	@PostMapping("/good")
	public Response selectGood(@RequestParam Long id) {
		userLogService.selectGood(id);
		return Response.of(true, Code.OK);
	}

	@PostMapping("/bad")
	public Response selectBad(@RequestParam Long id) {
		userLogService.selectBad(id);
		return Response.of(true, Code.OK);
	}
}
