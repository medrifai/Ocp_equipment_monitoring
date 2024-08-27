package net.enjoy.springboot.registrationlogin.service;

import java.util.List;

import net.enjoy.springboot.registrationlogin.dto.AlertDto;

public interface AlertService {
    List<AlertDto> getAllAlerts();
    AlertDto getAlertById(Long id);
    AlertDto saveAlert(AlertDto alertDto);
    void deleteAlert(Long id);
    List<AlertDto> getAllResolvedAlerts();
    List<AlertDto> getAllUnresolvedAlerts();
    void resolveAlert(Long id); // Ajouté
}
