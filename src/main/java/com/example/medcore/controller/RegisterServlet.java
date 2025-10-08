package  com.example.medcore.controller;

import com.example.medcore.dao.UserDAO;
import com.example.medcore.model.Infirmier;
import com.example.medcore.model.MedecinGeneraliste;
import com.example.medcore.model.MedecinSpecialiste;
import com.example.medcore.model.Utilisateur;
import com.example.medcore.service.UserSercive;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/plain");


        String username = request.getParameter("username");
        String secondname = request.getParameter("secondname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        Utilisateur user = null;
        switch (role) {
            case "INFIRMIER":
                user  = new Infirmier(username,secondname,email,password);
                user.setRole(Utilisateur.Role.INFIRMIER);
                break;
            case "GENERALISTE":
                user = new MedecinGeneraliste(username,secondname,email,password,null);
                user.setRole(Utilisateur.Role.GENERALISTE);
                break;
            case "SPECIALISTE":
                user = new MedecinSpecialiste(username,secondname,email,password,null,null,null);
                user.setRole(Utilisateur.Role.GENERALISTE);
                break;
            default:

                response.getWriter().println("Unknown role");
                return;
        }
        try {
            UserDAO userDAO = new UserDAO();
            UserSercive userSercive = new UserSercive(userDAO);
            userSercive.register(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}