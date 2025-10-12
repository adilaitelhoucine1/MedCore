package com.example.medcore.service;

import com.example.medcore.dao.ActeDAO;
import com.example.medcore.dao.ConsultationDAO;
import com.example.medcore.model.ActeTechnique;
import com.example.medcore.model.Consultation;

public class GeneralistService {
    ConsultationDAO consultationDAO = new ConsultationDAO();
    ActeDAO acteDAO=new ActeDAO();
    public boolean addConsultation(Consultation consultation){

        return consultationDAO.save(consultation);
    }

    public  boolean saveActe(ActeTechnique acteTechnique){
       return acteDAO.save(acteTechnique);
    }
}