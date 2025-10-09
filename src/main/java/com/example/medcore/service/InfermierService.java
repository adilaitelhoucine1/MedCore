package com.example.medcore.service;

import com.example.medcore.dao.DossierDAO;
import com.example.medcore.dao.SignesDAO;
import com.example.medcore.model.DossierMedical;
import com.example.medcore.model.SignesVitaux;

public class InfermierService {
    DossierDAO dossierDAO=new DossierDAO();
    SignesDAO signesDAO= new SignesDAO();

    public  boolean addMedicalInfo(DossierMedical dossierMedical){
       return dossierDAO.addMedicalInfo(dossierMedical);
    }

    public  boolean  addSigne(SignesVitaux signesVitaux){
        return  signesDAO.addSigne(signesVitaux);
    }
}
