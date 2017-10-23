package com.example.reminderapi.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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

import com.example.reminderapi.model.User;
import com.example.reminderapi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("ci")
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserService userService;

    @WithMockUser
    @Test
    public void createUser() throws Exception {

        User user = new User("tester@example.com", "aBcD1234!");

        when(userService.create(any(User.class))).thenReturn(user);

        MvcResult result = this.mvc
                .perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        verify(userService, times(1)).create(any(User.class));

        assertThat(status).isEqualTo(201);
        assertThat(content).isNotEmpty();
    }

}
