package com.example.practiceiv_firebase_fragments.Player;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practiceiv_firebase_fragments.FirebaseHelper;
import com.example.practiceiv_firebase_fragments.R;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class PlayerAdapter_Backups extends RecyclerView.Adapter<PlayerAdapter_Backups.ViewHolder> {

    //Local variables to keep track of products in the list
    private List<Player> players;


    //default constructor
    public PlayerAdapter_Backups() {
        this.players = new ArrayList<>();
    }

    public PlayerAdapter_Backups(List<Player> players) {
        this.players = players;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.backups_list_item, parent, false);
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

            public void onClick(View view){
                //get the player information to recruit
                Player toRecruit = players.get(position1);
                String players_position = toRecruit.getPlayer_position();
                System.out.println(("YOU ARE RECRUITING: " + toRecruit.getPlayer_name()));
                System.out.println(("THEIR ID IS: " + toRecruit.getPlayer_id()));

                //Check the players for one with the same position.
                //if same position exists, Toast and do not recruit
                List<Player> list_of_players = new ArrayList<Player>();
                //retrieve the players
                // Query players from Firestore
                FirebaseHelper.getInstance().getAllBackups(
                        queryDocumentSnapshots -> {
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                Player player = document.toObject(Player.class);
                                list_of_players.add(player);
                            }
                        },
                        e -> Log.e("AdapterBackupsFragment", "Error querying players", e)
                );

                Boolean found = false;
                for(int i = 0; i < players.size(); i++){
                    if(players.get(i).getPlayer_position().equals(players_position)){
                        found = true;
                        Toast myToast = Toast.makeText(view.getContext(), "This position is taken. Drop the player before recruiting.", Toast.LENGTH_SHORT);
                        myToast.show();
                        return;
                    }
                }

                //successful recruitment.
                //update the Player Database
                FirebaseHelper.getInstance().addPlayer(toRecruit,
                        documentReference -> Log.d("AdapterBackupsFragment", "Player added successfully with ID: " + documentReference.getId()),
                        e -> Log.e("AdapterBackupsFragment", "Error added player", e)
                );

                //remove from the Backup Database
                FirebaseHelper.getInstance().dropBackup(toRecruit,
                        documentReference -> Log.d("AdapterBackupsFragment", "Player drop successfully with ID: " + documentReference.getId()),
                        e -> Log.e("AdapterBackupsFragment", "Error dropping backup", e)
                );

                //update the view
                list_of_players.remove(position1);
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