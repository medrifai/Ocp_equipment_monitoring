package net.enjoy.springboot.registrationlogin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import net.enjoy.springboot.registrationlogin.dto.EquipmentDto;
import net.enjoy.springboot.registrationlogin.dto.MaintenanceLogDto;
import net.enjoy.springboot.registrationlogin.service.EquipmentService;
import net.enjoy.springboot.registrationlogin.service.MaintenanceLogService;

@Controller
@RequestMapping("/maintenance_logs")
public class MaintenanceLogController {

    @Autowired
    private MaintenanceLogService maintenanceLogService;

    @Autowired
    private EquipmentService equipmentService;

    @GetMapping
    public String listMaintenanceLogs(Model model) {
        model.addAttribute("maintenanceLogs", maintenanceLogService.getAllMaintenanceLogs());
        return "maintenance_log_list";
    }

    @GetMapping("/{id}")
    public String viewMaintenanceLog(@PathVariable Long id, Model model) {
        MaintenanceLogDto maintenanceLog = maintenanceLogService.getMaintenanceLogById(id);
        if (maintenanceLog == null) {
            return "error/404";
        }
        model.addAttribute("maintenanceLog", maintenanceLog);
        return "maintenance_log_view";
    }

    @GetMapping("/create")
    public String createMaintenanceLogForm(Model model) {
        MaintenanceLogDto maintenanceLogDto = new MaintenanceLogDto();
        List<EquipmentDto> equipmentList = equipmentService.getAllEquipments();

        model.addAttribute("maintenanceLog", maintenanceLogDto);
        model.addAttribute("equipmentList", equipmentList); // Ajouter la liste des équipements au modèle
        return "maintenance_log_form";
    }

    @PostMapping("/create")
    public String saveMaintenanceLog(@ModelAttribute @Valid MaintenanceLogDto maintenanceLogDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<EquipmentDto> equipmentList = equipmentService.getAllEquipments();
            model.addAttribute("equipmentList", equipmentList); // Réajouter la liste des équipements en cas d'erreur
            return "maintenance_log_form";
        }
        maintenanceLogService.saveMaintenanceLog(maintenanceLogDto);
        return "redirect:/maintenance_logs";
    }

    @GetMapping("/delete/{id}")
    public String deleteMaintenanceLog(@PathVariable Long id) {
        MaintenanceLogDto maintenanceLog = maintenanceLogService.getMaintenanceLogById(id);
        if (maintenanceLog == null) {
            return "error/404";
        }
        maintenanceLogService.deleteMaintenanceLog(id);
        return "redirect:/maintenance_logs";
    }
}
