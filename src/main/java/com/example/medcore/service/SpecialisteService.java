package com.example.medcore.service;

import com.example.medcore.dao.SpecialisteDAO;
import com.example.medcore.model.MedecinSpecialiste;

public class SpecialisteService {

    SpecialisteDAO specialisteDAO=new SpecialisteDAO();
    public  void updateProfile(MedecinSpecialiste medecinSpecialiste , String name,MedecinSpecialiste.Specialite specialite,Double tarif , int dureeConsultation){
        medecinSpecialiste.setNom(name);
        medecinSpecialiste.setSpecialite(specialite);
        medecinSpecialiste.setTarif(tarif);
        medecinSpecialiste.setDureeConsultation(dureeConsultation);
        specialisteDAO.updateProfile(medecinSpecialiste);

    }
}
