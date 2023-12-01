package com.sku.minimlops.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sku.minimlops.model.domain.TaskManagement;

public interface TaskMangementRepository extends JpaRepository<TaskManagement, Long> {
}
