package com.example.reminderapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.reminderapi.model.Reminder;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    Reminder findByIdAndUserEmail(Long id, String email);

    List<Reminder> findAllByUserEmail(String email);

}
