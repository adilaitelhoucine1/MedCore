package com.example.medcore.controller;

import com.example.medcore.dao.DossierDAO;
import com.example.medcore.dao.PatientDAO;
import com.example.medcore.model.Consultation;
import com.example.medcore.model.MedecinGeneraliste;
import com.example.medcore.model.Patient;
import com.example.medcore.model.Utilisateur;
import com.example.medcore.service.GeneralistService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/addconsultation")
public class ConsultationServelt extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String servletPath = req.getServletPath();

        switch (servletPath) {
            case "/addconsultation":
                int patientId=Integer.parseInt(req.getParameter("patient_id"));
                PatientDAO patientDAO = new PatientDAO();
                DossierDAO dossierDAO = new DossierDAO();
                Patient patient = patientDAO.findById(patientId);
                HttpSession session = req.getSession(false);
                MedecinGeneraliste generaliste =(MedecinGeneraliste) session.getAttribute("user");
                String motif = req.getParameter("motif");
                String observations = req.getParameter("observations");
                String diagnostic = req.getParameter("diagnostic");
                String traitement = req.getParameter("traitement");
                Double cout = Double.parseDouble(req.getParameter("cout"));

                Consultation consultation= new Consultation(patient,generaliste,motif,observations,diagnostic,traitement,cout);
                GeneralistService generalistService = new GeneralistService();
                generalistService.addConsultation(consultation);


                break;

            default:
                resp.getWriter().println("consultation introuvable !");
                break;
        }
    }
}
