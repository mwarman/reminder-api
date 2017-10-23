package com.example.reminderapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("ci")
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReminderApiApplicationTests {

    @Test
    public void contextLoads() {
    }

}
