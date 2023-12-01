package com.sku.minimlops.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sku.minimlops.model.domain.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

	Page<Movie> findByCollectionDateBetweenOrderByCollectionDateDesc(LocalDate startDate, LocalDate endDate,
		Pageable pageable);

	List<Movie> findByCollectionDateBetweenOrderByCollectionDateDesc(LocalDate startDate, LocalDate endDate);

	int countAllByCollectionDateBetween(LocalDate startDate, LocalDate endDate);
}
