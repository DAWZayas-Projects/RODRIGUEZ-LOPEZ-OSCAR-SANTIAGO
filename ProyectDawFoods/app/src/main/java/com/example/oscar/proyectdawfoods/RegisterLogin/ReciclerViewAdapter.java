package com.example.oscar.proyectdawfoods.RegisterLogin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.oscar.proyectdawfoods.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReciclerViewAdapter extends RecyclerView.Adapter<ReciclerViewHolders> {

    private List<RecipeDatabase> itemList;
    private Context context;

    public ReciclerViewAdapter(Context context, List<RecipeDatabase> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public ReciclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_card_food, null);
        ReciclerViewHolders rcv = new ReciclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(ReciclerViewHolders holder, int position) {
        holder.countryName.setText(itemList.get(position).getNameFood());
        Picasso.with(context).load(itemList.get(position).getPhotoRecipe()).into(holder.countryPhoto);
        holder.user.setText(itemList.get(position).getNameUser());
        holder.description.setText(itemList.get(position).getDescriptionRecipe());
        Picasso.with(context).load(itemList.get(position).getPhotoUrl()).into(holder.profileImg);
        holder.favourite.setImageResource(itemList.get(position).getFavourite());
        holder.people.setImageResource(itemList.get(position).getPeopleRecipe());

    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}