package com.example.reminderapi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reminderapi.model.Reminder;
import com.example.reminderapi.repository.ReminderRepository;
import com.example.reminderapi.util.Objects;

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

    @Transactional
    @Override
    public Reminder update(Reminder reminder) {
        Reminder reminderToUpdate = findById(reminder.getId());

        if (reminderToUpdate == null) {
            return null;
        }

        if (!Objects.isNullOrEmpty(reminder.getText())) {
            reminderToUpdate.setText(reminder.getText());
        }
        if (!Objects.isNullOrEmpty(reminder.getDueAt())) {
            reminderToUpdate.setDueAt(reminder.getDueAt());
        }
        if (reminderToUpdate.isComplete() != reminder.isComplete()) {
            if (reminder.isComplete()) {
                reminderToUpdate.setComplete(true);
                reminderToUpdate.setCompletedAt(new Date());
            } else {
                reminderToUpdate.setComplete(false);
                reminderToUpdate.setCompletedAt(null);
            }
        }

        return reminderRepository.save(reminderToUpdate);
    }

    @Transactional
    @Override
    public Reminder delete(Long id) {
        Reminder reminderToDelete = findById(id);

        if (reminderToDelete == null) {
            return null;
        }

        reminderRepository.delete(id);

        return reminderToDelete;
    }

}
