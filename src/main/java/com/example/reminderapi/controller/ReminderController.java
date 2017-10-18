package com.example.reminderapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.reminderapi.model.Reminder;
import com.example.reminderapi.service.ReminderService;

@RestController
@RequestMapping(
        value = "/api/reminders")
public class ReminderController {

    private ReminderService reminderService;

    @Autowired
    public ReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<Reminder> createReminder(
            @RequestBody Reminder reminder) {

        Reminder savedReminder = reminderService.create(reminder);

        return new ResponseEntity<Reminder>(savedReminder, HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<List<Reminder>> getAllReminders() {

        List<Reminder> reminders = reminderService.findAll();

        return new ResponseEntity<List<Reminder>>(reminders, HttpStatus.OK);
    }

    // Find Reminder by Id

    // Update Reminder

    // Delete Reminder

}
