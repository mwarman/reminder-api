package com.example.reminderapi.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.reminderapi.model.User;
import com.example.reminderapi.repository.UserRepository;

@ActiveProfiles("ci")
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    private String email = "tester@example.com";
    private String password = "aBcD1234!";

    @Before
    public void beforeEach() {
        userRepository.save(new User(email, passwordEncoder.encode(password)));
    }

    @Transactional
    @Test
    public void createUser() {
        String email = "user@example.com";
        String password = "password";

        User savedUser = userService.create(new User(email, password));

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo(email);
        assertThat(savedUser.getPassword()).isNotEqualTo(password);
        assertThat(passwordEncoder.matches(password, savedUser.getPassword()))
                .isTrue();
    }

    @Transactional
    @Test
    public void findByEmail() {
        User user = userService.findByEmail(email);

        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(passwordEncoder.matches(password, user.getPassword()))
                .isTrue();
    }

    @Transactional
    @Test
    public void findByEmailNotFound() {
        User user = userService.findByEmail("notfound@example.com");

        assertThat(user).isNull();
    }

}
