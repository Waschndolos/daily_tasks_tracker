package de.waschndolos.tasks.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Service
public class CookieService {

    public String createCookie(HttpServletResponse response) {
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
