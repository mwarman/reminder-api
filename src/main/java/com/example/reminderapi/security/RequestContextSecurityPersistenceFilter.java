package com.example.reminderapi.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.example.reminderapi.model.User;
import com.example.reminderapi.service.UserService;
import com.example.reminderapi.util.RequestContext;

public class RequestContextSecurityPersistenceFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory
            .getLogger(RequestContextSecurityPersistenceFilter.class);

    private static final String PRINCIPLE_ANONYMOUS = "anonymousUser";

    private UserService userService;

    public RequestContextSecurityPersistenceFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        String principle = (String) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        logger.debug("Principle: {}", principle);

        if (principle == null || principle.equals(PRINCIPLE_ANONYMOUS)) {
            // do nothing for anonymous access
        } else {
            User user = userService.findByEmail(principle);
            logger.debug("Fetched user {}", user);

            RequestContext.setUser(user);
        }

        chain.doFilter(request, response);

    }

}
