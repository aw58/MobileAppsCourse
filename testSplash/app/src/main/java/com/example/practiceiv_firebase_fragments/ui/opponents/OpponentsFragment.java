package com.example.practiceiv_firebase_fragments.ui.opponents;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practiceiv_firebase_fragments.FirebaseHelper;
import com.example.practiceiv_firebase_fragments.Player.Player;
import com.example.practiceiv_firebase_fragments.Player.PlayerAdapter_Away;
import com.example.practiceiv_firebase_fragments.R;
import com.example.practiceiv_firebase_fragments.databinding.FragmentOpponentsBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class OpponentsFragment extends Fragment {
    private static final String TAG = "OpponentsFragment";

    private FragmentOpponentsBinding binding;

    private RecyclerView opponent_recyclerView;

    private PlayerAdapter_Away playerAdapterAway;

    private FirebaseFirestore player_firebase_db;

    private List<Player> list_of_opponents;

    public View onCreateView(@NonNull LayoutInflater inflater,
        ViewGroup container, Bundle savedInstanceState) {
        System.out.println("OPPONENTS!1");
        OpponentsViewModel opponentsViewModel = new ViewModelProvider(this).get(OpponentsViewModel.class);

        binding = FragmentOpponentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Update the title in the app_bar
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Opponent Team Roster");
            actionBar.setDisplayHomeAsUpEnabled(true); // Example: enable back button
        }
        System.out.println("OPPONENTS!2");
        opponent_recyclerView = root.findViewById(R.id.opponent_recyclerView);
        playerAdapterAway = new PlayerAdapter_Away();
        opponent_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        opponent_recyclerView.setAdapter(playerAdapterAway);
        System.out.println("OPPONENTS!3");
        opponentsViewModel.getPlayerListLiveData().observe(getViewLifecycleOwner(), players -> {
            // Update RecyclerView adapter with new player list
            System.out.println("UPDATING OPPONENT ROSTER LIST");
            playerAdapterAway.updateRosterList(players);
        });

        // Get the Firebase database instance
        player_firebase_db = FirebaseHelper.getInstance().getDatabase();
        list_of_opponents = new ArrayList<Player>();
        System.out.println("OPPONENTS!4 " + player_firebase_db);
        //retrieve the players
        // Query players from Firestore
        FirebaseHelper.getInstance().getAllOpponents(
                queryDocumentSnapshots -> {
                    list_of_opponents.clear(); // Clear existing list
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Player player = document.toObject(Player.class);
                        System.out.println("ADDING A OPPONENT! INSIDE");
                        list_of_opponents.add(player);
                        System.out.println("OPPONENT ADDED TO AWAYYYYYY: " + player.getPlayer_name());

                        System.out.println("OPPONENTS");
                        for(int i = 0; i < list_of_opponents.size(); i++){
                            System.out.print("OPPONENTS here: ");
                            System.out.println(list_of_opponents.get(i).getPlayer_name());
                        }

                        // Set player list to ViewModel
                        opponentsViewModel.setPlayerList(list_of_opponents);
                    }
                },
                e -> Log.e("OpponentsFragment", "Error querying players", e)
        );
        System.out.println("OPPONENTS!5");
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}