package com.example.reminderapi.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

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

    @Before
    public void beforeEach() {
        Reminder reminderOne = reminderRepository
                .save(new Reminder("Test One", new Date()));
        Reminder reminderTwo = reminderRepository
                .save(new Reminder("Test One", new Date()));
    }

    @Transactional
    @Test
    public void createReminder() {
        String text = "Test the Service";
        Date dueAt = new Date();
        Reminder input = new Reminder(text, dueAt);

        Reminder result = reminderService.create(input);

        assertThat(result).isNotNull();
        assertThat(result.getText()).isEqualTo(text);
        assertThat(result.getDueAt()).isEqualTo(dueAt);
        assertThat(result.getCreatedAt()).isNotNull();
        assertThat(result.isComplete()).isFalse();
        assertThat(result.getCompletedAt()).isNull();

        assertThat(reminderRepository.count()).isEqualTo(3);
    }

}
