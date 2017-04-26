package com.example.oscar.proyectdawfoods.RegisterLogin;


import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DatabaseAccess {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foods-5078e.firebaseio.com");
    private ArrayList<Food> foods = new ArrayList<Food>();
    private ArrayList<DatabaseUser> users  = new ArrayList<DatabaseUser>();
    private DataSnapshot cachedDataSnapshot = null;

    public DatabaseAccess(){
        snapShotUsers();
        snapShotFoods();
    }

    public void getAllDatabaseFoods(DataSnapshot snapShot){
        Food food = snapShot.getValue(Food.class);
        foods.add(food);
    }


    public void getAllDatabaseUsers(DataSnapshot snapShot){
        DatabaseUser  user = snapShot.getValue(DatabaseUser.class);
        users.add(user);
    }


    public void snapShotUsers(){
        ChildEventListener Listener  = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getAllDatabaseUsers(dataSnapshot);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getAllDatabaseUsers(dataSnapshot);
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //taskDeletion(dataSnapshot);
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        databaseReference.child("users").addChildEventListener(Listener);
    }


    public void snapShotFoods(){
        ChildEventListener ChildFoods = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getAllDatabaseFoods(dataSnapshot);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getAllDatabaseFoods(dataSnapshot);
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //taskDeletion(dataSnapshot);
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        databaseReference.child("foods").addChildEventListener(ChildFoods);
    }

    public ArrayList<RecipeDatabase> getRecipes (){
        ArrayList<RecipeDatabase> Recipes = new ArrayList<RecipeDatabase>();

        /*foods.stream().map(food -> Recipes.add(new RecipeDatabase(food.getNameFood(), food.getPhoto(), food.getDescription(),
                food.getFavorite(), food.getPeople())));*/
        foods.stream().map( (e) -> Log.d("Name", e.getNameFood()));
        /*users.stream().map(user ->
            Recipes.stream().map(recipe->{
                recipe.setNamePhotoUrl(user.getPhotoUrl());
                recipe.setNameprovider(user.getProvider());
                recipe.setNameUser(user.getNameDatabaseUser());
                return recipe;
            })
        );*/

        return Recipes;
    }

}
