package com.example.practiceiv_firebase_fragments;

import com.example.practiceiv_firebase_fragments.Player.Player;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QuerySnapshot;

public class FirebaseHelper {

    private static FirebaseHelper instance;
    private final FirebaseFirestore db;

    private FirebaseHelper() {
        db = FirebaseFirestore.getInstance();
    }

    public static synchronized FirebaseHelper getInstance() {
        if (instance == null) {
            instance = new FirebaseHelper();
        }
        return instance;
    }

    public FirebaseFirestore getDatabase() {
        return db;
    }

    public void addPlayer(Player player, OnSuccessListener<DocumentReference> onSuccessListener, OnFailureListener onFailureListener) {
        db.collection("players").add(player)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    public void getAllPlayers(OnSuccessListener<QuerySnapshot> onSuccessListener, OnFailureListener onFailureListener) {
        db.collection("players")
                .get()
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }
}
