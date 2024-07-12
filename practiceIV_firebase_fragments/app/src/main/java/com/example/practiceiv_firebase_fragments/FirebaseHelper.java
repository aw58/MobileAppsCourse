package com.example.practiceiv_firebase_fragments;

import android.util.Log;

import com.example.practiceiv_firebase_fragments.Player.Player;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
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
        DocumentReference playerRef = db.collection("players").document(player.getPlayer_id().toString());
        playerRef.set(player)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Player added with custom ID: " + player.getPlayer_id()))
                .addOnFailureListener(e -> Log.e("Firestore", "Error adding player", e));
    }

    public void dropPlayer(Player player, OnSuccessListener<DocumentReference> onSuccessListener, OnFailureListener onFailureListener) {
        DocumentReference playerRef = db.collection("players").document(player.getPlayer_id().toString());
        playerRef.delete()
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Player deleted with custom ID: " + player.getPlayer_id()))
                .addOnFailureListener(e -> Log.e("Firestore", "Error deleting player", e));
    }

    public void addOpponent(Player opponent, OnSuccessListener<DocumentReference> onSuccessListener, OnFailureListener onFailureListener) {
        DocumentReference playerRef = db.collection("opponents").document(opponent.getPlayer_id().toString());
        playerRef.set(opponent)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Opponent added with custom ID: " + opponent.getPlayer_id()))
                .addOnFailureListener(e -> Log.e("Firestore", "Error adding Opponent", e));
    }

    public void addBackup(Player opponent, OnSuccessListener<DocumentReference> onSuccessListener, OnFailureListener onFailureListener) {
        DocumentReference playerRef = db.collection("backups").document(opponent.getPlayer_id().toString());
        playerRef.set(opponent)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Backup added with custom ID: " + opponent.getPlayer_id()))
                .addOnFailureListener(e -> Log.e("Firestore", "Error adding Backup", e));
    }

    public void dropBackup(Player player, OnSuccessListener<DocumentReference> onSuccessListener, OnFailureListener onFailureListener) {
        DocumentReference playerRef = db.collection("backups").document(player.getPlayer_id().toString());
        playerRef.delete()
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Backup deleted with custom ID: " + player.getPlayer_id()))
                .addOnFailureListener(e -> Log.e("Firestore", "Error deleting Backup", e));
    }

    public void getAllPlayers(OnSuccessListener<QuerySnapshot> onSuccessListener, OnFailureListener onFailureListener) {
        db.collection("players")
                .get()
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    public void getAllOpponents(OnSuccessListener<QuerySnapshot> onSuccessListener, OnFailureListener onFailureListener) {
        db.collection("opponents")
                .get()
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    public void getAllBackups(OnSuccessListener<QuerySnapshot> onSuccessListener, OnFailureListener onFailureListener) {
        db.collection("backups")
                .get()
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    public void updatePlayer(String playerID, Player player){
        // Update the player document in Firestore
        FirebaseHelper.getInstance().getDatabase()
                .collection("players")
                .document(playerID)
                .set(player)
                .addOnSuccessListener(aVoid -> {
                    // Successfully updated the player
                    Log.d("Firestore", "Player successfully updated!");
                })
                .addOnFailureListener(e -> {
                    // Failed to update the player
                    Log.e("Firestore", "Error updating player", e);
                });
    }

    public void updateOpponent(String playerID, Player player){
        // Update the player document in Firestore
        FirebaseHelper.getInstance().getDatabase()
                .collection("opponents")
                .document(playerID)
                .set(player)
                .addOnSuccessListener(aVoid -> {
                    // Successfully updated the player
                    Log.d("Firestore", "opponents successfully updated!");
                })
                .addOnFailureListener(e -> {
                    // Failed to update the player
                    Log.e("Firestore", "Error updating opponents", e);
                });
    }

    public void updateBackup(String playerID, Player player){
        // Update the player document in Firestore
        FirebaseHelper.getInstance().getDatabase()
                .collection("backups")
                .document(playerID)
                .set(player)
                .addOnSuccessListener(aVoid -> {
                    // Successfully updated the player
                    Log.d("Firestore", "backups successfully updated!");
                })
                .addOnFailureListener(e -> {
                    // Failed to update the player
                    Log.e("Firestore", "Error updating backups", e);
                });
    }

    // Method to delete all documents from a collection
    public void clearCollection(String collectionName, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        System.out.println("CLEARING THE " + collectionName + " DATABASE");
        db.collection(collectionName)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<DocumentSnapshot> documents = task.getResult().getDocuments();
                        for (DocumentSnapshot document : documents) {
                            db.collection(collectionName)
                                    .document(document.getId())
                                    .delete()
                                    .addOnFailureListener(e -> Log.e("FirebaseHelper", "Error deleting document" + document.getId(), e));
                        }
                        System.out.println("CLEARED " + collectionName + " DATABASE");
                    } else {
                        Log.e("FirebaseHelper", "Error getting documents: ", task.getException());
                    }
                    onCompleteListener.onComplete(task);
                });
    }

}
