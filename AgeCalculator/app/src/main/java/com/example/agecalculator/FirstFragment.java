package com.example.agecalculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.agecalculator.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    //Data definitions
    private FragmentFirstBinding binding;
    String showCountTextView;

    //Called at view's first setup
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View fragmentFirstLayout = inflater.inflate(R.layout.fragment_first, container, false);
        showCountTextView = "0";
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        //return binding.getRoot();
        return fragmentFirstLayout;

    }

    //Called at view's first setup
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Link up button clicks to action
        /*
        binding.CalculateBtn.setOnClickListener(v ->
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment)
        );
        */

        view.findViewById(R.id.Calculate_Btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countMe(view);
            }
        });
    }

    private void countMe(View view) {
        // Convert value to a number and increment it
        Integer count = Integer.parseInt(showCountTextView);
        count++;
        // Display the new value in the Toast.
        showCountTextView = count.toString();

        Toast myToast = Toast.makeText(getActivity(), showCountTextView, Toast.LENGTH_SHORT);
        myToast.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}