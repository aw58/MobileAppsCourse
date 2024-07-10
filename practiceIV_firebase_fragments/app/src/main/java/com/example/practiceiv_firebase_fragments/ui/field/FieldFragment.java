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
    private ImageButton home_left_def;
    private ImageButton home_left_mid;
    private ImageButton home_left_front;
    private ImageButton home_mid_def;
    private ImageButton home_mid_mid;
    private ImageButton home_mid_front;
    private ImageButton home_right_def;
    private ImageButton home_right_mid;
    private ImageButton home_right_front;
    private ImageButton opp_goalie;
    private ImageButton opp_left_def;
    private ImageButton opp_left_mid;
    private ImageButton opp_left_front;
    private ImageButton opp_mid_def;
    private ImageButton opp_mid_mid;
    private ImageButton opp_mid_front;
    private ImageButton opp_right_def;
    private ImageButton opp_right_mid;
    private ImageButton opp_right_front;

    private FirebaseFirestore player_firebase_db;

    private List<Player> list_of_players;
    private List<Player> list_of_opponents;

    private List<String> list_of_positions = new ArrayList<>();
    private List<ImageButton> list_of_home_buttons = new ArrayList<>();
    private List<ImageButton> list_of_opponent_buttons = new ArrayList<>();

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

        list_of_positions.add("Goalie");
        list_of_positions.add("Left Defense");
        list_of_positions.add("Left Midfield");
        list_of_positions.add("Left Forward");
        list_of_positions.add("Center Defense");
        list_of_positions.add("Center Midfield");
        list_of_positions.add("Center Forward");
        list_of_positions.add("Right Defense");
        list_of_positions.add("Right Midfield");
        list_of_positions.add("Right Forward");

        home_goalie = root.findViewById(R.id.home_goalie);
        home_left_def = root.findViewById(R.id.home_left_back);
        home_left_mid = root.findViewById(R.id.home_left_mid);
        home_left_front = root.findViewById(R.id.home_left_front);
        home_mid_def = root.findViewById(R.id.home_mid_back);
        home_mid_mid = root.findViewById(R.id.home_mid_mid);
        home_mid_front = root.findViewById(R.id.home_mid_front);
        home_right_def = root.findViewById(R.id.home_right_back);
        home_right_mid = root.findViewById(R.id.home_right_mid);
        home_right_front = root.findViewById(R.id.home_right_front);
        
        list_of_home_buttons.add(home_goalie);
        list_of_home_buttons.add(home_left_def);
        list_of_home_buttons.add(home_left_mid);
        list_of_home_buttons.add(home_left_front);
        list_of_home_buttons.add(home_mid_def);
        list_of_home_buttons.add(home_mid_mid);
        list_of_home_buttons.add(home_mid_front);
        list_of_home_buttons.add(home_right_def);
        list_of_home_buttons.add(home_right_mid);
        list_of_home_buttons.add(home_right_front);

        opp_goalie = root.findViewById(R.id.opp_goalie);
        opp_left_def = root.findViewById(R.id.opp_left_back);
        opp_left_mid = root.findViewById(R.id.opp_left_mid);
        opp_left_front = root.findViewById(R.id.opp_left_front);
        opp_mid_def = root.findViewById(R.id.opp_mid_back);
        opp_mid_mid = root.findViewById(R.id.opp_mid_mid);
        opp_mid_front = root.findViewById(R.id.opp_mid_front);
        opp_right_def = root.findViewById(R.id.opp_right_back);
        opp_right_mid = root.findViewById(R.id.opp_right_mid);
        opp_right_front = root.findViewById(R.id.opp_right_front);

        list_of_opponent_buttons.add(opp_goalie);
        list_of_opponent_buttons.add(opp_left_def);
        list_of_opponent_buttons.add(opp_left_mid);
        list_of_opponent_buttons.add(opp_left_front);
        list_of_opponent_buttons.add(opp_mid_def);
        list_of_opponent_buttons.add(opp_mid_mid);
        list_of_opponent_buttons.add(opp_mid_front);
        list_of_opponent_buttons.add(opp_right_def);
        list_of_opponent_buttons.add(opp_right_mid);
        list_of_opponent_buttons.add(opp_right_front);

        // Get the Firebase database instance
        player_firebase_db = FirebaseHelper.getInstance().getDatabase();
        list_of_players = new ArrayList<Player>();
        list_of_opponents = new ArrayList<Player>();

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

                    Player tempPlayer;
                    for(int i = 0; i < list_of_positions.size(); i++){
                        tempPlayer = getPlayerByPosition(list_of_positions.get(i));
                        if(tempPlayer != null){
                            list_of_home_buttons.get(i).setImageResource(tempPlayer.getImageResourceID());
                        }
                    }
 
                },
                e -> Log.e("FieldFragment", "Error querying players", e)
        );

        //retrieve the opponents
        // Query opponents from Firestore
        FirebaseHelper.getInstance().getAllOpponents(
                queryDocumentSnapshots -> {
                    list_of_opponents.clear(); // Clear existing list
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Player player = document.toObject(Player.class);
                        System.out.println("ADDING A FIELD OPPONENT! INSIDE");
                        list_of_opponents.add(player);
                        System.out.println("FIELD OPPONENT ADDED: " + player.getPlayer_name());

                    }
                    System.out.println("FIELD OPPONENT");
                    for(int i = 0; i < list_of_opponents.size(); i++){
                        System.out.print("FIELD OPPONENThere: ");
                        System.out.println(list_of_opponents.get(i).getPlayer_name());
                    }

                    // Set player list to ViewModel
                    fieldViewModel.setOpponentList(list_of_opponents);

                    Player tempPlayer;
                    for(int i = 0; i < list_of_positions.size(); i++){
                        tempPlayer = getOpponentByPosition(list_of_positions.get(i));
                        if(tempPlayer != null){
                            list_of_opponent_buttons.get(i).setImageResource(tempPlayer.getImageResourceID());
                        }
                    }

                },
                e -> Log.e("FieldFragment", "Error querying opponents", e)
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

    public Player getOpponentByPosition(String position){
        for(int i = 0; i < list_of_players.size(); i++){
            if(list_of_players.get(i).getPlayer_position().equals(position)){
                return list_of_players.get(i);
            }
        }
        return null;
    }
}