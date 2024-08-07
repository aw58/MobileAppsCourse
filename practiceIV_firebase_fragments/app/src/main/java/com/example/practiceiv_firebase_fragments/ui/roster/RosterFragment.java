package com.example.practiceiv_firebase_fragments.ui.roster;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practiceiv_firebase_fragments.FirebaseHelper;
import com.example.practiceiv_firebase_fragments.Player.Player;
import com.example.practiceiv_firebase_fragments.Player.PlayerAdapter;
import com.example.practiceiv_firebase_fragments.R;
import com.example.practiceiv_firebase_fragments.databinding.FragmentRosterBinding;
import com.google.android.material.badge.BadgeUtils;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class RosterFragment extends Fragment implements PlayerAdapter.OnItemClickListener{
    private static final String TAG = "RosterFragment";

    private FragmentRosterBinding binding;

    private RecyclerView player_recyclerView;

    private PlayerAdapter playerAdapter;

    private FirebaseFirestore player_firebase_db;

    private List<Player> list_of_players;

    RosterViewModel rosterViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        System.out.println("ROSTER FRAGMENT STARTED");
        rosterViewModel = new ViewModelProvider(this).get(RosterViewModel.class);

        binding = FragmentRosterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Update the title in the app_bar
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Home Team Roster");
            actionBar.setDisplayHomeAsUpEnabled(true); // Example: enable back button
        }

        player_recyclerView = root.findViewById(R.id.player_recyclerView);
        playerAdapter = new PlayerAdapter(this);
        player_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        player_recyclerView.setAdapter(playerAdapter);

        rosterViewModel.getPlayerListLiveData().observe(getViewLifecycleOwner(), players -> {
            // Update RecyclerView adapter with new player list
            System.out.println("From within RosterFragment, saw that players changed and calling UpdateRosterList");
            playerAdapter.updateRosterList(players);
            playerAdapter.notifyDataSetChanged();
        });

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
                        System.out.println("ADDING A PLAYER! INSIDE");
                        list_of_players.add(player);
                        System.out.println("PLAYER ADDED: " + player.getPlayer_name());

                    }
                    System.out.println("PLAYERS");
                    for(int i = 0; i < list_of_players.size(); i++){
                        System.out.print("here: ");
                        System.out.println(list_of_players.get(i).getPlayer_name());
                    }

                    // Set player list to ViewModel
                    rosterViewModel.setPlayerList(list_of_players);
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

    @Override
    public void onDropItemClick(int position) {
        Player player = list_of_players.get(position);

        // Remove the item from the local data set
        list_of_players.remove(position);
        rosterViewModel.setPlayerList(list_of_players);
        playerAdapter.notifyItemRemoved(position);
        playerAdapter.notifyDataSetChanged();
    }

}