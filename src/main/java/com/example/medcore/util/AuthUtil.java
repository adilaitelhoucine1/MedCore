package com.example.medcore.util;

import com.example.medcore.model.Utilisateur;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AuthUtil {

    // Check if user is logged in
    public static boolean isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("user") != null;
    }

    // Redirect to login if not logged in
    public static void requireLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }

    // Check user role
    public static boolean hasRole(HttpServletRequest request, Class<?> roleClass) {
        HttpSession session = request.getSession(false);
        if (session == null) return false;

        Object user = session.getAttribute("user");
        return user != null && roleClass.isInstance(user);
    }

    // Require role or redirect/deny
    public static void requireRole(HttpServletRequest request, HttpServletResponse response, Class<?> roleClass) throws IOException {
        if (!hasRole(request, roleClass)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès interdit !");
        }
    }
}
