package com.example.oscar.proyectdawfoods.RegisterLogin;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.oscar.proyectdawfoods.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Recipes extends Fragment implements  View.OnClickListener{

    private LinearLayoutManager lLayout;
    private DatabaseReference databaseReference;
    private List<RecipeDatabase> allFoods;
    private ReciclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef;
    private Food food;


    public Recipes() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipes, container, false);

        lLayout = new LinearLayoutManager(getActivity());
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foods-5078e.firebaseio.com");
        recyclerView= (RecyclerView)rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(lLayout);
        allFoods = new ArrayList<RecipeDatabase>();
        storageRef = storage.getReferenceFromUrl("gs://foods-5078e.appspot.com");
        FloatingActionButton myFab = (FloatingActionButton)  rootView.findViewById(R.id.myFAB);

        myFab.setOnClickListener(this);

        getRecipeDatabase();
        /*databaseReference.child("foods").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getAllFoods(dataSnapshot);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getAllFoods(dataSnapshot);
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
        });

*/
        return rootView;
    }

/*
    private void getAllFoods(DataSnapshot dataSnapshot){
        allFoods = new ArrayList<Food>();
        for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                food= singleSnapshot.getValue(Food.class);
                food.setFavourite(R.drawable.ic_favorite_black_24dp);
                food.setPeople(R.drawable.ic_people_black_24dp);



                allFoods.add();
                recyclerViewAdapter = new ReciclerViewAdapter(getActivity(), allFoods);
                recyclerView.setAdapter(recyclerViewAdapter);
        }
    }*/

    private void getRecipeDatabase(){
        DatabaseAccess access = new DatabaseAccess();
        ArrayList<RecipeDatabase> Recipes = access.getRecipes();
        /*allFoods = new ArrayList<RecipeDatabase>();
        
        Recipes.stream().map(recipe -> {
            allFoods.add(recipe);
            recyclerViewAdapter = new ReciclerViewAdapter(getActivity(), allFoods);
            recyclerView.setAdapter(recyclerViewAdapter);
            return "";
        });*/

    }

    public void onClick(View v){
        Intent intent = new Intent(getActivity(), CreateFood.class);
        startActivity(intent);
    }



}
