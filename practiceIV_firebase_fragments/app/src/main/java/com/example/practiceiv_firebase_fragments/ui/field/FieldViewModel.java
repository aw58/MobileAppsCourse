package com.example.practiceiv_firebase_fragments.ui.field;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FieldViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public FieldViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Field fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}