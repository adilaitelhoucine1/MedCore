package  com.example.medcore.controller;

import com.example.medcore.dao.UserDAO;
import com.example.medcore.model.Infirmier;
import com.example.medcore.model.MedecinGeneraliste;
import com.example.medcore.model.MedecinSpecialiste;
import com.example.medcore.model.Utilisateur;
import com.example.medcore.service.UserSercive;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException,ServletException  {

        request.getRequestDispatcher("login.jsp").forward(request, response);

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/plain");


        String username = request.getParameter("username");
        String secondname = request.getParameter("secondname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        Utilisateur user = null;
        switch (role) {
            case "INFIRMIER":
                user  = new Infirmier(username,secondname,email,hashedPassword);
                user.setRole(Utilisateur.Role.INFIRMIER);
                break;
            case "GENERALISTE":
                user = new MedecinGeneraliste(username,secondname,email,hashedPassword,null);
                user.setRole(Utilisateur.Role.GENERALISTE);
                break;
            case "SPECIALISTE":
                user = new MedecinSpecialiste(username,secondname,email,hashedPassword,null,null,null);
                user.setRole(Utilisateur.Role.SPECIALISTE);
                break;
            default:

                response.getWriter().println("Unknown role");
                return;
        }
        try {
            UserDAO userDAO = new UserDAO();
            UserSercive userSercive = new UserSercive(userDAO);
            boolean registered = userSercive.register(user);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}