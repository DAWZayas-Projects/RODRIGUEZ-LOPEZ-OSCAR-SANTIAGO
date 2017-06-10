package com.example.oscar.proyectdawfoods.RegisterLogin;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.oscar.proyectdawfoods.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Recipes extends Fragment implements  View.OnClickListener{

    private LinearLayoutManager lLayout;
    private List<RecipeDatabase> allFoods;
    private ReciclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foods-5078e.firebaseio.com");
    private ArrayList<Food> foods = new ArrayList<Food>();
    private ArrayList<DatabaseUser> users = new ArrayList<DatabaseUser>();
    private Food food;
    private DatabaseUser user;
    private ChildEventListener foodsListener;
    private ChildEventListener userListener;
    private int count = 0;


    public Recipes() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipes, container, false);

        lLayout = new LinearLayoutManager(getActivity());
        recyclerView= (RecyclerView)rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(lLayout);
        allFoods = new ArrayList<RecipeDatabase>();
        FloatingActionButton myFab = (FloatingActionButton)  rootView.findViewById(R.id.myFAB);
        myFab.setOnClickListener(this);

        getUsers();

        return rootView;
    }

    public void getUsers(){
        Log.d("yyyyyyyyyyyusero", Integer.toString(users.size()));
        userListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getAllDatabaseUsers(dataSnapshot);
                if(users.size() >= dataSnapshot.getChildrenCount()){
                    syncronizeDB();
                }

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getAllDatabaseUsers(dataSnapshot);
                if(users.size() >= dataSnapshot.getChildrenCount()){
                    syncronizeDB();
                }

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

        databaseReference.child("users").addChildEventListener(userListener);
        Log.d("yyyyyyyyyyyusero", Integer.toString(users.size()));
    }

    public void syncronizeDB()  {
        Log.d("yyyyyyyyyyyfoo", Integer.toString(foods.size()));
        foodsListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getAllDatabaseFoods(dataSnapshot);
                if(foods.size() >= dataSnapshot.getChildrenCount()){
                    makeRecipes();
                }
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                getAllDatabaseFoods(dataSnapshot);
                if(foods.size() >= dataSnapshot.getChildrenCount()){
                    makeRecipes();
                }

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

        databaseReference.child("foods").addChildEventListener(foodsListener);
        Log.d("yyyyyyyyyyyfoo", Integer.toString(foods.size()));


    }

    public void getAllDatabaseFoods(DataSnapshot snapShot){

        for(DataSnapshot value : snapShot.getChildren()){
            count ++;

            food = value.getValue(Food.class);
            food.setPeople(R.drawable.ic_people_black_24dp);
            food.setFavourite(R.drawable.ic_favorite_black_24dp);

            for(DataSnapshot child : value.getChildren()){
                if(child.getKey().equals("user") ){
                    food.setUserId(child.getValue(String.class));
                }

                if(child.getKey().equals("profilePhoto")){
                    food.setPhoto(child.getValue(String.class));
                }
            }
            foods.add(food);

        }

    }

    public void getAllDatabaseUsers(DataSnapshot snapShot) {
        user = snapShot.getValue(DatabaseUser.class);
        for(DataSnapshot f :  snapShot.getChildren())
            if("name".equals(f.getKey()))
                user.setNameDatabaseUser(f.getValue(String.class));
        user.setId(snapShot.getKey());
        users.add(user);

    }

    public void makeRecipes(){
        Log.d("yyyyyyyyyyrec", Integer.toString(allFoods.size()));
        allFoods = new ArrayList<>();
        for(Food fo : foods){
            RecipeDatabase recipe = new RecipeDatabase(fo.getNameFood(), fo.getPhoto(), fo.getDescription(), fo.getFavorite(),
            fo.getPeople(), fo.getUserId());
            allFoods.add(recipe);
        }
        foods = new ArrayList<>();
        for(DatabaseUser user : users){
            for(RecipeDatabase f : allFoods){
                if(user.getId().equals(f.getUserId())){
                    f.setNamePhotoUrl(user.getPhotoUrl());
                    f.setNameUser(user.getNameDatabaseUser());
                    f.setNameprovider(user.getProvider());
                }
            }

        }

        Log.d("yyyyyyyyyyrec", Integer.toString(allFoods.size()));
        recyclerViewAdapter = new ReciclerViewAdapter(getActivity(), allFoods);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    public void onClick(View v){
        Intent intent;
        intent = new Intent(getActivity(), ScrollingCreateFood.class);
        intent.putExtra("idLastFood",  count);
        //intent.putParcelableArrayListExtra("lista", (ArrayList<? extends Parcelable>) allFoods);
        startActivity(intent);
    }

    public void changeRecipesUser(DataSnapshot snapShot){
        user = snapShot.getValue(DatabaseUser.class);
        for(DataSnapshot f :  snapShot.getChildren())
            if("name".equals(f.getKey()))
                user.setNameDatabaseUser(f.getValue(String.class));
        user.setId(snapShot.getKey());


        for(RecipeDatabase rec : allFoods){

            if(rec.getUserId().equals(user.getId())){
                rec.setNamePhotoUrl(user.getPhotoUrl());
                rec.setNameUser(user.getNameDatabaseUser());
            }
        }
        recyclerViewAdapter = new ReciclerViewAdapter(getActivity(), allFoods);
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    @Override
    public void onStop() {
        super.onStop();
        if (foodsListener != null && userListener != null) {
            databaseReference.removeEventListener(foodsListener);
            databaseReference.removeEventListener(userListener);
        }
    }



}
