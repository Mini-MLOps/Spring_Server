package com.sku.minimlops.repository;

import com.sku.minimlops.model.dto.MovieDailyDTO;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sku.minimlops.model.domain.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

	Page<Movie> findByReleaseDateBetweenOrderByReleaseDateDesc(LocalDate startDate, LocalDate endDate,
		Pageable pageable);

	List<Movie> findByReleaseDateBetweenOrderByReleaseDateDesc(LocalDate startDate, LocalDate endDate);

	Page<Movie> findByCollectionDateBetweenOrderByCollectionDateDesc(LocalDate startDate, LocalDate endDate, Pageable pageable);

	List<Movie> findByCollectionDateBetweenOrderByCollectionDateDesc(LocalDate startDate, LocalDate endDate);

	int countMoviesByReleaseDateBetween(LocalDate startDate, LocalDate endDate);

	@Query("SELECT COUNT(e) FROM Movie e WHERE e.collectionDate = :today")
	int countMoviesByCollectionDateToday(@Param("today") LocalDate today);

	@Query("SELECT new com.sku.minimlops.model.dto.MovieDailyDTO(e.collectionDate, COUNT(e)) " +
            "FROM Movie e " +
            "WHERE e.collectionDate BETWEEN :startDate AND :endDate " +
            "GROUP BY e.collectionDate " +
            "ORDER BY e.collectionDate")
	List<MovieDailyDTO> getCountByCollectionDateInRange(
			@Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate
	);
}
