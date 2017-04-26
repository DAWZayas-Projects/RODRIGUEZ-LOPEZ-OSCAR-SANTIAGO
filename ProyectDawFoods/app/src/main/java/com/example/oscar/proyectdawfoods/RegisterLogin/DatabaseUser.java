package com.example.oscar.proyectdawfoods.RegisterLogin;

/**
 * Created by oscar on 22/04/17.
 */

public class DatabaseUser {

    private String name;
    private String photoUrl;
    private String provider;

    public void DatabaseUser(String name, String photoUrl, String provider){
        this.name  = name ;
        this.photoUrl = photoUrl;
        this.provider = provider;
    }

    public void setNameDatabaseUser(String name ){
        this.name = name;
    }

    public void setPhotoUrl(String photoUrl){
        this.photoUrl = photoUrl;
    }

    public void setProvider(String provider){
        this.provider = provider;
    }

    public String getNameDatabaseUser(){
        return this.name;
    }

    public String getPhotoUrl (){
        return this.photoUrl;
    }

    public String getProvider (){
        return this.provider;
    }
}
