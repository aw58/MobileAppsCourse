package com.example.practiceiv_firebase_fragments.ui.field;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practiceiv_firebase_fragments.FirebaseHelper;
import com.example.practiceiv_firebase_fragments.MainActivity;
import com.example.practiceiv_firebase_fragments.Player.Player;
import com.example.practiceiv_firebase_fragments.Player.PlayerAdapter;
import com.example.practiceiv_firebase_fragments.R;
import com.example.practiceiv_firebase_fragments.databinding.FragmentFieldBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

public class FieldFragment extends Fragment {

    private FragmentFieldBinding binding;
    private static final String TAG = "FieldFragment";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FieldViewModel fieldViewModel =
                new ViewModelProvider(this).get(FieldViewModel.class);

        binding = FragmentFieldBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        fieldViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Update the title in the app_bar
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Field");
            actionBar.setDisplayHomeAsUpEnabled(true); // Example: enable back button
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}