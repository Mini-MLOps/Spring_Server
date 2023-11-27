package com.sku.minimlops.repository;

import com.sku.minimlops.model.domain.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {

    @Query(value = "SELECT Auto_increment FROM information_schema.tables WHERE table_schema = 'mlops_db' AND table_name = 'model'", nativeQuery = true)
    Long getAutoIncrementValue();
}
