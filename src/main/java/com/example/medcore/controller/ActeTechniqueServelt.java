package com.example.medcore.controller;

import com.example.medcore.dao.ConsultationDAO;
import com.example.medcore.model.ActeTechnique;
import com.example.medcore.model.Consultation;
import com.example.medcore.service.GeneralistService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/addactetechnique")
public class ActeTechniqueServelt extends HttpServlet {
    protected void doPost(HttpServletRequest request , HttpServletResponse response)
            throws IOException, ServletException{

        String path= request.getServletPath();
        switch (path){
            case"/addactetechnique":
                String type=request.getParameter("actetyppe");
                ActeTechnique.TypeActe typeActe = ActeTechnique.TypeActe.valueOf(type.toUpperCase());

                String result=request.getParameter("result");
                Long consultation_id= Long.parseLong( request.getParameter("consutation_id"));
                ConsultationDAO consultationDAO= new ConsultationDAO();
                Consultation consultation =consultationDAO.findByID(consultation_id);

                ActeTechnique acteTechnique=new ActeTechnique(consultation,result,typeActe);
                GeneralistService generalistService= new GeneralistService();
               boolean added= generalistService.saveActe(acteTechnique);
               if (added){
                   response.sendRedirect("specilaiste");
               }

                break;
            default:
                response.getWriter().println("errrrrrrrrrrrrorr");
                break;
        }

    }
}
