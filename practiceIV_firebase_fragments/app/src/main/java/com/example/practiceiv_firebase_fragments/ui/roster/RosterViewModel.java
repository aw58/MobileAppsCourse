package com.example.practiceiv_firebase_fragments.ui.roster;

import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practiceiv_firebase_fragments.MainActivity;
import com.example.practiceiv_firebase_fragments.Player.Player;
import com.example.practiceiv_firebase_fragments.Player.PlayerDatabaseHelper;

import java.util.List;

public class RosterViewModel extends ViewModel {

    private final MutableLiveData<List<Player>> rosterList = new MutableLiveData<>();


    public RosterViewModel() {


    }

    public LiveData<List<Player>> getRosterList() {
        return rosterList;
    }

    public void setRosterList(List<Player> newRosterList) {
        rosterList.setValue(newRosterList);
    }



}