package com.example.medcore.service;

import com.example.medcore.dao.UserDAO;
import com.example.medcore.model.Utilisateur;

public class UserSercive {

        Utilisateur user;
        UserDAO userDAO;
        public UserSercive(UserDAO userDAO){
             this.userDAO=new UserDAO();
        }

        public void register(Utilisateur user){
            // validate email , password
            userDAO.save(user);


        }


}
