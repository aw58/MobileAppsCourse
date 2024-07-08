package com.example.practiceiv_firebase_fragments.ui.opponents;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OpponentsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public OpponentsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is OpponentsBBBBBBBBBBBB fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}