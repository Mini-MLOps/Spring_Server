package com.sku.minimlops.controller;

import com.sku.minimlops.model.dto.response.MovieChartResponse;
import com.sku.minimlops.model.dto.response.MovieTodayCountResponse;
import java.time.LocalDate;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sku.minimlops.exception.Code;
import com.sku.minimlops.exception.dto.DataResponse;
import com.sku.minimlops.exception.dto.Response;
import com.sku.minimlops.model.dto.response.MovieCountResponse;
import com.sku.minimlops.model.dto.response.MovieResponse;
import com.sku.minimlops.service.MovieService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

	private final MovieService movieService;

	@GetMapping
	public DataResponse<MovieResponse> getMovies(
		@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
		@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
		@PageableDefault(sort = {"releaseDate"}, direction = Sort.Direction.DESC) Pageable pageable) {
		return DataResponse.of(movieService.getMoviesByCollectionDate(startDate, endDate, pageable));
	}

	@GetMapping("/chart")
	public DataResponse<MovieChartResponse> countMoviesDaily(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		return DataResponse.of(movieService.countMoviesDaily(startDate, endDate));
	}

	@GetMapping("/count")
	public DataResponse<MovieCountResponse> countMovies(
		@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
		@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		return DataResponse.of(movieService.countMoviesByReleaseDate(startDate, endDate));
	}

	@GetMapping("/count/today")
	public DataResponse<MovieTodayCountResponse> countTodayMovies() {
		return DataResponse.of(movieService.countMoviesByCollectionDateToday());
	}

	@DeleteMapping
	public Response deleteMovie(@RequestParam Long id) {
		movieService.deleteMovie(id);
		return Response.of(true, Code.OK);
	}
}
