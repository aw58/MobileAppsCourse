package com.example.practiceiv_firebase_fragments.Player;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practiceiv_firebase_fragments.FirebaseHelper;
import com.example.practiceiv_firebase_fragments.R;

import java.util.ArrayList;
import java.util.List;


public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {

    //Local variables to keep track of players in the list
    private List<com.example.practiceiv_firebase_fragments.Player.Player> players;


    private AdapterView.OnItemClickListener listener;


    public interface OnItemClickListener {
        void onDropItemClick(int position);
    }

    //default constructor
    public PlayerAdapter(OnItemClickListener listener) {
        this.players = new ArrayList<>();
    }

    public PlayerAdapter(List<com.example.practiceiv_firebase_fragments.Player.Player> players) {
        this.players = players;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_list_item_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int position1 = position;  //got an error saying that "position" could change and that I should use a local variable instead
        //create a local copy of the product at position1
        com.example.practiceiv_firebase_fragments.Player.Player player = players.get(position1);

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
                com.example.practiceiv_firebase_fragments.Player.Player item = players.get(position1);
                //Move to a detail view?
            }
        });

        //onclick behavior for each recruit button
        holder.drop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //get the player information to drop
                com.example.practiceiv_firebase_fragments.Player.Player toDrop = players.get(position1);
                System.out.println(("YOU ARE DROPPING: " + toDrop.getPlayer_name()));
                System.out.println(("THEIR ID IS: " + toDrop.getPlayer_id()));

                //update the Player Database
                FirebaseHelper.getInstance().dropPlayer(toDrop,
                        documentReference -> Log.d("PlayerAdapter", "Player dropped successfully with ID: " + documentReference.getId()),
                        e -> Log.e("PlayerAdapter", "Error dropping player", e)
                );

                //Add to the Backup Database
                FirebaseHelper.getInstance().addBackup(toDrop,
                        documentReference -> Log.d("PlayerAdapter", "Player added to backups successfully with ID: " + documentReference.getId()),
                        e -> Log.e("PlayerAdapter", "Error adding backup", e)
                );

                //update the view
                players.remove(position1);
                notifyDataSetChanged();
            }
        });
    }


    //Getters and Setters

    @Override
    public int getItemCount() {
        //System.out.println("THE NUMBER OF PRODUCTS IS " + String.valueOf(this.products.size()));
        return this.players.size();
    }

    public void clearPlayers(){
        players.clear();
        notifyDataSetChanged();
    }

    public void updateRosterList(List<com.example.practiceiv_firebase_fragments.Player.Player> rosterList) {
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
        public Button drop_button;

        public ViewHolder(View itemView) {
            super(itemView);
            //assign actual views to the local variables
            viewholder_name_editable = itemView.findViewById(R.id.item_name_editable);
            viewholder_age_editable = itemView.findViewById(R.id.item_age_editable);
            viewholder_years_played_editable = itemView.findViewById(R.id.item_years_played_editable);
            viewholder_position_editable = itemView.findViewById(R.id.item_position_editable);
            productImage = itemView.findViewById(R.id.imageView);
            drop_button = itemView.findViewById(R.id.drop_button);
        }
    }
}