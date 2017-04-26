package com.example.oscar.proyectdawfoods.RegisterLogin;


public class UserDatabase {

    private String name, photoUrl;

    public UserDatabase (String name, String photoUrl){
        this.name = name;
        this.photoUrl = photoUrl;
    }

    public String getNameUser(){
        return this.name;
    }

    public String getPhotoUrl(){
        return this.photoUrl;
    }

}
