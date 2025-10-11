package com.example.medcore.service;

import com.example.medcore.dao.ConsultationDAO;
import com.example.medcore.model.Consultation;

public class GeneralistService {
    ConsultationDAO consultationDAO = new ConsultationDAO();
    public void addConsultation(Consultation consultation){

        consultationDAO.save(consultation);
    }

}
