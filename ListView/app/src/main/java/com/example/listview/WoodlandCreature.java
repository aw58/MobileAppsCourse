package com.example.listview;

import android.graphics.Color;
import android.media.Image;

import java.io.Serializable;

public class WoodlandCreature implements Serializable {
    //Data definitions
    private String name; //The name of the creature. Ex: Steve

    private String type; //The creature's type. Ex: Squirrel
    private int age; //The creature's age in years. Ex: 3
    private String description; //The creature's description. Ex: Steve loves acorns

    private int imageResourceID;

    private int color;

    //Instantiate...
    public WoodlandCreature(String name, String type, int age, String description, int imageResourceID, int color){
        this.name = name;
        this.type = type;
        this.age = age;
        this.description = description;
        this.imageResourceID = imageResourceID;
        this.color = color;
    }

    //Getters and setters
    public String getName(){
        return this.name;
    }

    public void setName(String type) {
        this.name = type;
    }

    public String getType(){
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAge(){
        return this.age;
    }

    public void setAge(int age){
        this.age = age;
    }

    public java.lang.String getDescription() {
        return this.description;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    public int getImageResourceID() {
        return this.imageResourceID;
    }

    public void setImageResourceID(int imageResourceID) {
        this.imageResourceID = imageResourceID;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
