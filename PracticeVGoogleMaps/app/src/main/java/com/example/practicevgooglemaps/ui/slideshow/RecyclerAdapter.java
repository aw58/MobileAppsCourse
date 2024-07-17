package com.example.practicevgooglemaps.ui.slideshow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicevgooglemaps.R;

import java.util.ArrayList;
import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    //Local variables to keep track of objects in the list
    private List<RecycleObject> recycleObjects;


    //default constructor
    public RecyclerAdapter() {
        this.recycleObjects = new ArrayList<>();
    }

    public RecyclerAdapter(List<RecycleObject> recycleObjects) {
        this.recycleObjects = recycleObjects;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int position1 = position;  //got an error saying that "position" could change and that I should use a local variable instead
        //create a local copy of the object at position1
        RecycleObject object = recycleObjects.get(position1);

        //Set the holder's (item's) xml elements to the object-unique details
        //the ViewHolder class is defined below.
        holder.viewholder_name_editable.setText(object.getObject_name());
        holder.viewholder_description_editable.setText(String.valueOf(object.getObject_description()));
        holder.objectImage.setImageResource(object.getImageResourceID());
    }

    //Getters and Setters

    @Override
    public int getItemCount() {
        return this.recycleObjects.size();
    }


    //Custom ViewHolder class which allows the xml elements in a recyclerView item to be bound together into one object
    //made for ease of reference
    public class ViewHolder extends RecyclerView.ViewHolder {
        //the local variables for each of the xml elements in an item
        public TextView viewholder_name_editable;
        public TextView viewholder_description_editable;
        public ImageView objectImage;
        public Button recruit_button;

        public ViewHolder(View itemView) {
            super(itemView);
            //assign actual views to the local variables
            viewholder_name_editable = itemView.findViewById(R.id.item_name_title);
            viewholder_description_editable = itemView.findViewById(R.id.item_description_title);
            objectImage = itemView.findViewById(R.id.imageView);
        }
    }
}