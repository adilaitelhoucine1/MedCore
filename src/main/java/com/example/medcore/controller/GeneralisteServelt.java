package com.example.medcore.controller;

import com.example.medcore.dao.ConsultationDAO;
import com.example.medcore.dao.CreneauDAO;
import com.example.medcore.dao.PatientDAO;
import com.example.medcore.dao.UserDAO;
import com.example.medcore.model.*;
import com.example.medcore.service.GeneralistService;
import com.example.medcore.util.AuthUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet({"/generaliste", "/creneau", "/confirmerDemande"})
public class GeneralisteServelt extends HttpServlet {
    GeneralistService   generalistService = new GeneralistService();
    PatientDAO patientDAO = new PatientDAO();
    ConsultationDAO consultationDAO = new ConsultationDAO();
    UserDAO userDAO = new UserDAO();
    CreneauDAO creneauDAO = new CreneauDAO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        if (!AuthUtil.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        if (!AuthUtil.hasRole(request, MedecinGeneraliste.class)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès interdit !");
            return;
        }
        switch (path){
            case "/generaliste":

                List<Patient> patients = patientDAO.findPatientswithInfo();
                List<MedecinSpecialiste> specialisteList = generalistService.getAllSpecialiste();
                request.setAttribute("patients", patients);
                request.setAttribute("specialisteList", specialisteList);

                request.getRequestDispatcher("/generaliste/GENERALISTE.jsp").forward(request, response);
                break;
            case "/creneau":
               Long specialistId = Long.parseLong(request.getParameter("medecinId"));
                List<Object[]> creneaux = generalistService.getCreneau(specialistId);
             //   System.out.println("sizzze"+ creneaux.size());
              //  response.getWriter().println(specialistId);
               // response.getWriter().println(request.getParameter("consultationId"));


                request.setAttribute("creneaux", creneaux);
                request.getRequestDispatcher("/generaliste/Creneau.jsp").forward(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String path = request.getServletPath();
        switch (path) {
            case "/confirmerDemande":

                long consultationid = Long.parseLong(request.getParameter("consultationId"));
                int medecinId = Integer.parseInt(request.getParameter("medecinId"));
                int creneauId = Integer.parseInt(request.getParameter("creneauId"));
                Creneau creneau = creneauDAO.findById(creneauId);
                Consultation consultation = consultationDAO.findByID(consultationid);
                MedecinSpecialiste medecinSpecialiste = (MedecinSpecialiste) userDAO.findById(medecinId);

                String question = request.getParameter("question");
                String priorityParam = request.getParameter("priority");
                DemandeExpertise.Priorite priorite = DemandeExpertise.Priorite.valueOf(priorityParam.toUpperCase());

                DemandeExpertise demandeExpertise = new DemandeExpertise(consultation, medecinSpecialiste, question, priorite);
                boolean added = generalistService.saveDemande(demandeExpertise);
                boolean updated = generalistService.updateStatusCreaneau(creneau, medecinSpecialiste, consultation);
                if (added && updated) {
                    response.sendRedirect("generaliste");
                }
                break;
            default:
                response.getWriter().println("no path found");
                break;
        }

    }
}
