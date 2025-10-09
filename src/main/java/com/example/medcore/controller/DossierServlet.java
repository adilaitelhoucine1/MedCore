package com.example.medcore.controller;

import com.example.medcore.dao.DossierDAO;
import com.example.medcore.dao.PatientDAO;
import com.example.medcore.model.DossierMedical;
import com.example.medcore.model.Patient;
import com.example.medcore.model.SignesVitaux;
import com.example.medcore.service.InfermierService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
@WebServlet({"/addMedicalInfo","/addVitals"})
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
                PatientDAO patientDAO = new PatientDAO();
                DossierDAO dossierDAO = new DossierDAO();
                Patient patient = patientDAO.findById(patientId);

                if (patient != null) {

                    DossierMedical dossierMedical = new DossierMedical(patient, antecedents, allergies, traitements);

                    InfermierService infermierService = new InfermierService();
                  boolean added=  infermierService.addMedicalInfo(dossierMedical);
                  if(added){
                      response.sendRedirect("listPatients");
                  }else{
                      String errorMessage = "Failed to add medical info. Please try again.";
                      response.sendRedirect("listPatients?error=" + java.net.URLEncoder.encode(errorMessage, "UTF-8"));

                  }
                } else {
                    response.getWriter().println("Patient introuvable !");
                }




                break;
            case "/addVitals":
                String tension =  request.getParameter("tension");
                int frequenceCardiaque = Integer.parseInt(request.getParameter("frequenceCardiaque"));
                Double temperature = Double.parseDouble(request.getParameter("temperature"));
                int frequenceResp = Integer.parseInt(request.getParameter("frequenceResp"));
                Double poids = Double.parseDouble(request.getParameter("poids"));
                Double taille = Double.parseDouble(request.getParameter("taille"));
                int patient_Id = Integer.parseInt(request.getParameter("patientId"));
                PatientDAO patientDAO1 = new PatientDAO();
                Patient pt = patientDAO1.findById(patient_Id);

                SignesVitaux signesVitaux = new SignesVitaux(pt,tension,frequenceCardiaque,temperature,frequenceResp,poids,taille);
                InfermierService infermierService=new InfermierService();
               boolean added= infermierService.addSigne(signesVitaux);
                if(added){
                    response.sendRedirect("listPatients");
                }else{
                    String errorMessage = "Failed to add medical info. Please try again.";
                    response.sendRedirect("listPatients?error=" + java.net.URLEncoder.encode(errorMessage, "UTF-8"));

                }
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }
}
