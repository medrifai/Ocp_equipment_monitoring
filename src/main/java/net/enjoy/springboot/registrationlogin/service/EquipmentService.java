package net.enjoy.springboot.registrationlogin.service;

import java.util.List;

import net.enjoy.springboot.registrationlogin.dto.EquipmentDto;
import net.enjoy.springboot.registrationlogin.entity.Equipment;

public interface EquipmentService {
    List<EquipmentDto> getAllEquipments();
    EquipmentDto getEquipmentById(Long id);
    void saveEquipment(EquipmentDto equipmentDto);
    void updateEquipment(Long id, EquipmentDto equipmentDto);
    void deleteEquipment(Long id);
    Equipment saveEquipement(EquipmentDto equipmentDto);
}
