package de.waschndolos.tasks.controller;

import de.waschndolos.tasks.model.Task;
import de.waschndolos.tasks.service.InterruptionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/interruptions")
public class InterruptionController {

    private final InterruptionService interruptionService;

    public InterruptionController(InterruptionService interruptionService) {
        this.interruptionService = interruptionService;
    }

    @PostMapping("/buttonClicked")
    public String handleButtonAction(@CookieValue(value = "USER_ID", defaultValue = "unknown") String userId, @RequestParam("actionId") int actionId, @RequestParam("isHomeOffice") boolean isHomeOffice, RedirectAttributes redirectAttributes) {

        try {
            interruptionService.save(userId, new Task(actionId, isHomeOffice));
            redirectAttributes.addFlashAttribute("success", "Successfully saved.");
            redirectAttributes.addFlashAttribute("isHomeOffice", isHomeOffice);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("fail", "Error while saving datapoint.");
        }
        return "redirect:/";
    }
}
