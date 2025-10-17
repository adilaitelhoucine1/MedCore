package com.example.medcore.controller;

import com.example.medcore.dao.ConsultationDAO;
import com.example.medcore.dao.CreneauDAO;
import com.example.medcore.dao.PatientDAO;
import com.example.medcore.dao.UserDAO;
import com.example.medcore.model.*;
import com.example.medcore.service.GeneralistService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet({"/generaliste", "/creneau", "/confirmerDemande"})
public class GeneralisteServelt extends HttpServlet {
    GeneralistService generalistService = new GeneralistService();
    PatientDAO patientDAO = new PatientDAO();
    ConsultationDAO consultationDAO = new ConsultationDAO();
    UserDAO userDAO = new UserDAO();
    CreneauDAO creneauDAO = new CreneauDAO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        switch (path){
            case "/generaliste":

                List<Patient> patients = patientDAO.findPatientswithInfo();
                List<MedecinSpecialiste> specialisteList = generalistService.getAllSpecialiste();
                request.setAttribute("patients", patients);
                request.setAttribute("specialisteList", specialisteList);

                request.getRequestDispatcher("/generaliste/GENERALISTE.jsp").forward(request, response);
                break;
            case "/creneau":
                int specialistId = Integer.parseInt(request.getParameter("medecinId"));
                List<Creneau> creneaux = generalistService.getCreneau(specialistId);
                //todo. handle the creneau based on status
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
