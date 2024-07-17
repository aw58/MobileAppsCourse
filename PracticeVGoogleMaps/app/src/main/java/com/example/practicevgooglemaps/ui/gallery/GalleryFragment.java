package com.example.practicevgooglemaps.ui.gallery;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.practicevgooglemaps.R;
import com.example.practicevgooglemaps.databinding.FragmentGalleryBinding;

import java.util.Random;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private Button click_me;
    private Random random;
    private int screenWidth;
    private int screenHeight;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        random = new Random();

        click_me = root.findViewById(R.id.click_me);
        // Get screen dimensions
        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = container.getWidth();
        screenHeight = container.getHeight();

        // Set click listener for the button
        click_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveButtonToRandomLocation();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void moveButtonToRandomLocation() {
        // Calculate random coordinates
        int newX = random.nextInt(screenWidth - click_me.getWidth());
        int newY = random.nextInt(screenHeight - click_me.getHeight());

        // Animate button to new coordinates
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(click_me, "x", newX);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(click_me  , "y", newY);
        animatorX.setDuration(250); // Animation duration in milliseconds
        animatorY.setDuration(500); //make it move erratically
        animatorX.start();
        animatorY.start();
    }
}