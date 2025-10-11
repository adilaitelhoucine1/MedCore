package com.example.medcore.controller;

import com.example.medcore.dao.PatientDAO;
import com.example.medcore.model.Patient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(urlPatterns = {"/registerPatient", "/listPatients"})
public class PatientServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        switch (path) {
            case "/registerPatient":
                String nom = request.getParameter("nom");
                String prenom = request.getParameter("prenom");
                String dateNaissance = request.getParameter("dateNaissance");
                String numSecu = request.getParameter("numSecu");
                String adresse = request.getParameter("adresse");
                String telephone = request.getParameter("telephone");
                String mutuelle = request.getParameter("mutuelle");


                Patient patient = new Patient();
                patient.setNom(nom);
                patient.setPrenom(prenom);


                if (dateNaissance != null && !dateNaissance.isEmpty()) {
                    patient.setDateNaissance(LocalDate.parse(dateNaissance));
                }

                patient.setNumSecu(numSecu);
                patient.setAdresse(adresse);
                patient.setTelephone(telephone);
                patient.setMutuelle(mutuelle);

                PatientDAO dao = new PatientDAO();
                dao.save(patient);


                response.sendRedirect(request.getContextPath() + "/listPatients");
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        switch (path) {
            case "/listPatients":
                PatientDAO dao = new PatientDAO();
                List<Patient> patients = dao.listPatients();
                request.setAttribute("patients", patients);
                request.getRequestDispatcher("/Infirmier/INFIRMIER.jsp").forward(request, response);
                break;
            case "/generaliste":
              //  response.getWriter().println("-------------------iokiçkçkiçikiçiçkiçkçà;;okuijuijuijuçjuino,juiniuinuiuiuii");
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }
}
