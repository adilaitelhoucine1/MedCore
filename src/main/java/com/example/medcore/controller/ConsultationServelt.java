package com.example.medcore.controller;

import com.example.medcore.dao.ConsultationDAO;
import com.example.medcore.dao.DossierDAO;
import com.example.medcore.dao.PatientDAO;
import com.example.medcore.model.Consultation;
import com.example.medcore.model.MedecinGeneraliste;
import com.example.medcore.model.Patient;
import com.example.medcore.service.GeneralistService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet({"/addconsultation", "/changestatusconsultation"})
public class ConsultationServelt extends HttpServlet {
    private GeneralistService generalistService = new GeneralistService();
    private ConsultationDAO consultationDAO = new ConsultationDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PatientDAO patientDAO = new PatientDAO();
        String servletPath = req.getServletPath();

        switch (servletPath) {
            case "/addconsultation":
                int patientId=Integer.parseInt(req.getParameter("patient_id"));

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

                boolean added= generalistService.addConsultation(consultation);
                if(added){
                    resp.sendRedirect("generaliste");
                }

                break;
            case "/changestatusconsultation":
                long consultationId = Long.parseLong(req.getParameter("consultationId"));
                String statusParam = req.getParameter("status");
                int patient_id= Integer.parseInt(req.getParameter("patient_id"));


                Patient patient1 =patientDAO.findById(patient_id);
                patientDAO.updateStatus(patient1);
                Consultation newconsultation = consultationDAO.findByID(consultationId);

                if (newconsultation != null && statusParam != null) {
                    try {
                        Consultation.Status newStatus = Consultation.Status.valueOf(statusParam);
                        newconsultation.setStatus(newStatus);

                        boolean updated = generalistService.updateStatus(newconsultation);
                        if (updated) {
                            resp.sendRedirect("generaliste");
                        }
                    } catch (IllegalArgumentException e) {
                        resp.getWriter().println("Invalid status value: " + statusParam);
                    }
                } else {
                    resp.getWriter().println("Missing consultation or status parameter");
                }
                break;


            default:
                resp.getWriter().println("consultation introuvable !");
                break;
        }
    }
}
