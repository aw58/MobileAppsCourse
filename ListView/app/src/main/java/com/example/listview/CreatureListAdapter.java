package com.example.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CreatureListAdapter extends ArrayAdapter<WoodlandCreature> {

    //Public constructor
    public CreatureListAdapter(Context context, ArrayList<WoodlandCreature> creatures){
        super(context, 0, creatures);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_woodlandcreature, parent, false);
        }

        WoodlandCreature creature = getItem(position);

        //Create local variables for the on-screen elements of the list item
        TextView textViewName = convertView.findViewById(R.id.textView_Name);
        TextView textViewType = convertView.findViewById(R.id.textView_Type);
        TextView textViewAge = convertView.findViewById(R.id.textView_Age);
        ImageView image = convertView.findViewById(R.id.ImageView_image);

        assert creature != null;
        //Customize the elements on-screen with the creature's unique information, as from its getter functions
        textViewName.setText(creature.getName());
        textViewType.setText(creature.getType());
        textViewAge.setText(String.valueOf(creature.getAge()));
        image.setImageResource(creature.getImageResourceID());

        return convertView;
    }

}
