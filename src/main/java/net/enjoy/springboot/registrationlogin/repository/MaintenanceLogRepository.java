package net.enjoy.springboot.registrationlogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.enjoy.springboot.registrationlogin.entity.MaintenanceLog;

public interface MaintenanceLogRepository extends JpaRepository<MaintenanceLog, Long> {
}
