package com.example.oscar.proyectdawfoods.RegisterLogin;



public class RecipeDatabase {

    private String nameFood;
    private String photo;
    private String description;
    private int favourite;
    private int people;
    private String name;
    private String photoUrl;
    private String userId;
    private String provider;

    public RecipeDatabase (String nameFood, String photo, String description, int favourite, int people, String userId ){

        this.nameFood = nameFood;
        this.photo = photo;
        this.description = description;
        this.favourite = favourite;
        this.people = people;
        this.userId = userId;

    }

    public String getNameFood(){
        return this.nameFood;
    }

    public String getPhotoRecipe(){
        return this.photo;
    }

    public String getDescriptionRecipe(){
        return this.description;
    }

    public int getFavourite(){
        return this.favourite;
    }

    public int getPeopleRecipe(){
        return this.people;
    }

    public void setNameUser( String name ){
        this.name = name;
    }

    public void setNamePhotoUrl( String name ){
        this.photoUrl = name;
    }

    public void setNameprovider( String name ){
        this.provider = name;
    }

    public String getNameUser(){
       return  this.name;
    }

    public String getPhotoUrl(){
        return this.photoUrl;
    }

    public String getUserId() {return this.userId;}




}
