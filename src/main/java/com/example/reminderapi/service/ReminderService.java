package com.example.reminderapi.service;

import java.util.List;

import com.example.reminderapi.model.Reminder;

public interface ReminderService {

    Reminder create(Reminder reminder);

    List<Reminder> findAll();

    // find reminder by id

    // update a reminder

    // delete a reminder
}
