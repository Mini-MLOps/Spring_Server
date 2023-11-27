package com.sku.minimlops.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sku.minimlops.exception.Code;
import com.sku.minimlops.exception.dto.DataResponse;
import com.sku.minimlops.exception.dto.Response;
import com.sku.minimlops.model.dto.request.ResultRequest;
import com.sku.minimlops.service.UserLogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user-logs")
@RequiredArgsConstructor
public class UserLogController {
	private final UserLogService userLogService;

	@PostMapping
	public Response handleResultComplete(@RequestBody ResultRequest resultRequest) {
		userLogService.addUserLog(resultRequest);
		return Response.of(true, Code.OK);
	}

	@GetMapping
	public DataResponse getAllUserLogs(
		@PageableDefault(sort = {"requestDate"}, direction = Sort.Direction.DESC) Pageable pageable) {
		return DataResponse.of(userLogService.getAllUserLogs(pageable));
	}
}
