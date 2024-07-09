package com.example.practiceiv_firebase_fragments.ui.opponents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.practiceiv_firebase_fragments.databinding.FragmentOpponentsBinding;

public class OpponentsFragment extends Fragment {

    private FragmentOpponentsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        OpponentsViewModel opponentsViewModel =
                new ViewModelProvider(this).get(OpponentsViewModel.class);

        binding = FragmentOpponentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        opponentsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Update the title in the app_bar
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Opponents");
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