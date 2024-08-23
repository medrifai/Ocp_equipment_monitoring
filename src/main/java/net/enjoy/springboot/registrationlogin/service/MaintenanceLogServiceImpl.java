package net.enjoy.springboot.registrationlogin.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.enjoy.springboot.registrationlogin.dto.MaintenanceLogDto;
import net.enjoy.springboot.registrationlogin.entity.Equipment;
import net.enjoy.springboot.registrationlogin.entity.MaintenanceLog;
import net.enjoy.springboot.registrationlogin.repository.EquipmentRepository;
import net.enjoy.springboot.registrationlogin.repository.MaintenanceLogRepository;

@Service
public class MaintenanceLogServiceImpl implements MaintenanceLogService {

    @Autowired
    private MaintenanceLogRepository maintenanceLogRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Override
    public List<MaintenanceLogDto> getAllMaintenanceLogs() {
        return maintenanceLogRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public MaintenanceLogDto getMaintenanceLogById(Long id) {
        return maintenanceLogRepository.findById(id)
                .map(this::convertToDto)
                .orElse(null);
    }

    @Override
    public MaintenanceLogDto saveMaintenanceLog(MaintenanceLogDto maintenanceLogDto) {
        MaintenanceLog maintenanceLog = convertToEntity(maintenanceLogDto);
        return convertToDto(maintenanceLogRepository.save(maintenanceLog));
    }

    @Override
    public void deleteMaintenanceLog(Long id) {
        maintenanceLogRepository.deleteById(id);
    }

    private MaintenanceLogDto convertToDto(MaintenanceLog maintenanceLog) {
        MaintenanceLogDto dto = new MaintenanceLogDto();
        dto.setId(maintenanceLog.getId());
        dto.setEquipmentId(maintenanceLog.getEquipment().getId());
        dto.setTimestamp(maintenanceLog.getTimestamp());
        dto.setDescription(maintenanceLog.getDescription());
        dto.setEquipmentName(maintenanceLog.getEquipment().getName());
        return dto;
    }

    private MaintenanceLog convertToEntity(MaintenanceLogDto dto) {
        MaintenanceLog maintenanceLog = new MaintenanceLog();
        maintenanceLog.setId(dto.getId());

        // Récupérer l'entité Equipment depuis l'ID fourni
        Optional<Equipment> equipmentOpt = equipmentRepository.findById(dto.getEquipmentId());
        if (equipmentOpt.isPresent()) {
            maintenanceLog.setEquipment(equipmentOpt.get());
        } else {
            throw new IllegalArgumentException("L'équipement avec l'ID " + dto.getEquipmentId() + " n'existe pas.");
        }

        maintenanceLog.setTimestamp(dto.getTimestamp());
        maintenanceLog.setDescription(dto.getDescription());
        return maintenanceLog;
    }
}
