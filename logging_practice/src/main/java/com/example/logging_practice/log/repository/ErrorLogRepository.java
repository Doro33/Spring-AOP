package com.example.logging_practice.log.repository;

import com.example.logging_practice.log.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorLogRepository extends JpaRepository<ErrorLog,Long> {
}
