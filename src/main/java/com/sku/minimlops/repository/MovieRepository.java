package com.sku.minimlops.repository;

import com.sku.minimlops.model.domain.Movie;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Page<Movie> findByCollectionDateBetweenOrderByCollectionDateDesc(LocalDate startDate, LocalDate endDate, Pageable pageable);

    List<Movie> findByCollectionDateBetweenOrderByCollectionDateDesc(LocalDate startDate, LocalDate endDate);

}
