package com.example.reminderapi.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @JsonIgnore
    @ManyToOne(
            optional = false)
    @JoinColumn(
            name = "userId",
            nullable = false,
            updatable = false)
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @PrePersist
    public void doBeforePersist() {
        this.createdAt = new Date();
    }

}
