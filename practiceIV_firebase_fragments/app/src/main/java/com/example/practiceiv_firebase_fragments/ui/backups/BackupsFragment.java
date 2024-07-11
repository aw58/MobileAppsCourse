package com.example.practiceiv_firebase_fragments.ui.backups;


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
import com.example.practiceiv_firebase_fragments.Player.PlayerAdapter;
import com.example.practiceiv_firebase_fragments.Player.PlayerAdapter_Backups;
import com.example.practiceiv_firebase_fragments.R;
import com.example.practiceiv_firebase_fragments.databinding.FragmentBackupsBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class BackupsFragment extends Fragment {

    private @NonNull FragmentBackupsBinding binding;

    private RecyclerView backups_recyclerView;

    private PlayerAdapter_Backups playerAdapter;

    private FirebaseFirestore player_firebase_db;

    private List<Player> list_of_backups;

    public View onCreateView(@NonNull LayoutInflater inflater,
        ViewGroup container, Bundle savedInstanceState) {
        BackupsViewModel backupsViewModel = new ViewModelProvider(this).get(BackupsViewModel.class);

        binding = FragmentBackupsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Update the title in the app_bar
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Available Backups");
            actionBar.setDisplayHomeAsUpEnabled(true); // Example: enable back button
        }
        backups_recyclerView = root.findViewById(R.id.backups_recyclerView);
        playerAdapter = new PlayerAdapter_Backups();
        backups_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        backups_recyclerView.setAdapter(playerAdapter);
        backupsViewModel.getPlayerListLiveData().observe(getViewLifecycleOwner(), players -> {
            // Update RecyclerView adapter with new player list
            System.out.println("UPDATING BACKUPS ROSTER LIST");
            playerAdapter.updateRosterList(players);
        });

        // Get the Firebase database instance
        player_firebase_db = FirebaseHelper.getInstance().getDatabase();
        list_of_backups = new ArrayList<Player>();
        //retrieve the players
        // Query players from Firestore
        FirebaseHelper.getInstance().getAllBackups(
                queryDocumentSnapshots -> {
                    list_of_backups.clear(); // Clear existing list
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Player player = document.toObject(Player.class);
                        System.out.println("LOCAL TO BACKUPS: ADDING " + player.getPlayer_name());
                        list_of_backups.add(player);
                    }
                    for(int i = 0; i < list_of_backups.size(); i++){
                        System.out.println(list_of_backups.get(i).getPlayer_name());
                    }

                    // Set player list to ViewModel
                    backupsViewModel.setPlayerList(list_of_backups);
                },
                e -> Log.e("BackupsFragment", "Error querying players", e)
        );
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}