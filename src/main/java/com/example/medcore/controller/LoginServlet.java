package com.example.medcore.controller;

import com.example.medcore.dao.UserDAO;
import com.example.medcore.service.UserSercive;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.*;

import javax.sql.rowset.serial.SerialException;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException , ServletException {

        request.getRequestDispatcher("login.jsp").forward(request, response);

    }

    public  void  doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException , ServletException {

        String username=request.getParameter("username");
        String password=request.getParameter("password");
        try {
            UserDAO userDAO = new UserDAO();
            UserSercive userSercive = new UserSercive(userDAO);
            boolean logged = userSercive.login(username,password);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
