package com.example.practiceiv_firebase_fragments.Player;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

public class Player implements Parcelable {

    //A Product's data fields:
    private String player_id;

    private String player_name;

    private String player_description;

    private String player_position;

    private int player_age;

    private int years_played;

    private int imageResourceID;

    private int on_the_roster;


    //default constructor
    public Player(String player_id, String player_name, String player_description, String player_position, int years_played, int player_age, int imageResourceID, int on_the_roster){
        this.player_id = player_id;
        this.player_name = player_name;
        this.player_description = player_description;
        this.player_position = player_position;
        this.years_played = years_played;
        this.player_age = player_age;
        this.imageResourceID = imageResourceID;
        this.on_the_roster = on_the_roster;
    }

    // Required default constructor
    public Player() {
        // Default constructor required for Firestore
    }

    //parcelable-specific constructor
    @SuppressLint("NewApi")
    protected Player(Parcel in) {
        player_id = in.readString();
        player_name = in.readString();
        player_description = in.readString();
        player_position = in.readString();
        player_age = in.readInt();
        imageResourceID = in.readInt();
        on_the_roster = in.readInt();
    }

    @SuppressLint("NewApi")
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.player_id);
        dest.writeString(this.player_name);
        dest.writeString(this.player_description);
        dest.writeString(this.player_position);
        dest.writeInt(this.player_age);
        dest.writeInt(this.imageResourceID);
        dest.writeInt(this.on_the_roster);
    }

    //necessary for the Parcel class
    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    //Getters and Setters!
    public String  getPlayer_id(){
        return this.player_id;
    }

    public void setPlayer_id(String  player_id){
        this.player_id = player_id;
    }

    public String getPlayer_name(){
        return this.player_name;
    }

    public void setPlayer_name(String player_name){
        this.player_name = player_name;
    }

    public int getPlayer_age(){
        return this.player_age;
    }

    public void setPlayer_age(int player_age){
        this.player_age = player_age;
    }

    public String getPlayer_description(){
        return this.player_description;
    }

    public void setPlayer_description(String player_description){
        this.player_description = player_description;
    }
    public int getYears_played(){
        return this.years_played;
    }

    public void setYears_played(int years_played){
        this.years_played = years_played;
    }

    public String getPlayer_position(){
        return this.player_position;
    }

    public void setPlayer_position(String player_position){
        this.player_position = player_position;
    }

    public int getImageResourceID(){
        return this.imageResourceID;
    }

    public void setImageResourceID(int imageResourceID){
        this.imageResourceID = imageResourceID;
    }

    public int getOnTheRoster(){
        return this.on_the_roster;
    }

    public void setOnTheRoster(int on_the_roster){
        this.on_the_roster = on_the_roster;
    }


    //required for Parcelable
    @Override
    public int describeContents() {
        return 0;
    }
}
