package com.example.oscar.proyectdawfoods.RegisterLogin;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oscar.proyectdawfoods.R;

public class ReciclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView countryName, user, description;
    public ImageView countryPhoto,  profileImg, favourite, people;

    public ReciclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        countryName = (TextView)itemView.findViewById(R.id.country_name);
        user = (TextView) itemView.findViewById(R.id.user);
        description = (TextView) itemView.findViewById(R.id.description);
        countryPhoto = (ImageView)itemView.findViewById(R.id.country_photo);
        profileImg = (ImageView)itemView.findViewById(R.id.profile_image);
        favourite = (ImageView)itemView.findViewById(R.id.favourite);
        people = (ImageView)itemView.findViewById(R.id.people);

    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
}
