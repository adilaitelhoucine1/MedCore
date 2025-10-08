package com.example.medcore.service;

import com.example.medcore.dao.UserDAO;
import com.example.medcore.model.Utilisateur;

public class UserSercive {

        Utilisateur user;
        UserDAO userDAO;
        public UserSercive(UserDAO userDAO){
             this.userDAO=new UserDAO();
        }

        public boolean register(Utilisateur user){
            // validate email , password
            return  userDAO.save(user);


        }

        public  boolean login(String email,String password){
        return  true;
        }


}
