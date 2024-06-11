package com.example.listview;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mListViewCreatures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Create an arraylist of woodland creatures and call a custom function to actually load them into the list
        ArrayList<WoodlandCreature> creatures = loadWoodlandCreatures();
        //Create an adapter object to adapt the list objects for display in the listview
        CreatureListAdapter adapter = new CreatureListAdapter(MainActivity.this, creatures);

        //set local listview variable to on-screen element
        mListViewCreatures = findViewById(R.id.creature_listview);
        //give the listview a pre-defined adapter, as required for the class
        mListViewCreatures.setAdapter(adapter);
        mListViewCreatures.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                WoodlandCreature selectedCreature = (WoodlandCreature) parent.getItemAtPosition(position);
                //When a list item is selected, create an explicit intent to navigate to the ShowcaseCreature Activity
                //This is a startForResult so that a snackbar can be shown upon completion.
                Intent intent = new Intent(MainActivity.this, ShowcaseCreatureActivity.class);
                //Include the selectedCreature obeject as an extra within the intent so its information
                //can be read within the ShowcaseCreatureActivity.
                intent.putExtra("Selected_Creature", selectedCreature);
                startForResult.launch(intent);
            }
        });
    }

    //loadWoodlandCreatures()
    // returns an ArrayList populated with at least one WoodlandCreature object.
    private ArrayList<WoodlandCreature> loadWoodlandCreatures(){
        ArrayList<WoodlandCreature> creatures = new ArrayList<>();
        //Add a whole bunch of creatures by creating new WoodlandCreature objects.
        creatures.add(new WoodlandCreature("Steve", "Squirrel", 3, "Steve is a red-tailed deciduous squirrel. He loves acorns and playing with his best friend Joe.", R.drawable.steve, ContextCompat.getColor(MainActivity.this, R.color.Steve)));
        creatures.add(new WoodlandCreature("Maple", "Gray Tree Frog", 1, "Maple loves to eat bugs and sit on her favorite lilly pad in the pond.", R.drawable.maple, ContextCompat.getColor(MainActivity.this, R.color.Maple)));
        creatures.add(new WoodlandCreature("Joe", "Fox", 5, "Joe enjoys basking in the sunshine and eating eggs from the farmer's chicken coop.", R.drawable.joe, ContextCompat.getColor(MainActivity.this, R.color.Joe)));
        creatures.add(new WoodlandCreature("Mr. Biscuit", "Copperhead Snake", 2, "Mr. Biscuit once lived as a pet, but made a valiant escape and lives peacefully in Brioche forest.", R.drawable.mr_biscuit, ContextCompat.getColor(MainActivity.this, R.color.Mr_Biscuit)));
        creatures.add(new WoodlandCreature("Farquadd", "Cardinal", 3, "Farquadd is the finest of all birds, and knows it. He struts his red feathers around the whole forest.", R.drawable.farquadd, ContextCompat.getColor(MainActivity.this, R.color.Farquadd)));
        creatures.add(new WoodlandCreature("Humdinger", "Hummingbird", 1, "Humdinger is the most traveled of the forest, covering its whole circumference every day searching for flowers.", R.drawable.humdinger, ContextCompat.getColor(MainActivity.this, R.color.Humdinger)));
        creatures.add(new WoodlandCreature("Kita", "Gopher", 6, "Kita lives in a small, quaint burrow in the center of the forest with her gopher family.", R.drawable.kita, ContextCompat.getColor(MainActivity.this, R.color.Kita)));

        return creatures;
    }

    //This function is automatically called upon return from the ShowcaseCreatureActivity.
    //It is used to show either a toast or snackbar dependent on the success or failure of the intent
    private ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // Display a Toast or snackbar indicating success
                    //Toast.makeText(this, "Operation successful", Toast.LENGTH_SHORT).show();
                    // Create a snackbar to show to the user.
                    Snackbar.make(findViewById(android.R.id.content), "Operation successful", Snackbar.LENGTH_SHORT).show();
                }
                else{
                    //A toast of snackbar indicating failure.
                    //Toast.makeText(this, "Operation Failed!", Toast.LENGTH_SHORT).show();
                    Snackbar.make(findViewById(android.R.id.content), "Operation Failed!", Snackbar.LENGTH_SHORT).show();
                }
            }
    );

}