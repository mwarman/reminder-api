package com.example.reminderapi.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.reminderapi.model.Reminder;
import com.example.reminderapi.service.ReminderService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(ReminderController.class)
public class ReminderControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReminderService reminderService;

    @Test
    public void createReminder() throws Exception {
        Reminder reminder = new Reminder("Test the Controller", new Date());
        Reminder savedReminder = new Reminder("Test the Controller",
                new Date());
        savedReminder.doBeforePersist();

        when(reminderService.create(any(Reminder.class)))
                .thenReturn(savedReminder);
        MvcResult result = this.mvc
                .perform(
                        post("/api/reminders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper()
                                        .writeValueAsString(reminder)))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        verify(reminderService, times(1)).create(any(Reminder.class));

        assertThat(status).isEqualTo(200);
        assertThat(content).isNotNull().isNotEmpty();
    }

}
