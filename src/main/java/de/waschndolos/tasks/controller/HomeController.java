package de.waschndolos.tasks.controller;

import de.waschndolos.tasks.model.Interruption;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.UUID;

@Controller
public class HomeController {


    @Value("${spring.application.name}")
    String appName;

    @GetMapping("/")
    public String homePage(Model model, @CookieValue(value = "USER_ID", defaultValue = "unknown") String userId, HttpServletResponse response) {
        if ("".equals(userId)) {
            model.addAttribute("identifier", setCookie(response));
        } else {
            model.addAttribute("identifier", userId);
        }
        model.addAttribute("appName", appName);
        Interruption[] interruptions = Interruption.values();
        Arrays.sort(interruptions, Comparator.comparing(Enum::name));
        model.addAttribute("interruptions", interruptions);
        return "home";
    }

    private String setCookie(HttpServletResponse response) {
        String uniqueID = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("USER_ID", uniqueID);
        LocalDate tenYearsLater = LocalDate.now().plusYears(10);
        Date expiryDate = Date.from(tenYearsLater.atStartOfDay(ZoneId.systemDefault()).toInstant());
        cookie.setMaxAge((int) (expiryDate.getTime() / 1000 - System.currentTimeMillis() / 1000));
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return uniqueID;
    }
}
