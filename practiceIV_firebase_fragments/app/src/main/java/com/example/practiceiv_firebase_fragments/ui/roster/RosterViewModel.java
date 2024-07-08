package com.example.practiceiv_firebase_fragments.ui.roster;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RosterViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RosterViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is roster fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}