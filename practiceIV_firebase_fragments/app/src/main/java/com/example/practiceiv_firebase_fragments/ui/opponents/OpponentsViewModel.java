package com.example.practiceiv_firebase_fragments.ui.opponents;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.practiceiv_firebase_fragments.Player.Player;

import java.util.List;

public class OpponentsViewModel extends ViewModel {


    private final MutableLiveData<List<Player>> playerListLiveData;

    public OpponentsViewModel() {
        playerListLiveData = new MutableLiveData<>();
    }

    // Method to set or update player list in ViewModel
    public void setPlayerList(List<Player> players) {
        playerListLiveData.setValue(players);
    }

    // Getter for LiveData
    public LiveData<List<Player>> getPlayerListLiveData() {
        return playerListLiveData;
    }
}