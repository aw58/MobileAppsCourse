package com.example.practiceiv_firebase_fragments.ui.roster;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.practiceiv_firebase_fragments.Player.Player;

import java.util.List;

public class RosterViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    private final MutableLiveData<List<Player>> playerListLiveData;

    public RosterViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is roster fragment");
        playerListLiveData = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setText(String text) {
        mText.setValue(text);
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