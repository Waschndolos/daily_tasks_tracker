package de.waschndolos.tasks.controller;

import de.waschndolos.tasks.model.Interruption;
import de.waschndolos.tasks.model.Task;
import de.waschndolos.tasks.service.CookieService;
import de.waschndolos.tasks.service.InterruptionService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Comparator;

@Controller
public class HomeController {

    private final CookieService cookieService;
    private final InterruptionService interruptionService;

    @Value("${spring.application.name}")
    String appName;

    public HomeController(CookieService cookieService, InterruptionService interruptionService) {
        this.cookieService = cookieService;
        this.interruptionService = interruptionService;
    }

    @GetMapping("/")
    public String homePage(Model model, @CookieValue(value = "USER_ID", defaultValue = "") String userId, HttpServletResponse response) {
        fillModel(userId, model, response);
        return "home";
    }

    @PostMapping("/send")
    public String handleButtonAction(@CookieValue(value = "USER_ID", defaultValue = "") String userId, @RequestParam("actionId") int actionId, @RequestParam("isHomeOffice") boolean isHomeOffice, Model model,  HttpServletResponse response) {

        fillModel(userId, model, response);
        try {
            interruptionService.save(userId, new Task(actionId, isHomeOffice));
            model.addAttribute("success", "Successfully saved.");
            model.addAttribute("isHomeOffice", isHomeOffice);
        } catch (Exception e) {
            model.addAttribute("fail", "Error while saving datapoint.");
        }
        return "home";
    }

    private void fillModel(String userId, Model model, HttpServletResponse response) {
        if (userId == null || userId.isEmpty()) {
            model.addAttribute("identifier", cookieService.createCookie(response));
        } else {
            model.addAttribute("identifier", userId);
        }
        model.addAttribute("appName", appName);
        Interruption[] interruptions = Interruption.values();
        Arrays.sort(interruptions, Comparator.comparing(Enum::name));
        model.addAttribute("interruptions", interruptions);
    }
}
