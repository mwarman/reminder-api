package com.example.reminderapi.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

@Entity
public class Reminder {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String text;

    @NotNull
    private Date createdAt;

    @NotNull
    private Date dueAt;

    private boolean complete = false;

    private Date completedAt;

    public Reminder() {

    }

    public Reminder(String text, Date dueAt) {
        this.text = text;
        this.dueAt = dueAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getDueAt() {
        return dueAt;
    }

    public void setDueAt(Date dueAt) {
        this.dueAt = dueAt;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    @PrePersist
    public void doBeforePersist() {
        this.createdAt = new Date();
    }

}
