package com.example.reminderapi.controller;

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
        value = "/api/reminders",
        consumes = "application/json",
        produces = "application/json")
public class ReminderController {

    private ReminderService reminderService;

    @Autowired
    public ReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    // Create Reminder
    @RequestMapping(
            method = RequestMethod.POST)
    public ResponseEntity<Reminder> createReminder(
            @RequestBody Reminder reminder) {

        // Save Reminder in DB
        Reminder savedReminder = reminderService.create(reminder);

        // Return the saved Reminder
        return new ResponseEntity<Reminder>(savedReminder, HttpStatus.OK);
    }

    // Find All Reminders

    // Find Reminder by Id

    // Update Reminder

    // Delete Reminder

}
