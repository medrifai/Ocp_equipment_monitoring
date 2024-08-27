package net.enjoy.springboot.registrationlogin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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
        addRoleAttributes(model);
        model.addAttribute("maintenanceLogs", maintenanceLogService.getAllMaintenanceLogs());
        return "maintenance_log_list";
    }

    @GetMapping("/{id}")
    public String viewMaintenanceLog(@PathVariable Long id, Model model) {
        addRoleAttributes(model);
        MaintenanceLogDto maintenanceLog = maintenanceLogService.getMaintenanceLogById(id);
        if (maintenanceLog == null) {
            return "error/404";
        }

        EquipmentDto equipment = equipmentService.getEquipmentById(maintenanceLog.getEquipmentId());
        if (equipment == null) {
            return "error/404";
        }

        model.addAttribute("maintenanceLog", maintenanceLog);
        model.addAttribute("equipment", equipment);

        return "maintenance_log_view";
    }

    @GetMapping("/create")
    public String createMaintenanceLogForm(Model model) {
        addRoleAttributes(model);
        MaintenanceLogDto maintenanceLogDto = new MaintenanceLogDto();
        List<EquipmentDto> equipmentList = equipmentService.getAllEquipments();

        model.addAttribute("maintenanceLog", maintenanceLogDto);
        model.addAttribute("equipmentList", equipmentList);
        return "maintenance_log_form";
    }

    @PostMapping("/create")
    public String saveMaintenanceLog(@ModelAttribute @Valid MaintenanceLogDto maintenanceLogDto, BindingResult result, Model model) {
        addRoleAttributes(model);
        if (result.hasErrors()) {
            List<EquipmentDto> equipmentList = equipmentService.getAllEquipments();
            model.addAttribute("equipmentList", equipmentList);
            return "maintenance_log_form";
        }
        maintenanceLogService.saveMaintenanceLog(maintenanceLogDto);
        return "redirect:/maintenance_logs";
    }

    // Utilisation de @DeleteMapping au lieu de @GetMapping pour la suppression
    @PostMapping("/delete/{id}")
    public String deleteMaintenanceLog(@PathVariable Long id, Model model) {
        MaintenanceLogDto maintenanceLog = maintenanceLogService.getMaintenanceLogById(id);
        if (maintenanceLog == null) {
            return "error/404";
        }
        maintenanceLogService.deleteMaintenanceLog(id);
        return "redirect:/maintenance_logs";
    }

    // Méthode privée pour ajouter l'état d'authentification et de rôle au modèle
    @SuppressWarnings("null")
    private void addRoleAttributes(Model model) {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);

        model.addAttribute("isAuthenticated", isAuthenticated);

        if (isAuthenticated) {
            model.addAttribute("isAdmin", authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN")));
            model.addAttribute("isTechnicien", authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_TECHNICIEN")));
        } else {
            model.addAttribute("isAdmin", false);
            model.addAttribute("isTechnicien", false);
        }
    }
}

