package com.example.medcore.controller;

import com.example.medcore.dao.UserDAO;
import com.example.medcore.model.Infirmier;
import com.example.medcore.model.MedecinGeneraliste;
import com.example.medcore.model.MedecinSpecialiste;
import com.example.medcore.model.Utilisateur;
import com.example.medcore.service.UserSercive;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.*;

import javax.sql.rowset.serial.SerialException;
import java.io.IOException;

import static java.lang.String.valueOf;

@WebServlet("/login")
public class LoginServlet  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException , ServletException {

        request.getRequestDispatcher("login.jsp").forward(request, response);

    }
    @Override

    public  void  doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException , ServletException {
        Utilisateur user=null;
        String email=request.getParameter("email");
        String password=request.getParameter("password");
        try {
            UserDAO userDAO = new UserDAO();
            UserSercive userSercive = new UserSercive(userDAO);
            user = userSercive.login(email, password);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Login failed: " + e.getMessage());
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        if (user==null){
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }else{
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            switch (user.getRole()) {
                case INFIRMIER:
                    response.sendRedirect("listPatients");

                    break;
                case GENERALISTE:
                    response.sendRedirect(request.getContextPath() +"/generaliste");
                    break;
                case SPECIALISTE:
                    response.sendRedirect("specilaiste");

                    break;
                default:
                    response.getWriter().println("Unknown role");
                    return;
            }

        }
    }

}
