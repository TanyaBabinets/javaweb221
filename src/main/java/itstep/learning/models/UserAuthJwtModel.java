/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itstep.learning.models;

import itstep.learning.dal.dto.User;


public class UserAuthJwtModel {
     private User user;
     private String jwtToken;

    public UserAuthJwtModel(User user, String jwtToken) {
        this.user = user;
        this.jwtToken = jwtToken;
       
    }
     



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

   
    
}
