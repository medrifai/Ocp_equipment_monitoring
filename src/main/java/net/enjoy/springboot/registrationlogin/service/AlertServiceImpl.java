package net.enjoy.springboot.registrationlogin.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.enjoy.springboot.registrationlogin.dto.AlertDto;
import net.enjoy.springboot.registrationlogin.entity.Alert;
import net.enjoy.springboot.registrationlogin.entity.Equipment;
import net.enjoy.springboot.registrationlogin.repository.AlertRepository;
import net.enjoy.springboot.registrationlogin.repository.EquipmentRepository;

@Service
public class AlertServiceImpl implements AlertService {

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Override
    public List<AlertDto> getAllAlerts() {
        return alertRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AlertDto getAlertById(Long id) {
        return alertRepository.findById(id)
                .map(this::convertToDto)
                .orElse(null); // Vous pourriez envisager de lancer une exception si l'alerte n'est pas trouvée
    }

    @Override
    public AlertDto saveAlert(AlertDto alertDto) {
        Alert alert = convertToEntity(alertDto);
        return convertToDto(alertRepository.save(alert));
    }

    @Override
    public void deleteAlert(Long id) {
        alertRepository.deleteById(id);
    }

    @Override
    public List<AlertDto> getAllResolvedAlerts() {
        return alertRepository.findByResolved(true).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AlertDto> getAllUnresolvedAlerts() {
        return alertRepository.findByResolved(false).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void resolveAlert(Long id) {
        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alert not found"));
        alert.setResolved(true);
        alertRepository.save(alert);
    }

    private AlertDto convertToDto(Alert alert) {
        if (alert == null) {
            return null;
        }
        
        AlertDto dto = new AlertDto();
        dto.setId(alert.getId());
        dto.setEquipmentId(alert.getEquipment() != null ? alert.getEquipment().getId() : null);
        dto.setTimestamp(alert.getTimestamp());
        dto.setDescription(alert.getDescription());
        dto.setResolved(alert.isResolved());
        dto.setEquipmentName(alert.getEquipment() != null ? alert.getEquipment().getName() : "Unknown"); // Affiche "Unknown" si l'équipement est nul
        return dto;
    }

    private Alert convertToEntity(AlertDto dto) {
        Alert alert = new Alert();
        alert.setId(dto.getId());

        if (dto.getEquipmentId() != null) {
            Optional<Equipment> equipmentOpt = equipmentRepository.findById(dto.getEquipmentId());
            if (equipmentOpt.isPresent()) {
                alert.setEquipment(equipmentOpt.get());
            } else {
                throw new IllegalArgumentException("Equipment with ID " + dto.getEquipmentId() + " does not exist.");
            }
        }

        alert.setTimestamp(dto.getTimestamp());
        alert.setDescription(dto.getDescription());
        alert.setResolved(dto.isResolved());
        return alert;
    }
}
