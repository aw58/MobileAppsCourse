package com.example.practiceiv_firebase_fragments;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;

import com.example.practiceiv_firebase_fragments.Player.Player;
import com.example.practiceiv_firebase_fragments.Player.PlayerAdapter;
import com.example.practiceiv_firebase_fragments.Player.PlayerDatabaseHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.practiceiv_firebase_fragments.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private Button logout_button;
    private PlayerDatabaseHelper playerDatabaseHelper;
    private SQLiteDatabase player_database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_roster, R.id.nav_opponents, R.id.nav_field)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
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

        //set up the player database
        playerDatabaseHelper = new PlayerDatabaseHelper(this);
        List<Player> player_roster = playerDatabaseHelper.getAllPlayers();

        //if there were no product there (default behavior) then load the products and pull again
        if(player_roster.isEmpty()){
            playerDatabaseHelper.populateProductsDatabase();
            player_roster = playerDatabaseHelper.getAllPlayers();
        }

        player_database = playerDatabaseHelper.getWritableDatabase();

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

    public SQLiteDatabase getDatabase(){
        return this.player_database;
    }
}