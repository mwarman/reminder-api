package com.example.reminderapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reminderapi.model.Reminder;
import com.example.reminderapi.repository.ReminderRepository;

@Service
public class ReminderServiceBean implements ReminderService {

    private ReminderRepository reminderRepository;

    @Autowired
    public ReminderServiceBean(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    @Transactional
    @Override
    public Reminder create(Reminder reminder) {
        return reminderRepository.save(reminder);
    }

    @Override
    public List<Reminder> findAll() {
        return reminderRepository.findAll();
    }

    @Override
    public Reminder findById(Long id) {
        return reminderRepository.findOne(id);
    }

}
