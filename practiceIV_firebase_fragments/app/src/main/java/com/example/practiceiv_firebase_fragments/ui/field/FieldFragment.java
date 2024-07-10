package com.example.practiceiv_firebase_fragments.ui.field;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practiceiv_firebase_fragments.FirebaseHelper;
import com.example.practiceiv_firebase_fragments.MainActivity;
import com.example.practiceiv_firebase_fragments.Player.Player;
import com.example.practiceiv_firebase_fragments.Player.PlayerAdapter;
import com.example.practiceiv_firebase_fragments.R;
import com.example.practiceiv_firebase_fragments.databinding.FragmentFieldBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FieldFragment extends Fragment {

    private FragmentFieldBinding binding;

    private ImageButton home_goalie;

    private FirebaseFirestore player_firebase_db;

    private List<Player> list_of_players;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FieldViewModel fieldViewModel =
                new ViewModelProvider(this).get(FieldViewModel.class);

        binding = FragmentFieldBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Update the title in the app_bar
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Let's Play!");
            actionBar.setDisplayHomeAsUpEnabled(true); // Example: enable back button
        }

        home_goalie = root.findViewById(R.id.home_goalie);
        //more definitions here TODO

        // Get the Firebase database instance
        player_firebase_db = FirebaseHelper.getInstance().getDatabase();
        list_of_players = new ArrayList<Player>();

        //retrieve the players
        // Query players from Firestore
        FirebaseHelper.getInstance().getAllPlayers(
                queryDocumentSnapshots -> {
                    list_of_players.clear(); // Clear existing list
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Player player = document.toObject(Player.class);
                        System.out.println("ADDING A FIELD PLAYER! INSIDE");
                        list_of_players.add(player);
                        System.out.println("FIELD PLAYER ADDED: " + player.getPlayer_name());

                    }
                    System.out.println("FIELD PLAYERS");
                    for(int i = 0; i < list_of_players.size(); i++){
                        System.out.print("FIELD here: ");
                        System.out.println(list_of_players.get(i).getPlayer_name());
                    }

                    // Set player list to ViewModel
                    fieldViewModel.setPlayerList(list_of_players);

                    home_goalie.setImageResource(getPlayerByPosition("Goalie").getImageResourceID());
                },
                e -> Log.e("RosterFragment", "Error querying players", e)
        );


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public Player getPlayerByPosition(String position){
        for(int i = 0; i < list_of_players.size(); i++){
            if(list_of_players.get(i).getPlayer_position().equals(position)){
                return list_of_players.get(i);
            }
        }
        return null;

    }
}