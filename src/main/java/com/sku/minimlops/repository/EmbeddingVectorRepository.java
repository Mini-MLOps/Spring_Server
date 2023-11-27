package com.sku.minimlops.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sku.minimlops.model.domain.EmbeddingVector;

public interface EmbeddingVectorRepository extends JpaRepository<EmbeddingVector, Long> {

}
