package com.example.practiceiv_firebase_fragments.Player;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practiceiv_firebase_fragments.R;

import java.util.ArrayList;
import java.util.List;


public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {

    //Local variables to keep track of products in the list
    private List<Player> players;

    //determines whether the items can be selected
    private Boolean isSelectable = true;

    //default constructor
    public PlayerAdapter() {
        this.players = new ArrayList<>();
    }

    public PlayerAdapter(List<Player> players) {
        this.players = players;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int position1 = position;  //got an error saying that "position" could change and that I should use a local variable instead
        //create a local copy of the product at position1
        Player player = players.get(position1);

        //Set the holder's (item's) xml elements to the product-unique details
        //the ViewHolder class is defined below.
        holder.viewholder_name_editable.setText(player.getPlayer_name());
        holder.viewholder_position_editable.setText(player.getPlayer_position());
        holder.viewholder_age_editable.setText(String.valueOf(player.getPlayer_age()));
        holder.viewholder_years_played_editable.setText(String.valueOf(player.getYears_played()));
        holder.productImage.setImageResource(player.getImageResourceID());

        //onclick behavior for each item.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Player item = players.get(position1);
                //Move to a detail view?
            }
        });

        //onclick behavior for each recruit button
        //TODO
        holder.recruit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //update the Player to be "recruited" or not based on the text on the button
                //update the button text
                //update the Database and RecyclerView
            }
        });
    }


    //Getters and Setters

    @Override
    public int getItemCount() {
        //System.out.println("THE NUMBER OF PRODUCTS IS " + String.valueOf(this.products.size()));
        return this.players.size();
    }

    public void setSelectable(Boolean selectable){
        isSelectable = selectable;
    }

    public void clearPlayers(){
        players.clear();
        notifyDataSetChanged();
    }

    public void updateRosterList(List<Player> rosterList) {
        this.players = rosterList;
        notifyDataSetChanged();
    }

    //Custom ViewHolder class which allows the xml elements in a recyclerView item to be bound together into one object
    //made for ease of reference
    public class ViewHolder extends RecyclerView.ViewHolder {
        //the local variables for each of the xml elements in an item
        public TextView viewholder_name_editable;
        public TextView viewholder_age_editable;
        public TextView viewholder_years_played_editable;
        public TextView viewholder_position_editable;
        public ImageView productImage;
        public Button recruit_button;

        public ViewHolder(View itemView) {
            super(itemView);
            //assign actual views to the local variables
            viewholder_name_editable = itemView.findViewById(R.id.item_name_editable);
            viewholder_age_editable = itemView.findViewById(R.id.item_age_editable);
            viewholder_years_played_editable = itemView.findViewById(R.id.item_years_played_editable);
            viewholder_position_editable = itemView.findViewById(R.id.item_position_editable);
            productImage = itemView.findViewById(R.id.imageView);
            recruit_button = itemView.findViewById(R.id.recruit_button);
        }
    }
}