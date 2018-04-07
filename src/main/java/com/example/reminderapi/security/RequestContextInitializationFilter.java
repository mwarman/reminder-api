package com.example.reminderapi.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.example.reminderapi.util.RequestContext;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class RequestContextInitializationFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory
            .getLogger(RequestContextInitializationFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        RequestContext.init();

        chain.doFilter(request, response);

    }

}
