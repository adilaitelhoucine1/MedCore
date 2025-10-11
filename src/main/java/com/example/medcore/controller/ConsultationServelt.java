package com.example.medcore.controller;

import com.example.medcore.dao.ConsultationDAO;
import com.example.medcore.dao.DossierDAO;
import com.example.medcore.dao.PatientDAO;
import com.example.medcore.model.Consultation;
import com.example.medcore.model.MedecinGeneraliste;
import com.example.medcore.model.Patient;
import com.example.medcore.model.Utilisateur;
import com.example.medcore.service.GeneralistService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet({"/addconsultation"})
public class ConsultationServelt extends HttpServlet {

    private ConsultationDAO consultationDAO = new ConsultationDAO();


//    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException {
//
//        int patientId = Integer.parseInt(req.getParameter("patientId"));
//        List<Consultation> consultations = consultationDAO.findByPatientId(patientId);
//
//        req.setAttribute("consultations", consultations);
//        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/consultations_fragment.jsp");
//        dispatcher.forward(req, resp);
//    }



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
                boolean added= generalistService.addConsultation(consultation);
                if(added){
                    resp.sendRedirect("generaliste");
                }

                break;

            default:
                resp.getWriter().println("consultation introuvable !");
                break;
        }
    }
}
