package com.example.reminderapi.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.reminderapi.model.Reminder;
import com.example.reminderapi.service.ReminderService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("ci")
@RunWith(SpringRunner.class)
@WebMvcTest(ReminderController.class)
public class ReminderControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ReminderService reminderService;

    @WithMockUser
    @Test
    public void createReminder() throws Exception {
        Reminder reminder = new Reminder("Test the Controller", new Date());
        Reminder savedReminder = new Reminder("Test the Controller",
                new Date());
        savedReminder.doBeforePersist();

        when(reminderService.create(any(Reminder.class)))
                .thenReturn(savedReminder);
        MvcResult result = this.mvc
                .perform(post("/api/reminders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(reminder)))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        verify(reminderService, times(1)).create(any(Reminder.class));

        assertThat(status).isEqualTo(201);
        assertThat(content).isNotNull().isNotEmpty();
    }

    @WithMockUser
    @Test
    public void getAllReminders() throws Exception {
        List<Reminder> reminderList = new ArrayList<Reminder>();
        reminderList.add(new Reminder("Test the Controller", new Date()));

        when(reminderService.findAll()).thenReturn(reminderList);
        MvcResult result = this.mvc.perform(
                get("/api/reminders").accept(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        verify(reminderService, times(1)).findAll();

        assertThat(status).isEqualTo(200);
        assertThat(content).isNotNull().isNotEmpty();
    }

    @WithMockUser
    @Test
    public void getReminder() throws Exception {
        Long reminderId = new Long(1);
        Reminder reminder = new Reminder("Test the Controller", new Date());
        reminder.setId(reminderId);

        when(this.reminderService.findById(reminderId)).thenReturn(reminder);
        MvcResult result = this.mvc.perform(get("/api/reminders/" + reminderId)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        verify(reminderService, times(1)).findById(reminderId);

        assertThat(status).isEqualTo(200);
        assertThat(content).isNotNull().isNotEmpty();
    }

    @WithMockUser
    @Test
    public void getReminderNotFound() throws Exception {
        Long reminderId = Long.MAX_VALUE;

        when(this.reminderService.findById(reminderId)).thenReturn(null);
        MvcResult result = this.mvc.perform(get("/api/reminders/" + reminderId)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        verify(reminderService, times(1)).findById(reminderId);

        assertThat(status).isEqualTo(404);
        assertThat(content).isEmpty();
    }

    @WithMockUser
    @Test
    public void updateReminder() throws Exception {
        Long reminderId = new Long(1);
        Reminder reminder = new Reminder("Test the Controller", new Date());
        reminder.setId(reminderId);

        when(this.reminderService.update(any(Reminder.class)))
                .thenReturn(reminder);
        MvcResult result = this.mvc
                .perform(patch("/api/reminders/" + reminderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(reminder)))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        verify(reminderService, times(1)).update(any(Reminder.class));

        assertThat(status).isEqualTo(200);
        assertThat(content).isNotNull().isNotEmpty();
    }

    @WithMockUser
    @Test
    public void updateReminderNotFound() throws Exception {
        Long reminderId = Long.MAX_VALUE;
        Reminder reminder = new Reminder("Test the Controller", new Date());
        reminder.setId(reminderId);

        when(this.reminderService.update(any(Reminder.class))).thenReturn(null);
        MvcResult result = this.mvc
                .perform(patch("/api/reminders/" + reminderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(reminder)))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        verify(reminderService, times(1)).update(any(Reminder.class));

        assertThat(status).isEqualTo(404);
        assertThat(content).isEmpty();
    }

    @WithMockUser
    @Test
    public void deleteReminder() throws Exception {
        Long reminderId = new Long(1);
        Reminder reminder = new Reminder("Test the Controller", new Date());
        reminder.setId(reminderId);

        when(this.reminderService.delete(reminderId)).thenReturn(reminder);
        MvcResult result = this.mvc
                .perform(delete("/api/reminders/" + reminderId)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        verify(reminderService, times(1)).delete(reminderId);

        assertThat(status).isEqualTo(200);
        assertThat(content).isNotEmpty();
    }

    @WithMockUser
    @Test
    public void deleteReminderNotFound() throws Exception {
        Long reminderId = Long.MAX_VALUE;

        when(this.reminderService.delete(reminderId)).thenReturn(null);
        MvcResult result = this.mvc
                .perform(delete("/api/reminders/" + reminderId)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        verify(reminderService, times(1)).delete(reminderId);

        assertThat(status).isEqualTo(404);
        assertThat(content).isEmpty();
    }

}
