package com.example.reminderapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.reminderapi.model.Reminder;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {

}
