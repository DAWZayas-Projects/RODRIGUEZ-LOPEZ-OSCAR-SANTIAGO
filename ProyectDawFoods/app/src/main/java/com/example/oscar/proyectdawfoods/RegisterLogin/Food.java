package com.example.oscar.proyectdawfoods.RegisterLogin;


public class Food {

    private String nameFood;
    private String photo;
    private String description;
    private int favourite;
    private int people;

    public Food(){

    }

    public Food(String nameFood, String description,  String photo) {
        this.nameFood = nameFood;
        this.description = description;
        this.photo = photo;

    }

    public String getPhoto() {
        return photo;
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


}