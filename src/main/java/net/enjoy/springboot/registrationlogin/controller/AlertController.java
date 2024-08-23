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
        model.addAttribute("alerts", alertService.getAllAlerts());
        return "alert_list";
    }

    @GetMapping("/resolved")
    public String listResolvedAlerts(Model model) {
        model.addAttribute("alerts", alertService.getAllResolvedAlerts());
        return "alert_list";
    }

    @GetMapping("/unresolved")
    public String listUnresolvedAlerts(Model model) {
        model.addAttribute("alerts", alertService.getAllUnresolvedAlerts());
        return "alert_list";
    }

    @GetMapping("/create")
    public String createAlertForm(Model model) {
        AlertDto alertDto = new AlertDto();
        List<EquipmentDto> equipmentList = equipmentService.getAllEquipments();

        model.addAttribute("alert", alertDto);
        model.addAttribute("equipmentList", equipmentList);
        return "alert_form";
    }

    @PostMapping("/create")
    public String saveAlert(@ModelAttribute @Valid AlertDto alertDto, BindingResult result, Model model) {
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
        AlertDto alert = alertService.getAlertById(id);
        if (alert == null) {
            return "error/404";
        }
        model.addAttribute("alert", alert);
        return "alert_view";
    }

    @GetMapping("/edit/{id}")
    public String editAlertForm(@PathVariable Long id, Model model) {
        AlertDto alertDto = alertService.getAlertById(id);
        if (alertDto == null) {
            return "error/404";
        }
        List<EquipmentDto> equipmentList = equipmentService.getAllEquipments();
        model.addAttribute("alert", alertDto);
        model.addAttribute("equipmentList", equipmentList);
        return "alert_form"; // Reuse the same form template for editing
    }

    @PostMapping("/edit/{id}")
    public String updateAlert(@PathVariable Long id, @ModelAttribute @Valid AlertDto alertDto, BindingResult result, Model model) {
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

    @PostMapping("/update/{id}")
    public String updateAlertStatus(@PathVariable Long id, @ModelAttribute("resolved") boolean resolved) {
        AlertDto alertDto = alertService.getAlertById(id);
        if (alertDto != null) {
            alertDto.setResolved(resolved);
            alertService.saveAlert(alertDto);
        }
        return "redirect:/alerts";
    }
}
