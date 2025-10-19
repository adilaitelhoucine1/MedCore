package com.example.medcore.controller;

import com.example.medcore.dao.ConsultationDAO;
import com.example.medcore.model.ActeTechnique;
import com.example.medcore.model.Consultation;
import com.example.medcore.model.MedecinSpecialiste;
import com.example.medcore.model.Utilisateur;
import com.example.medcore.service.GeneralistService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/addactetechnique")
public class ActeTechniqueServelt extends HttpServlet {
    protected void doPost(HttpServletRequest request , HttpServletResponse response)
            throws IOException, ServletException{

        String path= request.getServletPath();
        switch (path){
            case"/addactetechnique":
                HttpSession session = request.getSession(false);
                MedecinSpecialiste user = (MedecinSpecialiste) session.getAttribute("user");
                String type=request.getParameter("actetyppe");
                ActeTechnique.TypeActe typeActe = ActeTechnique.TypeActe.valueOf(type.toUpperCase());

                String result=request.getParameter("result");
                Long consultation_id= Long.parseLong( request.getParameter("consutation_id"));
                ConsultationDAO consultationDAO= new ConsultationDAO();
                Consultation consultation =consultationDAO.findByID(consultation_id);
                Double tarif=(Double)user.getTarif();
                ActeTechnique acteTechnique=new ActeTechnique(consultation,result,typeActe);
                GeneralistService generalistService= new GeneralistService();
                boolean updated=generalistService.updateCoutConsutation(consultation,tarif);
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
