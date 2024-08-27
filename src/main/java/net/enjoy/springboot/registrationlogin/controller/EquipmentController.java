package net.enjoy.springboot.registrationlogin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
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
import net.enjoy.springboot.registrationlogin.service.EquipmentService;

@Controller
@RequestMapping("/equipments")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @GetMapping
    public String listEquipments(Model model) {
        List<EquipmentDto> equipments = equipmentService.getAllEquipments();
        model.addAttribute("equipments", equipments);

        // Ajoutez les informations d'authentification et de rôle pour la vue
        addAuthenticationAttributes(model);

        return "equipments";
    }

    @GetMapping("/{id}")
    public String viewEquipment(@PathVariable Long id, Model model) {
        EquipmentDto equipment = equipmentService.getEquipmentById(id);
        if (equipment == null) {
            return "error/404";
        }
        model.addAttribute("equipment", equipment);

        // Ajoutez les informations d'authentification et de rôle pour la vue
        addAuthenticationAttributes(model);

        return "equipment_view";
    }

    @GetMapping("/create")
    public String createEquipmentForm(Model model) {
        model.addAttribute("equipment", new EquipmentDto());

        // Ajoutez les informations d'authentification et de rôle pour la vue
        addAuthenticationAttributes(model);

        return "equipment-form";
    }

    @PostMapping("/create")
    public String saveEquipment(@ModelAttribute @Valid EquipmentDto equipmentDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Ajoutez les informations d'authentification et de rôle pour la vue
            addAuthenticationAttributes(model);
            return "equipment-form";
        }
        equipmentService.saveEquipment(equipmentDto);
        return "redirect:/equipments";
    }

    @GetMapping("/edit/{id}")
    public String editEquipmentForm(@PathVariable Long id, Model model) {
        EquipmentDto equipment = equipmentService.getEquipmentById(id);
        if (equipment == null) {
            return "error/404";
        }
        model.addAttribute("equipment", equipment);

        // Ajoutez les informations d'authentification et de rôle pour la vue
        addAuthenticationAttributes(model);

        return "equipment-form";
    }

    @PostMapping("/edit/{id}")
    public String updateEquipment(@PathVariable Long id, @ModelAttribute @Valid EquipmentDto equipmentDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Ajoutez les informations d'authentification et de rôle pour la vue
            addAuthenticationAttributes(model);
            return "equipment_form";
        }
        equipmentService.updateEquipment(id, equipmentDto);
        return "redirect:/equipments";
    }

    @GetMapping("/delete/{id}")
    public String deleteEquipment(@PathVariable Long id) {
        EquipmentDto equipment = equipmentService.getEquipmentById(id);
        if (equipment == null) {
            return "error/404";
        }
        equipmentService.deleteEquipment(id);
        return "redirect:/equipments";
    }

    @GetMapping("/add")
    public String showAddEquipmentForm(Model model) {
        model.addAttribute("equipment", new EquipmentDto());

        // Ajoutez les informations d'authentification et de rôle pour la vue
        addAuthenticationAttributes(model);

        return "add-equipment";
    }

    @PostMapping("/add")
    public String addEquipment(@ModelAttribute("equipment") EquipmentDto equipmentDto) {
        equipmentService.saveEquipment(equipmentDto);
        return "redirect:/equipments";
    }

    @SuppressWarnings("null")
    private void addAuthenticationAttributes(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
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
