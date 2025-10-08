package com.example.medcore.controller;

import com.example.medcore.dao.PatientDAO;
import com.example.medcore.model.Patient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/registerPatient")
public class PatientServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
        patient.setDateNaissance(LocalDate.parse(dateNaissance));
        patient.setNumSecu(numSecu);
        patient.setAdresse(adresse);
        patient.setTelephone(telephone);
        patient.setMutuelle(mutuelle);

        PatientDAO dao = new PatientDAO();
        dao.save(patient);

        // Redirect or forward to confirmation or patient list
        response.sendRedirect("SPECIALISTE.jsp?success=1");
    }
}
