package com.example.oscar.proyectdawfoods.RegisterLogin;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SaveUsers {

    private String name,id, email, provider;
    private String photo;
    private DatabaseReference mDatabase;


    public SaveUsers(String id, String email, String name, String provider){
        this.email = email;
        this.name = name;
        this.id= id;
        this.provider = provider;
        this.mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    public void saveUser() {
        if(email != null) name = email;

        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("email", email);
        result.put("photoUrl", photo);
        result.put("provider", provider);
        mDatabase.child("users").child(id).setValue(result);

    }


    public void setPhoto(String photo){
        this.photo = photo;
    }



}
