package com.sku.minimlops.repository;

import com.sku.minimlops.model.domain.GPTEmb01;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GptEmb01Repository extends JpaRepository<GPTEmb01, Long> {
    GPTEmb01 findByMovieId(Long movieId);
}
