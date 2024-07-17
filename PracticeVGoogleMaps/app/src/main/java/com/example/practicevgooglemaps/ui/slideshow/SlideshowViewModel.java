package com.example.practicevgooglemaps.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class SlideshowViewModel extends ViewModel {

    private final MutableLiveData<List<RecycleObject>> objectListLiveData;

    public SlideshowViewModel() {
        objectListLiveData = new MutableLiveData<>();
    }
    // Method to set or update player list in ViewModel
    public void setPlayerList(List<RecycleObject> players) {
        objectListLiveData.setValue(players);
    }

    // Getter for LiveData
    public LiveData<List<RecycleObject>> getObjectListLiveData() {
        return objectListLiveData;
    }
}