package com.example.medcore.service;

import com.example.medcore.dao.*;
import com.example.medcore.model.*;

import java.util.List;

public class GeneralistService {
    ConsultationDAO consultationDAO = new ConsultationDAO();
    ActeDAO acteDAO=new ActeDAO();
    SpecialisteDAO specialisteDAO=new SpecialisteDAO();
    DemandeExpertiseDAO demandeExpertiseDAO=new DemandeExpertiseDAO();
    CreneauDAO creneauDAO = new CreneauDAO();
    public boolean addConsultation(Consultation consultation){

        return consultationDAO.save(consultation);
    }

    public  boolean saveActe(ActeTechnique acteTechnique){
       return acteDAO.save(acteTechnique);
    }
    public  boolean updateStatus(Consultation consultation){

       return consultationDAO.updateStatus(consultation);
    }
    public List<Consultation> getAvailableConsultations(long patientId) {
        return consultationDAO.getAvailableConsultations(patientId);
    }


    public List<MedecinSpecialiste> getAllSpecialiste(){


        return specialisteDAO.getAllSpecialiste();
    }


    public  List<Object[]> getCreneau(Long specialistId){
        return specialisteDAO.getAllCreneau(specialistId);
    }

    public boolean saveDemande(DemandeExpertise demandeExpertise){return demandeExpertiseDAO.save(demandeExpertise);}

    public boolean updateStatusCreaneau(Creneau creneau ,MedecinSpecialiste medecinSpecialiste,Consultation consultation){
        creneau.setStatus(Creneau.Status.RESERVE);
      return   creneauDAO.updateStatus(creneau,medecinSpecialiste,consultation);
    }
}