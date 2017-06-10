package com.example.oscar.proyectdawfoods.RegisterLogin;


public class Food {

    private String nameFood;
    private String profilePhoto;
    private String description;
    private String user;
    private int favourite;
    private int people;
    private String idFood;

    public Food(){

    }

    public Food(String nameFood, String description,  String profilePhoto, String user) {
        this.nameFood = nameFood;
        this.description = description;
        this.profilePhoto = profilePhoto;
        this.user = user;

    }

    public String getPhoto() {
        return profilePhoto;
    }

    public String getDescription(){
        return this.description;
    }

    public int getFavorite(){
        return this.favourite;
    }

    public int getPeople(){
        return this.people;
    }

    public void setFavourite(int e){ this.favourite = e; }

    public void setPeople(int e){ this.people = e; }

    public String getNameFood () {return this.nameFood;}

    public String getUserId(){
        return this.user;
    }

    public void setPhoto( String profile){
        this.profilePhoto = profile;
    }

    public void setUserId(String user){
        this.user = user;
    }


}