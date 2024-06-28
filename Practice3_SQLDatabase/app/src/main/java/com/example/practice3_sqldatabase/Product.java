package com.example.practice3_sqldatabase;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Product implements Parcelable {

    private int product_id;

    private String product_name;

    private String product_description;

    private String product_seller;

    private int product_cost;

    private int imageResourceID;



    public Product(int product_id, String product_name, String product_description, String product_seller, int product_cost, int imageResourceID){
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_description = product_description;
        this.product_seller = product_seller;
        this.product_cost = product_cost;
        this.imageResourceID = imageResourceID;
    }

    protected Product(Parcel in) {
        product_id = in.readInt();
        product_name = in.readString();
        product_description = in.readString();
        product_seller = in.readString();
        product_cost = in.readInt();
        imageResourceID = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.product_id);
        dest.writeString(this.product_name);
        dest.writeString(this.product_description);
        dest.writeString(this.product_seller);
        dest.writeInt(this.product_cost);
        dest.writeInt(this.imageResourceID);
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public int getProduct_id(){
        return this.product_id;
    }

    public void setProduct_id(int product_id){
        this.product_id = product_id;
    }

    public String getProduct_name(){
        return this.product_name;
    }

    public void setProduct_name(String product_name){
        this.product_name = product_name;
    }

    public int getProduct_cost(){
        return this.product_cost;
    }

    public void setProduct_cost(int product_cost){
        this.product_cost = product_cost;
    }

    public String getProduct_description(){
        return this.product_description;
    }

    public void setProduct_description(String product_description){
        this.product_description = product_description;
    }

    public String getProduct_seller(){
        return this.product_seller;
    }

    public void setProduct_seller(String product_seller){
        this.product_seller = product_seller;
    }

    public int getImageResourceID(){
        return this.imageResourceID;
    }

    public void setImageResourceID(int imageResourceID){
        this.imageResourceID = imageResourceID;
    }
    @Override
    public int describeContents() {
        return 0;
    }
}
