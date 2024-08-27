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
public class AlertDto {

    private Long id;

    @NotNull(message = "L'équipement ne peut pas être nul")
    private Long equipmentId;
    @NotNull(message = "La date et l'heure ne peuvent pas être nulles")
    private LocalDateTime timestamp;

    @Size(min = 1, max = 255, message = "La description doit avoir au moins 1 caractère et au maximum 255 caractères")
    private String description;

    private boolean resolved;

    // Nouveau champ pour afficher le nom de l'équipement au lieu de l'ID
    private String equipmentName;

    // Getters et Setters pour le nouveau champ
    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }
}
