package com.example.medcore.service;

import com.example.medcore.dao.DossierDAO;
import com.example.medcore.model.DossierMedical;

public class InfermierService {
    DossierDAO dossierDAO;
    public InfermierService(DossierDAO dossierDAO){
        this.dossierDAO=new DossierDAO();
    }

    public  void addMedicalInfo(DossierMedical dossierMedical){
        dossierDAO.addMedicalInfo(dossierMedical);
    }
}
