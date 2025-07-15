package com.example.logging_practice.log.repository;

import com.example.logging_practice.log.InfoLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfoLogRepository extends JpaRepository<InfoLog, Long> {
}
