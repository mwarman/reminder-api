package com.example.reminderapi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.reminderapi.model.User;

public final class RequestContext {

    private static final Logger logger = LoggerFactory
            .getLogger(RequestContext.class);

    private static ThreadLocal<User> users = new ThreadLocal<User>();

    private RequestContext() {

    }

    public static User getUser() {
        return users.get();
    }

    public static void setUser(User user) {
        users.set(user);
        logger.debug("Set User {} in RequestContext", user.getEmail());
    }

    public static void init() {
        users.set(null);
        logger.debug("Initialized RequestContext");
    }

}
