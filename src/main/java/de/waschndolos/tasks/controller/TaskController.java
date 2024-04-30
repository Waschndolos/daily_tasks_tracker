package de.waschndolos.tasks.controller;

import de.waschndolos.tasks.model.Task;
import de.waschndolos.tasks.service.EncryptionService;
import de.waschndolos.tasks.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/interruptions")
public class TaskController {

    private final TaskService taskService;
    private final EncryptionService encryptionService;

    public TaskController(TaskService taskService, EncryptionService encryptionService) {
        this.taskService = taskService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("/buttonClicked")
    public String handleButtonAction(HttpServletRequest request, @RequestParam("actionId") int actionId, @RequestParam("isHomeOffice") boolean isHomeOffice, RedirectAttributes redirectAttributes) {        try {

        String identifier = encryptionService.encrypt(getClientIp(request));
        taskService.save(identifier, new Task(actionId, isHomeOffice));
            redirectAttributes.addFlashAttribute("success", "Successfully saved.");
            redirectAttributes.addFlashAttribute("isHomeOffice", isHomeOffice);
            redirectAttributes.addFlashAttribute("identifier", identifier);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("fail", "Error while saving datapoint.");
        }
        return "redirect:/";
    }

    public String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

}
