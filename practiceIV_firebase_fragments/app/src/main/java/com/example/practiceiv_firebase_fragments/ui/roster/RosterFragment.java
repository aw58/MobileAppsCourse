package com.example.practiceiv_firebase_fragments.ui.roster;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practiceiv_firebase_fragments.MainActivity;
import com.example.practiceiv_firebase_fragments.Player.PlayerAdapter;
import com.example.practiceiv_firebase_fragments.Player.PlayerDatabaseHelper;
import com.example.practiceiv_firebase_fragments.R;
import com.example.practiceiv_firebase_fragments.databinding.FragmentRosterBinding;

import java.util.ArrayList;
import java.util.List;

public class RosterFragment extends Fragment {

    private FragmentRosterBinding binding;
    private RecyclerView player_recyclerView;
    private PlayerAdapter playerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RosterViewModel rosterViewModel =
                new ViewModelProvider(this).get(RosterViewModel.class);

        binding = FragmentRosterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Access the database from MainActivity
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            SQLiteDatabase database = mainActivity.getDatabase();
        }

        player_recyclerView = root.findViewById(R.id.roster_RecyclerView);
        player_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        playerAdapter = new PlayerAdapter(new ArrayList<>());
        player_recyclerView.setAdapter(playerAdapter);

        rosterViewModel = new ViewModelProvider(this).get(RosterViewModel.class);

        fetchDataFromDatabase();

        // Observe the LiveData from the ViewModel
        rosterViewModel.getRosterList().observe(getViewLifecycleOwner(), rosterList -> {
            playerAdapter.updateRosterList(rosterList);
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void fetchDataFromDatabase() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            SQLiteDatabase database = mainActivity.getDatabase();
            List<String> playerList = new ArrayList<>();

            // Query the database to get player information
            Cursor cursor = database.rawQuery("SELECT name FROM players", null);
            if (cursor.moveToFirst()) {
                do {
                    String playerName = cursor.getString(0);
                    playerList.add(playerName);
                } while (cursor.moveToNext());
            }
            cursor.close();

            // Pass data to ViewModel
            //
        }
    }
}