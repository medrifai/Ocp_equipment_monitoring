package net.enjoy.springboot.registrationlogin.dto;

import java.util.List;

public class ReportRequestDto {
    private String startDate;
    private String endDate;
    private String equipmentType;
    private String alertType;
    private List<String> kpi;

    // Getters and Setters

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public List<String> getKpi() {
        return kpi;
    }

    public void setKpi(List<String> kpi) {
        this.kpi = kpi;
    }
}
