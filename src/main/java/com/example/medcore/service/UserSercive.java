package com.example.medcore.service;

import com.example.medcore.dao.UserDAO;
import com.example.medcore.model.Utilisateur;
import org.mindrot.jbcrypt.BCrypt;

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

        public  Utilisateur login(String email,String password){
            // validate exsiting email
            Utilisateur user = userDAO.findByEmail(email);
            if (user != null) {
                if (BCrypt.checkpw(password, user.getMotDePasse())) {
                    return user;
                }else{
                    return  null;
                }
            }
            return user;

        }


}
