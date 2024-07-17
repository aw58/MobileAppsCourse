package com.example.practicevgooglemaps.ui.slideshow;

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

import com.example.practicevgooglemaps.R;
import com.example.practicevgooglemaps.databinding.FragmentSlideshowBinding;

import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = root.findViewById(R.id.recyclerView);
        List<RecycleObject> objects = loadObjects();
        recyclerAdapter = new RecyclerAdapter(objects);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyclerAdapter);

        return root;
    }

    public List<RecycleObject> loadObjects(){
        List<RecycleObject> objects = new ArrayList<>();

        objects.add(new RecycleObject("Plastic Bottle", "Recycle me!", R.drawable.bottle));
        objects.add(new RecycleObject("Glass Bottle", "Recycle me!", R.drawable.glass));
        objects.add(new RecycleObject("Aluminum Can", "Recycle me!", R.drawable.can));
        objects.add(new RecycleObject("Tissue", "Throw Away!", R.drawable.tissue));
        objects.add(new RecycleObject("Newspaper", "Recycle me!", R.drawable.news));
        objects.add(new RecycleObject("Flushable Wipe", "Throw Away!", R.drawable.wipe));
        objects.add(new RecycleObject("Batteries", "Throw Away", R.drawable.batterz));
        objects.add(new RecycleObject("Food Waste", "Compost!", R.drawable.food));
        objects.add(new RecycleObject("Styrofoam", "Throw Away", R.drawable.styro));

        return objects;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}