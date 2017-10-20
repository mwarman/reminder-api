package com.example.reminderapi.service;

import com.example.reminderapi.model.User;

public interface UserService {

    User create(User user);

    User findByEmail(String email);

}
