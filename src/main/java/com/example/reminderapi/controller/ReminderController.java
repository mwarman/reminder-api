package com.example.reminderapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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

        return new ResponseEntity<Reminder>(savedReminder, HttpStatus.CREATED);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<List<Reminder>> getAllReminders() {

        List<Reminder> reminders = reminderService.findAll();

        return new ResponseEntity<List<Reminder>>(reminders, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<Reminder> getReminder(@PathVariable("id") Long id) {

        Reminder reminder = reminderService.findById(id);
        if (reminder == null) {
            return new ResponseEntity<Reminder>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Reminder>(reminder, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/{id}",
            method = { RequestMethod.PUT, RequestMethod.PATCH },
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<Reminder> updateReminder(@PathVariable("id") Long id,
            @RequestBody Reminder reminder) {

        reminder.setId(id);

        Reminder updatedReminder = reminderService.update(reminder);
        if (updatedReminder == null) {
            return new ResponseEntity<Reminder>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Reminder>(updatedReminder, HttpStatus.OK);
    }

    // Delete Reminder

}
