package de.waschndolos.tasks.controller;

import de.waschndolos.tasks.model.Focus;
import de.waschndolos.tasks.model.Interruption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.Comparator;

@Controller
public class HomeController {


    @Value("${spring.application.name}")
    String appName;

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        Interruption[] interruptions = Interruption.values();
        Arrays.sort(interruptions, Comparator.comparing(Enum::name));
        model.addAttribute("interruptions", interruptions);

        Focus[] focus = Focus.values();
        Arrays.sort(focus, Comparator.comparing(Focus::name));

        model.addAttribute("focus", focus);
        return "home";
    }
}
