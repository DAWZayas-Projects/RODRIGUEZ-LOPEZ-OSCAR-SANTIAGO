package com.example.oscar.proyectdawfoods.RegisterLogin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.oscar.proyectdawfoods.R;


public class InputCreateFood extends Fragment {


    public InputCreateFood() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input_create_food, container, false);
    }

}
