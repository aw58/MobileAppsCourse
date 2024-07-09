package com.example.practiceiv_firebase_fragments.ui.roster;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practiceiv_firebase_fragments.FirebaseHelper;
import com.example.practiceiv_firebase_fragments.Player.Player;
import com.example.practiceiv_firebase_fragments.Player.PlayerAdapter;
import com.example.practiceiv_firebase_fragments.R;
import com.example.practiceiv_firebase_fragments.databinding.FragmentRosterBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class RosterFragment extends Fragment {
    private static final String TAG = "RosterFragment";

    private FragmentRosterBinding binding;

    private RecyclerView player_recyclerView;

    private PlayerAdapter playerAdapter;

    private FirebaseFirestore player_firebase_db;

    private List<Player> list_of_players;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RosterViewModel rosterViewModel =
                new ViewModelProvider(this).get(RosterViewModel.class);

        binding = FragmentRosterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        player_recyclerView = root.findViewById(R.id.player_recyclerView);
        playerAdapter = new PlayerAdapter();
        player_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        player_recyclerView.setAdapter(playerAdapter);

        final TextView textView = binding.textGallery;
        rosterViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        rosterViewModel.getPlayerListLiveData().observe(getViewLifecycleOwner(), players -> {
            // Update RecyclerView adapter with new player list
            playerAdapter.updateRosterList(players);
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

                        System.out.println("PLAYERS");
                        for(int i = 0; i < list_of_players.size(); i++){
                            System.out.print("here: ");
                            System.out.println(list_of_players.get(i).getPlayer_name());
                        }

                        // Set player list to ViewModel
                        rosterViewModel.setPlayerList(list_of_players);
                    }
                },
                e -> Log.e(TAG, "Error querying players", e)
        );

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}