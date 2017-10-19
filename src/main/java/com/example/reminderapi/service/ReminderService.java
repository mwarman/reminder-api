package com.example.reminderapi.service;

import java.util.List;

import com.example.reminderapi.model.Reminder;

public interface ReminderService {

    Reminder create(Reminder reminder);

    List<Reminder> findAll();

    Reminder findById(Long id);

    Reminder update(Reminder reminder);

    Reminder delete(Long id);
}
