package com.example.reminderapi.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.reminderapi.model.Reminder;
import com.example.reminderapi.repository.ReminderRepository;

@ActiveProfiles("ci")
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReminderServiceTests {

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private ReminderService reminderService;

    private Reminder reminderOne;
    private Reminder reminderTwo;

    @Before
    public void beforeEach() {
        reminderOne = reminderRepository
                .save(new Reminder("Test One", new Date()));

        reminderTwo = new Reminder("Test Two", new Date());
        reminderTwo.setComplete(true);
        reminderTwo.setCompletedAt(new Date());
        reminderRepository.save(reminderTwo);
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
        assertThat(reminders).contains(this.reminderOne);
    }

    @Transactional
    @Test
    public void findReminderById() {
        Reminder result = this.reminderService
                .findById(this.reminderOne.getId());

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(this.reminderOne.getId());
    }

    @Transactional
    @Test
    public void findReminderByIdNotFound() {
        Reminder result = this.reminderService.findById(Long.MAX_VALUE);

        assertThat(result).isNull();
    }

    @Transactional
    @Test
    public void updateReminder() {
        Reminder reminderToUpdate = new Reminder();
        reminderToUpdate.setId(this.reminderOne.getId());
        String text = "Update a Reminder";
        reminderToUpdate.setText(text);

        Reminder updatedReminder = this.reminderService
                .update(reminderToUpdate);

        assertThat(updatedReminder).isNotNull();
        assertThat(updatedReminder.getText()).isEqualTo(text);
    }

    @Transactional
    @Test
    public void updateReminderComplete() {
        Reminder reminderToUpdate = new Reminder();
        reminderToUpdate.setId(this.reminderOne.getId());
        reminderToUpdate.setComplete(true);

        Reminder updatedReminder = this.reminderService
                .update(reminderToUpdate);

        assertThat(updatedReminder).isNotNull();
        assertThat(updatedReminder.isComplete()).isTrue();
        assertThat(updatedReminder.getCompletedAt()).isToday();
    }

    @Transactional
    @Test
    public void updateReminderIncomplete() {
        Reminder reminderToUpdate = new Reminder();
        reminderToUpdate.setId(this.reminderTwo.getId());
        reminderToUpdate.setComplete(false);

        Reminder updatedReminder = this.reminderService
                .update(reminderToUpdate);

        assertThat(updatedReminder).isNotNull();
        assertThat(updatedReminder.isComplete()).isFalse();
        assertThat(updatedReminder.getCompletedAt()).isNull();
    }

    @Transactional
    @Test
    public void updateReminderNotFound() {
        Reminder reminderToUpdate = new Reminder();
        reminderToUpdate.setId(Long.MAX_VALUE);
        String text = "Update a Reminder";
        reminderToUpdate.setText(text);

        Reminder updatedReminder = this.reminderService
                .update(reminderToUpdate);

        assertThat(updatedReminder).isNull();
    }

    @Transactional
    @Test
    public void deleteReminder() {
        Long deletedId = this.reminderOne.getId();
        Reminder deletedReminder = this.reminderService
                .delete(this.reminderOne.getId());

        assertThat(deletedReminder).isNotNull();
        assertThat(deletedReminder.getId()).isEqualTo(deletedId);

        assertThat(this.reminderRepository.count()).isEqualTo(1);
    }

    @Transactional
    @Test
    public void deleteReminderNotFound() {
        Reminder deletedReminder = this.reminderService.delete(Long.MAX_VALUE);

        assertThat(deletedReminder).isNull();

        assertThat(this.reminderRepository.count()).isEqualTo(2);
    }

}
