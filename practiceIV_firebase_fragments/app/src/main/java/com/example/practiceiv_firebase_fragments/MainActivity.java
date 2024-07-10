package com.example.practiceiv_firebase_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

import com.example.practiceiv_firebase_fragments.Player.Player;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.practiceiv_firebase_fragments.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthMultiFactorException;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private Button logout_button;
    private static final String TAG = "RosterFragment";

    private NavController navController;

    private FirebaseFirestore player_firestore_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("SOMETHING");
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        System.out.println("SOMETHING1.5");
        setContentView(binding.getRoot());
        System.out.println("SOMETHING1.75");

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });

        System.out.println("SOMETHING1.9");
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_roster, R.id.nav_opponents, R.id.nav_field)
                .setOpenableLayout(drawer)
                .build();
        System.out.println("SOMETHING1.95");
        // Initialize NavController
        //NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        //navController = navHostFragment.getNavController();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        System.out.println("SOMETHING1.96");

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        System.out.println("SOMETHING1.97");

        NavigationUI.setupWithNavController(navigationView, navController);
        System.out.println("SOMETHING2");
        //update the nav header with the user's information
        View headerView = navigationView.getHeaderView(0);
        /*
        String user_email = (String) getIntent().getSerializableExtra("user_email");
        assert user_email != null;
        String user_name = user_email.split("@")[0]; //TODO update with their name from the login pag
        navigationView = findViewById(R.id.nav_view);
        TextView nav_user_name = headerView.findViewById(R.id.nav_user_name_textView);
        TextView nav_user_email = headerView.findViewById(R.id.nav_user_email_textView);
        nav_user_name.setText(user_name);
        nav_user_email.setText(user_email);
        */
        System.out.println("SOMETHING3");
        //link up the log out button with logout functionality
        logout_button = headerView.findViewById(R.id.logout_button);
        logout_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        System.out.println("SOMETHING4");
        //create the Firebase database
        System.out.println("CREATING THE DATABASE");
        player_firestore_db = FirebaseFirestore.getInstance();

        //Clear the database
        /*
        System.out.println("CLEARING THE DB FROM MAIN");
        FirebaseHelper.getInstance().clearCollection("players", new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.d("MainActivity", "Collection successfully cleared");
            }
        });
        */


        //Add all the players to the database
        System.out.println("CALLING LOAD PLAYERS");
        loadPlayersToDB();
        //Add all the opponents to the database
        System.out.println("CALLING LOAD OPPONENTS");
        loadOpponentsToDB();

        // Update the title in the app_bar
        getSupportActionBar().setTitle("Home Team Roster");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public FirebaseFirestore getDatabase() {
        return player_firestore_db;
    }

    private void loadPlayersToDB(){
        //Create Player Objects
        List<Player> list_of_players = new ArrayList<>();

        //Add all player objects here
        list_of_players.add(new Player("1", "Forg the Unstoppable", "From the back swamps of Nantucket, this frog just keeps hopping!", "Center Midfield", 10, 4, R.drawable.forg, 1 ));
        list_of_players.add(new Player("2", "Maple the Unsheathed", "Watch out- this little from fights dirty and will stop anything approaching the goal.", "Center Forward", 3, 2, R.drawable.forg, 1 ));
        list_of_players.add(new Player("3", "Crazy Jack", "Some say Jack can jump to the ceiling of the stadium, though he's never needed to in a game.", "Center Defense", 5, 5, R.drawable.forg, 1 ));
        list_of_players.add(new Player("4", "Sister Jumpy", "Sister Jumpy has an incredible devotion to the sport, and the passion to win over her teammates", "Left Midfield", 6, 6, R.drawable.forg, 1 ));
        list_of_players.add(new Player("5", "Slimester the Slippery", "This frog can't be caught without their pursuer being covered in slime!", "Right Midfield", 5, 8, R.drawable.forg, 1 ));
        list_of_players.add(new Player("6", "Broccoli the Camouflaged", "You'll never see him coming until it's too late. So fast that he's played for longer than he's lived, so he claims.", "Left Forward", 9, 2, R.drawable.forg, 0 ));
        list_of_players.add(new Player("7", "Dennis", "Just Dennis", "Right Forward", 0, 1, R.drawable.forg, 0 ));

        //System.out.println("PLAYERS SIZE: " + String.valueOf(list_of_players.size()));

        // Add players to Firestore
        for(int i = 0; i < list_of_players.size(); i++){
            System.out.println("ADDING A PLAYER!");
            Player player = list_of_players.get(i);
            System.out.println("PLAYER: " + list_of_players.get(i).getPlayer_name());
            FirebaseHelper.getInstance().addPlayer(player,
                    documentReference -> Log.d("MainActivity", "Player added successfully with ID: " + documentReference.getId()),
                    e -> Log.e("MainActivity", "Error adding player", e)
            );
            System.out.println("GOT THROUGH ADDING");
        }
    }

    private void loadOpponentsToDB(){
        //Create Player Objects
        List<Player> list_of_opponents = new ArrayList<>();

        //Add all player objects here
        list_of_opponents.add(new Player("1", "Bartholomew", "From the back swamps of Nantucket, this frog just keeps hopping!", "Center Midfield", 10, 4, R.drawable.forg, 1 ));
        list_of_opponents.add(new Player("2", "Richard", "Watch out- this little from fights dirty and will stop anything approaching the goal.", "Center Forward", 3, 2, R.drawable.forg, 1 ));
        list_of_opponents.add(new Player("3", "Shawn", "Some say Jack can jump to the ceiling of the stadium, though he's never needed to in a game.", "Center Defense", 5, 5, R.drawable.forg, 1 ));
        list_of_opponents.add(new Player("4", "Jake", "Sister Jumpy has an incredible devotion to the sport, and the passion to win over her teammates", "Left Midfield", 6, 6, R.drawable.forg, 1 ));
        list_of_opponents.add(new Player("5", "Dude", "This frog can't be caught without their pursuer being covered in slime!", "Right Midfield", 5, 8, R.drawable.forg, 1 ));
        list_of_opponents.add(new Player("6", "Porridge", "You'll never see him coming until it's too late. So fast that he's played for longer than he's lived, so he claims.", "Left Forward", 9, 2, R.drawable.forg, 1 ));
        list_of_opponents.add(new Player("7", "Uhhh", "Just Dennis", "Right Forward", 0, 1, R.drawable.forg, 1 ));

        //System.out.println("PLAYERS SIZE: " + String.valueOf(list_of_players.size()));

        // Add players to Firestore
        for(int i = 0; i < list_of_opponents.size(); i++){
            System.out.println("ADDING A OPPONENT!");
            Player player = list_of_opponents.get(i);
            System.out.println("OPPONENT: " + list_of_opponents.get(i).getPlayer_name());
            FirebaseHelper.getInstance().addOpponent(player,
                    documentReference -> Log.d("MainActivity", "Opponent added successfully with ID: " + documentReference.getId()),
                    e -> Log.e("MainActivity", "Error adding Opponent", e)
            );
            System.out.println("GOT THROUGH Opponent ADDING");
        }
    }
}