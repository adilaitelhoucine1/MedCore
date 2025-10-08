package com.example.medcore.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("INFIRMIER")
public class Infirmier extends Utilisateur {


   protected Infirmier() {}
    public Infirmier(String nom, String prenom, String email, String motDePasse) {
        super(nom,prenom,email,motDePasse);
    }
}