package com.example.practiceiv_firebase_fragments.ui.field;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.practiceiv_firebase_fragments.Player.Player;

import java.util.List;

public class FieldViewModel extends ViewModel {


    private final MutableLiveData<List<Player>> playerListLiveData;
    private final MutableLiveData<List<Player>> opponentListLiveData;

    public FieldViewModel() {

        playerListLiveData = new MutableLiveData<>();
        opponentListLiveData = new MutableLiveData<>();
    }


    // Method to set or update player list in ViewModel
    public void setPlayerList(List<Player> players) {
        playerListLiveData.setValue(players);
    }

    // Getter for LiveData
    public LiveData<List<Player>> getPlayerListLiveData() {
        return playerListLiveData;
    }

    // Method to set or update player list in ViewModel
    public void setOpponentList(List<Player> players) {
        opponentListLiveData.setValue(players);
    }

    // Getter for LiveData
    public LiveData<List<Player>> getOpponentListLiveData() {
        return opponentListLiveData;
    }

}