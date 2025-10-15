package com.example.medcore.service;

import com.example.medcore.dao.CreneauDAO;
import com.example.medcore.dao.SpecialisteDAO;
import com.example.medcore.model.Creneau;
import com.example.medcore.model.MedecinSpecialiste;

import java.util.List;

public class SpecialisteService {

    SpecialisteDAO specialisteDAO=new SpecialisteDAO();
    CreneauDAO creneauDAO = new CreneauDAO();
    public  void updateProfile(MedecinSpecialiste medecinSpecialiste , String name,MedecinSpecialiste.Specialite specialite,Double tarif , int dureeConsultation){
        medecinSpecialiste.setNom(name);
        medecinSpecialiste.setSpecialite(specialite);
        medecinSpecialiste.setTarif(tarif);
        medecinSpecialiste.setDureeConsultation(dureeConsultation);
        specialisteDAO.updateProfile(medecinSpecialiste);

    }

    public List<Creneau> getCreneaux(){
        return creneauDAO.getCreneaux();
    }
}
