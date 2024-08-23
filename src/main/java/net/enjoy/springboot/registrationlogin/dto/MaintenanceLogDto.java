package net.enjoy.springboot.registrationlogin.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceLogDto {

    private Long id;

    @NotNull(message = "L'ID de l'équipement ne peut pas être nul")
    private Long equipmentId;

    @NotNull(message = "La date et l'heure ne peuvent pas être nulles")
    private LocalDateTime timestamp;

    @Size(min = 1, max = 255, message = "La description doit avoir au moins 1 caractère et au maximum 255 caractères")
    private String description;

    private String equipmentName; // Ajouté pour stocker le nom de l'équipement

    // Getters et Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getEquipmentId() {
        return equipmentId;
    }
    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getEquipmentName() {
        return equipmentName;
    }
    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }
}
