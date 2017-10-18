package com.example.reminderapi.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.reminderapi.model.Reminder;
import com.example.reminderapi.repository.ReminderRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReminderServiceTests {

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private ReminderService reminderService;

    private Reminder reminder;

    @Before
    public void beforeEach() {
        reminder = reminderRepository
                .save(new Reminder("Test One", new Date()));
        reminderRepository.save(new Reminder("Test One", new Date()));
    }

    @Transactional
    @Test
    public void createReminder() {
        String text = "Test the Service";
        Date dueAt = new Date();
        Reminder input = new Reminder(text, dueAt);

        Reminder result = this.reminderService.create(input);

        assertThat(result).isNotNull();
        assertThat(result.getText()).isEqualTo(text);
        assertThat(result.getDueAt()).isEqualTo(dueAt);
        assertThat(result.getCreatedAt()).isNotNull();
        assertThat(result.isComplete()).isFalse();
        assertThat(result.getCompletedAt()).isNull();

        assertThat(this.reminderRepository.count()).isEqualTo(3);
    }

    @Transactional
    @Test
    public void findAllReminders() {
        List<Reminder> reminders = this.reminderService.findAll();

        assertThat(reminders).isNotNull().isNotEmpty();
        assertThat(reminders.size()).isEqualTo(2);
        assertThat(reminders).contains(this.reminder);
    }

    @Transactional
    @Test
    public void findReminderById() {
        Reminder result = this.reminderService.findById(this.reminder.getId());

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(this.reminder.getId());
    }

    @Transactional
    @Test
    public void findReminderByIdNotFound() {
        Reminder result = this.reminderService.findById(Long.MAX_VALUE);

        assertThat(result).isNull();
    }

}
