package com.sku.minimlops.repository;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sku.minimlops.model.domain.UserLog;

public interface UserLogRepository extends JpaRepository<UserLog, Long> {
    int countAllBy();

    int countAllByRequestDate(LocalDate today);
}
