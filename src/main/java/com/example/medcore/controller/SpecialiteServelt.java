package com.example.medcore.controller;

import com.example.medcore.dao.UserDAO;
import com.example.medcore.model.MedecinSpecialiste;
import com.example.medcore.service.SpecialisteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet("/updateSpecialiteProfiile")
public class SpecialiteServelt extends HttpServlet{
    SpecialisteService specialisteService = new SpecialisteService();
    UserDAO userDAO = new UserDAO();
    @Override
    protected void doPost(HttpServletRequest request , HttpServletResponse response)
            throws IOException, ServletException {

        String path = request.getServletPath();
        switch (path){
            case"/updateSpecialiteProfiile":
               // response.getWriter().println("------------");
               int user_id=Integer.parseInt(request.getParameter("user_id"));
                String name =  request.getParameter("name");

                 MedecinSpecialiste.Specialite  specialite = MedecinSpecialiste.Specialite.valueOf(request.getParameter("specialite"));
                Double tarif = Double.parseDouble( request.getParameter("tarif"));
                int dureeConsultation =  Integer.parseInt(request.getParameter("dureeConsultation"));
                MedecinSpecialiste medecinSpecialiste=(MedecinSpecialiste) userDAO.findById(user_id);
                specialisteService.updateProfile(medecinSpecialiste,name,specialite,tarif,dureeConsultation);
                break;
            default:
                response.getWriter().println("errrrrrrrrrrrrorr");
                break;

        }

     }
}
