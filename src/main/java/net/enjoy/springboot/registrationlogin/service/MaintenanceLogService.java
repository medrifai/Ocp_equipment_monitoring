package net.enjoy.springboot.registrationlogin.service;

import java.util.List;

import net.enjoy.springboot.registrationlogin.dto.MaintenanceLogDto;

public interface MaintenanceLogService {
    List<MaintenanceLogDto> getAllMaintenanceLogs();
    MaintenanceLogDto getMaintenanceLogById(Long id);
    MaintenanceLogDto saveMaintenanceLog(MaintenanceLogDto maintenanceLogDto);
    void deleteMaintenanceLog(Long id);
}
