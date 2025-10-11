package com.example.medcore.controller;

import com.example.medcore.dao.PatientDAO;
import com.example.medcore.model.Patient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/generaliste")
public class GeneralisteServelt extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        switch (path){

            case "/generaliste":
                PatientDAO patientDAO = new PatientDAO();
                List<Patient> patients = patientDAO.findPatientswithInfo();

                request.setAttribute("patients", patients);

                request.getRequestDispatcher("/generaliste/GENERALISTE.jsp").forward(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }


    }
}
