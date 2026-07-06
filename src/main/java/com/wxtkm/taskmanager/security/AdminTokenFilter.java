package com.wxtkm.taskmanager.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AdminTokenFilter extends OncePerRequestFilter {

    @Value("${ADMIN_TOKEN}")
    private String adminToken;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();

        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (
                path.equals("/api/projects")
                        && request.getMethod().equalsIgnoreCase("POST")
        ) {

            String token = request.getHeader("X-ADMIN-TOKEN");

            System.out.println("EXPECTED: " + adminToken);
            System.out.println("RECEIVED: " + token);

            if (token == null || !token.equals(adminToken)) {

                response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "Invalid admin token"
                );

                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}