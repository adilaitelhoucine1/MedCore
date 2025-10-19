package com.example.medcore.controller;

import com.example.medcore.dao.UserDAO;
import com.example.medcore.model.Creneau;
import com.example.medcore.model.MedecinSpecialiste;
import com.example.medcore.model.Utilisateur;
import com.example.medcore.service.SpecialisteService;
import com.example.medcore.util.AuthUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet({"/updateSpecialiteProfiile","/specilaiste","/updatedemande"})
public class SpecialiteServelt extends HttpServlet{
    SpecialisteService specialisteService = new SpecialisteService();
    UserDAO userDAO = new UserDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException , ServletException {
        String path = request.getServletPath();
        if (!AuthUtil.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        if (!AuthUtil.hasRole(request, MedecinSpecialiste.class)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès interdit !");
            return;
        }
        switch (path){
            case "/specilaiste":

                HttpSession session = request.getSession(false);
                Utilisateur user = (Utilisateur) session.getAttribute("user");
                Long specialist_id = (Long) user.getId();


                List<Object[]> creneaus = specialisteService.getAllCreneau(specialist_id);
                List<Object[]> Demandes = specialisteService.getAllDemande(specialist_id) ;

                request.setAttribute("creneaus", creneaus);
                request.setAttribute("Demandes", Demandes);
                request.getRequestDispatcher("/specialiste/SPECIALISTE.jsp").forward(request, response);
            return;

                //break;


            default:
                response.getWriter().println("errrrrrrrrrrrrorr");
                break;
        }



        request.getRequestDispatcher("login.jsp").forward(request, response);

    }


    @Override
    protected void doPost(HttpServletRequest request , HttpServletResponse response)
            throws IOException, ServletException {

        String path = request.getServletPath();
        switch (path){
            case"/updateSpecialiteProfiile":

               int user_id=Integer.parseInt(request.getParameter("user_id"));
                String name =  request.getParameter("name");

                 MedecinSpecialiste.Specialite  specialite = MedecinSpecialiste.Specialite.valueOf(request.getParameter("specialite"));
                Double tarif = Double.parseDouble( request.getParameter("tarif"));
                int dureeConsultation =  Integer.parseInt(request.getParameter("dureeConsultation"));
                MedecinSpecialiste medecinSpecialiste=(MedecinSpecialiste) userDAO.findById(user_id);
                specialisteService.updateProfile(medecinSpecialiste,name,specialite,tarif,dureeConsultation);
                response.sendRedirect("specilaiste");
                break;

            case "/updatedemande":
                long id =Long.parseLong(request.getParameter("demandeId"));
                String avis_medecin = request.getParameter("avis_medecin");
                String recommandations = request.getParameter("recommandations");
               boolean updated= specialisteService.updateDemande(id,avis_medecin,recommandations);

               if(updated){
                   response.sendRedirect("specilaiste");
               }
                break;
            default:
                response.getWriter().println("errrrrrrrrrrrrorr");
                break;

        }

     }
}
