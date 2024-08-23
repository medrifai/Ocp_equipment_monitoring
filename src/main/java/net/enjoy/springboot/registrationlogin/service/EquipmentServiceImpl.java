package net.enjoy.springboot.registrationlogin.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.enjoy.springboot.registrationlogin.dto.EquipmentDto;
import net.enjoy.springboot.registrationlogin.entity.Equipment;
import net.enjoy.springboot.registrationlogin.repository.EquipmentRepository;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    private static final Logger logger = LoggerFactory.getLogger(EquipmentServiceImpl.class);

    @Autowired
    private EquipmentRepository equipmentRepository;

    // Chemin du répertoire de téléchargement doit être configuré via des propriétés ou variables d'environnement
    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads"; 

    @Override
    public List<EquipmentDto> getAllEquipments() {
        return equipmentRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public EquipmentDto getEquipmentById(Long id) {
        return equipmentRepository.findById(id)
                .map(this::convertToDto)
                .orElse(null);
    }

    @Override
    public void saveEquipment(EquipmentDto equipmentDto) {
        Equipment equipment = convertToEntity(equipmentDto);

        // Gestion de la photo
        if (equipmentDto.getPhoto() != null && !equipmentDto.getPhoto().isEmpty()) {
            try {
                String photoUrl = savePhoto(equipmentDto.getPhoto());
                equipment.setPhotoUrl(photoUrl);
            } catch (IOException e) {
                logger.error("Failed to save photo for new equipment", e);
                throw new RuntimeException("Failed to save photo for new equipment", e);
            }
        }

        equipmentRepository.save(equipment);
    }

    @Override
    public void updateEquipment(Long id, EquipmentDto equipmentDto) {
        if (equipmentRepository.existsById(id)) {
            Equipment equipment = convertToEntity(equipmentDto);
            equipment.setId(id);

            // Gestion de la mise à jour de la photo
            if (equipmentDto.getPhoto() != null && !equipmentDto.getPhoto().isEmpty()) {
                try {
                    String photoUrl = savePhoto(equipmentDto.getPhoto());
                    equipment.setPhotoUrl(photoUrl);
                } catch (IOException e) {
                    logger.error("Failed to save photo for equipment with id " + id, e);
                    throw new RuntimeException("Failed to update photo for equipment with id " + id, e);
                }
            }

            equipmentRepository.save(equipment);
        } else {
            logger.warn("Equipment with id {} not found for update", id);
        }
    }

    @Override
    public void deleteEquipment(Long id) {
        if (equipmentRepository.existsById(id)) {
            equipmentRepository.deleteById(id);
        } else {
            logger.warn("Equipment with id {} not found for deletion", id);
        }
    }

    private EquipmentDto convertToDto(Equipment equipment) {
        EquipmentDto dto = new EquipmentDto();
        dto.setId(equipment.getId());
        dto.setName(equipment.getName());
        dto.setLocation(equipment.getLocation());
        dto.setPhotoUrl(equipment.getPhotoUrl());
        return dto;
    }

    private Equipment convertToEntity(EquipmentDto dto) {
        Equipment equipment = new Equipment();
        equipment.setId(dto.getId());
        equipment.setName(dto.getName());
        equipment.setLocation(dto.getLocation());
        equipment.setPhotoUrl(dto.getPhotoUrl());
        return equipment;
    }

    private String savePhoto(MultipartFile photo) throws IOException {
        // Générer un nom de fichier unique
        String fileName = UUID.randomUUID().toString() + "_" + photo.getOriginalFilename();
        // Déterminer le chemin du fichier de destination
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // Crée le répertoire s'il n'existe pas
        }
        File destinationFile = new File(uploadDir, fileName);
        // Enregistrer le fichier sur le disque
        photo.transferTo(destinationFile);
        // Retourner l'URL ou le chemin relatif du fichier enregistré
        return "/uploads/" + fileName; // Assurez-vous que ce chemin est accessible via votre serveur web
    }

    @Override
    public Equipment saveEquipement(EquipmentDto equipmentDto) {
        Equipment equipment = new Equipment();
        equipment.setName(equipmentDto.getName());
        equipment.setLocation(equipmentDto.getLocation());
        equipment.setPhotoUrl(equipmentDto.getPhotoUrl());
        return equipmentRepository.save(equipment);
    }
    
}
