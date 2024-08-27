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
import net.enjoy.springboot.registrationlogin.dto.AlertDto;
import net.enjoy.springboot.registrationlogin.dto.EquipmentDto;
import net.enjoy.springboot.registrationlogin.service.AlertService;
import net.enjoy.springboot.registrationlogin.service.EquipmentService;

@Controller
@RequestMapping("/alerts")
public class AlertController {

    @Autowired
    private AlertService alertService;

    @Autowired
    private EquipmentService equipmentService;

    @GetMapping
    public String listAlerts(Model model) {
        addRoleAttributes(model);
        model.addAttribute("alerts", alertService.getAllAlerts());
        return "alert_list";
    }

    @GetMapping("/resolved")
    public String listResolvedAlerts(Model model) {
        addRoleAttributes(model);
        model.addAttribute("alerts", alertService.getAllResolvedAlerts());
        return "alert_list";
    }

    @GetMapping("/unresolved")
    public String listUnresolvedAlerts(Model model) {
        addRoleAttributes(model);
        model.addAttribute("alerts", alertService.getAllUnresolvedAlerts());
        return "alert_list";
    }

    @GetMapping("/create")
    public String createAlertForm(Model model) {
        addRoleAttributes(model);
        AlertDto alertDto = new AlertDto();
        List<EquipmentDto> equipmentList = equipmentService.getAllEquipments();

        model.addAttribute("alert", alertDto);
        model.addAttribute("equipmentList", equipmentList);
        return "alert_form";
    }

    @PostMapping("/create")
    public String saveAlert(@ModelAttribute @Valid AlertDto alertDto, BindingResult result, Model model) {
        addRoleAttributes(model);
        if (result.hasErrors()) {
            List<EquipmentDto> equipmentList = equipmentService.getAllEquipments();
            model.addAttribute("equipmentList", equipmentList);
            return "alert_form";
        }
        alertService.saveAlert(alertDto);
        return "redirect:/alerts";
    }

    @GetMapping("/{id}")
    public String viewAlert(@PathVariable Long id, Model model) {
        addRoleAttributes(model);
        AlertDto alert = alertService.getAlertById(id);
        if (alert == null) {
            return "error/404";
        }
        model.addAttribute("alert", alert);
        return "alert_view";
    }

    @GetMapping("/edit/{id}")
    public String editAlertForm(@PathVariable Long id, Model model) {
        addRoleAttributes(model);
        AlertDto alertDto = alertService.getAlertById(id);
        if (alertDto == null) {
            return "error/404";
        }
        List<EquipmentDto> equipmentList = equipmentService.getAllEquipments();
        model.addAttribute("alert", alertDto);
        model.addAttribute("equipmentList", equipmentList);
        return "alert_form";
    }

    @PostMapping("/edit/{id}")
    public String updateAlert(@PathVariable Long id, @ModelAttribute @Valid AlertDto alertDto, BindingResult result, Model model) {
        addRoleAttributes(model);
        if (result.hasErrors()) {
            List<EquipmentDto> equipmentList = equipmentService.getAllEquipments();
            model.addAttribute("equipmentList", equipmentList);
            return "alert_form";
        }
        alertDto.setId(id); // Ensure the ID is set for updating
        alertService.saveAlert(alertDto);
        return "redirect:/alerts";
    }

    @GetMapping("/delete/{id}")
    public String deleteAlert(@PathVariable Long id) {
        alertService.deleteAlert(id);
        return "redirect:/alerts";
    }

    @PostMapping("/resolve/{id}")
    public String resolveAlert(@PathVariable Long id) {
        alertService.resolveAlert(id);
        return "redirect:/alerts";
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
