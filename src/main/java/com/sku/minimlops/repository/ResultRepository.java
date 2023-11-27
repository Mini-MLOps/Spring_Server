package com.sku.minimlops.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sku.minimlops.model.domain.Result;

public interface ResultRepository extends JpaRepository<Result, Long> {
}
