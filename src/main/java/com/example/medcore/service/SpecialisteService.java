package com.example.medcore.service;

import com.example.medcore.dao.CreneauDAO;
import com.example.medcore.dao.DemandeExpertiseDAO;
import com.example.medcore.dao.SpecialisteDAO;
import com.example.medcore.model.Creneau;
import com.example.medcore.model.DemandeExpertise;
import com.example.medcore.model.MedecinSpecialiste;
import com.mysql.cj.log.Log;

import java.util.List;
import java.util.Objects;

public class SpecialisteService {

    SpecialisteDAO specialisteDAO=new SpecialisteDAO();
    CreneauDAO creneauDAO = new CreneauDAO();
    DemandeExpertiseDAO demandeExpertiseDAO=new DemandeExpertiseDAO();
    public  void updateProfile(MedecinSpecialiste medecinSpecialiste , String name,MedecinSpecialiste.Specialite specialite,Double tarif , int dureeConsultation){
        medecinSpecialiste.setNom(name);
        medecinSpecialiste.setSpecialite(specialite);
        medecinSpecialiste.setTarif(tarif);
        medecinSpecialiste.setDureeConsultation(dureeConsultation);
        specialisteDAO.updateProfile(medecinSpecialiste);

    }

    public List<Creneau> getCreneaux(Long specialist_id){
        return creneauDAO.getCreneaux(specialist_id);
    }
    public List<Object[]> getAllCreneau(Long specialist_id){
        return creneauDAO.getAllCreneau(specialist_id);
    }
    public List<Object[]> getAllDemande(Long specialist_id){
        return demandeExpertiseDAO.getAllDemande(specialist_id);
    }


    public  boolean updateDemande(Long id,String avis_medecin,String  recommandations){
        DemandeExpertise demandeExpertise=demandeExpertiseDAO.findById(id);
        System.out.println(demandeExpertise);
        demandeExpertise.setAvisMedecin(avis_medecin);
        demandeExpertise.setRecommandations(recommandations);
        demandeExpertise.setStatus(DemandeExpertise.Status.TERMINEE);
        return  demandeExpertiseDAO.updateDemande(demandeExpertise);
    }


}
