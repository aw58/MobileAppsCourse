package com.example.practiceiv_firebase_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Button;

import com.example.practiceiv_firebase_fragments.Player.Player;
import com.example.practiceiv_firebase_fragments.ui.roster.RosterFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.practiceiv_firebase_fragments.databinding.ActivityMainBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private Button logout_button;
    private Button reset_button;

    private NavController navController;

    private FirebaseFirestore player_firestore_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_roster, R.id.nav_backups, R.id.nav_opponents, R.id.nav_field)
                .setOpenableLayout(drawer)
                .build();

        // Initialize NavController
        //NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        //navController = navHostFragment.getNavController();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        NavigationUI.setupWithNavController(navigationView, navController);
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
        //link up the log out button with logout functionality
        logout_button = headerView.findViewById(R.id.logout_button);
        logout_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //link up the reset_database button with logout functionality
        reset_button = headerView.findViewById(R.id.reset_players);
        reset_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                System.out.println("RESET PLAYERS BUTTON PRESSED.");
                resetDatabase();
            }
        });

        //create the Firebase database. No instantiation needed. Can use reset button.
        System.out.println("CREATING THE DATABASE");
        player_firestore_db = FirebaseFirestore.getInstance();

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
        list_of_players.add(new Player(Long.parseLong("1"), "Forg the Unstoppable", "From the back swamps of Nantucket, this frog just keeps hopping!", "Center Midfield", 10, 4, R.drawable.forg, 1 ));
        list_of_players.add(new Player(Long.parseLong("2"), "Maple the Unsheathed", "Watch out- this little from fights dirty and will stop anything approaching the goal.", "Center Forward", 3, 2, R.drawable.forg, 1 ));
        list_of_players.add(new Player(Long.parseLong("3"), "Crazy Jack", "Some say Jack can jump to the ceiling of the stadium, though he's never needed to in a game.", "Center Defense", 5, 5, R.drawable.forg, 1 ));
        list_of_players.add(new Player(Long.parseLong("4"), "Sister Jumpy", "Sister Jumpy has an incredible devotion to the sport, and the passion to win over her teammates", "Left Midfield", 6, 6, R.drawable.forg, 1 ));
        list_of_players.add(new Player(Long.parseLong("5"), "Slimester the Slippery", "This frog can't be caught without their pursuer being covered in slime!", "Right Midfield", 5, 8, R.drawable.forg, 1 ));
        list_of_players.add(new Player(Long.parseLong("6"), "Broccoli the Camouflaged", "You'll never see him coming until it's too late. So fast that he's played for longer than he's lived, so he claims.", "Left Forward", 9, 2, R.drawable.forg, 0 ));
        list_of_players.add(new Player(Long.parseLong("7"), "Dennis", "Just Dennis", "Right Forward", 0, 1, R.drawable.forg, 0 ));
        list_of_players.add(new Player(Long.parseLong("8"), "Hopper", "Just Dennis", "Goalie", 0, 1, R.drawable.goalie, 0 ));

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
        list_of_opponents.add(new Player(Long.parseLong("1"), "Bartholomew", "From the back swamps of Nantucket, this frog just keeps hopping!", "Center Midfield", 10, 4, R.drawable.forg, 1 ));
        list_of_opponents.add(new Player(Long.parseLong("2"), "Richard", "Watch out- this little from fights dirty and will stop anything approaching the goal.", "Center Forward", 3, 2, R.drawable.forg, 1 ));
        list_of_opponents.add(new Player(Long.parseLong("3"), "Shawn", "Some say Jack can jump to the ceiling of the stadium, though he's never needed to in a game.", "Center Defense", 5, 5, R.drawable.forg, 1 ));
        list_of_opponents.add(new Player(Long.parseLong("4"), "Jake", "Sister Jumpy has an incredible devotion to the sport, and the passion to win over her teammates", "Left Midfield", 6, 6, R.drawable.forg, 1 ));
        list_of_opponents.add(new Player(Long.parseLong("5"), "Dude", "This frog can't be caught without their pursuer being covered in slime!", "Right Midfield", 5, 8, R.drawable.forg, 1 ));
        list_of_opponents.add(new Player(Long.parseLong("6"), "Porridge", "You'll never see him coming until it's too late. So fast that he's played for longer than he's lived, so he claims.", "Left Forward", 9, 2, R.drawable.forg, 1 ));
        list_of_opponents.add(new Player(Long.parseLong("7"), "Uhhh", "Just Dennis", "Right Forward", 0, 1, R.drawable.forg, 1 ));

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

    private void loadBackupsToDB(){
        //Create Player Objects
        List<Player> list_of_backups = new ArrayList<>();

        //Add all player objects here
        list_of_backups.add(new Player(Long.parseLong("9"), "Backup1", "From the back swamps of Nantucket, this frog just keeps hopping!", "Center Midfield", 10, 4, R.drawable.forg, 1 ));
        list_of_backups.add(new Player(Long.parseLong("10"), "Backup2", "Watch out- this little from fights dirty and will stop anything approaching the goal.", "Center Forward", 3, 2, R.drawable.forg, 1 ));
        list_of_backups.add(new Player(Long.parseLong("11"), "Backup3", "Some say Jack can jump to the ceiling of the stadium, though he's never needed to in a game.", "Center Defense", 5, 5, R.drawable.forg, 1 ));
        list_of_backups.add(new Player(Long.parseLong("12"), "Backup4", "Sister Jumpy has an incredible devotion to the sport, and the passion to win over her teammates", "Left Midfield", 6, 6, R.drawable.forg, 1 ));

        //System.out.println("PLAYERS SIZE: " + String.valueOf(list_of_players.size()));

        // Add players to Firestore
        for(int i = 0; i < list_of_backups.size(); i++){
            System.out.println("ADDING A Backup!");
            Player player = list_of_backups.get(i);
            System.out.println("Backup: " + list_of_backups.get(i).getPlayer_name());
            FirebaseHelper.getInstance().addBackup(player,
                    documentReference -> Log.d("MainActivity", "Backup added successfully with ID: " + documentReference.getId()),
                    e -> Log.e("MainActivity", "Error adding Backup", e)
            );
            System.out.println("GOT THROUGH Backup ADDING");
        }
    }

    private void resetDatabase(){
        FirebaseHelper.getInstance().clearCollection("players", new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.d("MainActivity", "Players successfully cleared");
                loadPlayersToDB();

                FirebaseHelper.getInstance().clearCollection("backups", new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.d("MainActivity", "Backups successfully cleared");
                        loadBackupsToDB();


                        System.out.println("REFRESHING THE FRAGMENT");
                        // Get the NavController associated with your NavHostFragment or DrawerLayout
                        NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment_content_main);

                        // Replace the current fragment with the new fragment
                        navController.navigate(R.id.nav_roster);
                    }
                });
            }
        });

    }

}