package com.example.practiceiv_firebase_fragments;

import android.util.Log;

import com.example.practiceiv_firebase_fragments.Player.Player;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FirebaseHelper {

    private static FirebaseHelper instance;
    private final FirebaseFirestore db;
    private static final String TAG = "RosterFragment";


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

    // Method to delete all documents from a collection
    public void clearCollection(String collectionName, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        db.collection(collectionName)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<DocumentSnapshot> documents = task.getResult().getDocuments();
                        for (DocumentSnapshot document : documents) {
                            db.collection(collectionName)
                                    .document(document.getId())
                                    .delete()
                                    .addOnFailureListener(e -> Log.e(TAG, "Error deleting document", e));
                        }
                        System.out.println("CLEARED DATABASE");
                    } else {
                        Log.e(TAG, "Error getting documents: ", task.getException());
                    }
                    onCompleteListener.onComplete(task);
                });
    }
}
