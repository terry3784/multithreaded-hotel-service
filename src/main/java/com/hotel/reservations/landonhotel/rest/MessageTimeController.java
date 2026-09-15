package com.hotel.reservations.landonhotel.rest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MessageTimeController {

    @GetMapping("/api/status")
    public Map<String, String> getStatus() throws InterruptedException {

        Map<String, String> result = new LinkedHashMap<>();

        // Thread 1: English welcome message
        Thread englishThread = new Thread(() -> {
            ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.ENGLISH);
            result.put("welcomeEn", bundle.getString("welcome.message"));
        }, "EN-Thread");

        // Thread 2: French welcome message
        Thread frenchThread = new Thread(() -> {
            ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.FRENCH);
            result.put("welcomeFr", bundle.getString("welcome.message"));
        }, "FR-Thread");

        // Start both threads
        englishThread.start();
        frenchThread.start();

        englishThread.join();
        frenchThread.join();

        result.putAll(getPresentationTimes());

        return result;
    }

    private Map<String, String> getPresentationTimes() {

        Map<String, String> times = new LinkedHashMap<>();

        // B3a: Conversion method: presentation time between ET, MT, and UTC
        LocalTime presentationET = LocalTime.of(15, 0);

        ZonedDateTime et = presentationET
                .atDate(LocalDate.now())
                .atZone(ZoneId.of("America/New_York"));

        ZonedDateTime mt = et.withZoneSameInstant(ZoneId.of("America/Denver"));
        ZonedDateTime utc = et.withZoneSameInstant(ZoneOffset.UTC);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");

        // B3b: Display the presentation time in all three time zones
        times.put("timeET", et.format(fmt));
        times.put("timeMT", mt.format(fmt));
        times.put("timeUTC", utc.format(fmt));

        return times;
    }
}
