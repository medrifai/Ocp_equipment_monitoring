package net.enjoy.springboot.registrationlogin.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceDataDto {
    private Long id;
    private Long equipmentId;
    private LocalDateTime timestamp;
    private float productionActual;
    private float productionTheoretical;
    private boolean operationalStatus;

    // Getters and Setters

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

    public float getProductionActual() {
        return productionActual;
    }

    public void setProductionActual(float productionActual) {
        this.productionActual = productionActual;
    }

    public float getProductionTheoretical() {
        return productionTheoretical;
    }

    public void setProductionTheoretical(float productionTheoretical) {
        this.productionTheoretical = productionTheoretical;
    }

    public boolean isOperationalStatus() {
        return operationalStatus;
    }

    public void setOperationalStatus(boolean operationalStatus) {
        this.operationalStatus = operationalStatus;
    }
}
