package net.enjoy.springboot.registrationlogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.enjoy.springboot.registrationlogin.entity.PerformanceData;

public interface PerformanceDataRepository extends JpaRepository<PerformanceData, Long> {
}

