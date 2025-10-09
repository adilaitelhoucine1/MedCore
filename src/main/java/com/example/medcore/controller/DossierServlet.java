package com.example.medcore.controller;

import com.example.medcore.dao.DossierDAO;
import com.example.medcore.dao.PatientDAO;
import com.example.medcore.model.DossierMedical;
import com.example.medcore.model.Patient;
import com.example.medcore.service.InfermierService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
@WebServlet("/addMedicalInfo")
public class DossierServlet  extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        switch (path) {
            case "/addMedicalInfo":
                int patientId = Integer.parseInt(request.getParameter("patientId"));
                String allergies = request.getParameter("allergies");
                String antecedents = request.getParameter("antecedents");
                String traitements = request.getParameter("traitements");
//                response.getWriter().println(allergies);
//                response.getWriter().println(antecedents);
//                response.getWriter().println(traitements);
//                response.getWriter().println(patientId);

                PatientDAO patientDAO = new PatientDAO();
                DossierDAO dossierDAO = new DossierDAO();
                Patient patient = patientDAO.findById(patientId);

                if (patient != null) {

                    DossierMedical dossierMedical = new DossierMedical(patient, antecedents, allergies, traitements);

                    InfermierService infermierService = new InfermierService(dossierDAO);
                    infermierService.addMedicalInfo(dossierMedical);
                } else {
                    response.getWriter().println("Patient introuvable !");
                }




                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }
}
