package com.example.reminderapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ReminderApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReminderApiApplication.class, args);
    }

    @RequestMapping("/")
    public String home() {
        return "Hello World!";
    }
}
