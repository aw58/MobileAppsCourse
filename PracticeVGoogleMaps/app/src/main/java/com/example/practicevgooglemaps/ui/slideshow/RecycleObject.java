package com.example.practicevgooglemaps.ui.slideshow;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

public class RecycleObject implements Parcelable {

    //A Product's data fields:

    private String object_name;

    private String object_description;

    private int imageResourceID;



    //default constructor
    public RecycleObject(String object_name, String object_description, int imageResourceID){
        this.object_name = object_name;
        this.object_description = object_description;
        this.imageResourceID = imageResourceID;
    }

    // Required default constructor
    public RecycleObject() {
        // Default constructor required for Firestore
    }

    //parcelable-specific constructor
    @SuppressLint("NewApi")
    protected RecycleObject(Parcel in) {
        object_name = in.readString();
        object_description = in.readString();
        imageResourceID = in.readInt();
    }

    @SuppressLint("NewApi")
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.object_name);
        dest.writeString(this.object_description);
        dest.writeInt(this.imageResourceID);
    }

    //necessary for the Parcel class
    public static final Creator<RecycleObject> CREATOR = new Creator<RecycleObject>() {
        @Override
        public RecycleObject createFromParcel(Parcel in) {
            return new RecycleObject(in);
        }

        @Override
        public RecycleObject[] newArray(int size) {
            return new RecycleObject[size];
        }
    };

    //Getters and Setters!


    public String getObject_name(){
        return this.object_name;
    }

    public void setObject_name(String player_name){
        this.object_name = player_name;
    }


    public String getObject_description(){
        return this.object_description;
    }

    public void setObject_description(String player_description) {
        this.object_description = player_description;
    }

    public int getImageResourceID(){
        return this.imageResourceID;
    }

    public void setImageResourceID(int imageResourceID){
        this.imageResourceID = imageResourceID;
    }

    //required for Parcelable
    @Override
    public int describeContents() {
        return 0;
    }
}
