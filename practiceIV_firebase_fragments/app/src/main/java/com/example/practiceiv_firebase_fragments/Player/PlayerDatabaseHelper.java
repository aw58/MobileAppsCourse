package com.example.practiceiv_firebase_fragments.Player;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.practiceiv_firebase_fragments.R;

import java.util.ArrayList;
import java.util.List;

public class PlayerDatabaseHelper extends SQLiteOpenHelper {

    //local variables
    private static final String DATABASE_NAME = "player_database";

    //must update database version every time the schema changes!
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_PLAYERS = "products";

    //keys to the database values
    private static final String KEY_ID = "id";

    private static final String KEY_NAME = "name";

    private static final String KEY_DESCRIPTION = "description";

    private static final String KEY_POSITION = "position";

    private static final String KEY_YEARS_PLAYED = "years_played";

    private static final String KEY_AGE = "age";

    private static final String KEY_IMAGE = "image";

    private static final String KEY_ON_ROSTER = "on_roster";

    public PlayerDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //create the database schema. Changes here must update the version number
        String QUERY_CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PLAYERS + " (" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_NAME + " TEXT, " +
                KEY_DESCRIPTION + " TEXT," +
                KEY_POSITION + " TEXT," +
                KEY_YEARS_PLAYED + " INTEGER," +
                KEY_AGE + " INTEGER, " +
                KEY_IMAGE + " INTEGER," +
                KEY_ON_ROSTER + " INTEGER" +
                ")";

        db.execSQL(QUERY_CREATE_PRODUCTS_TABLE);
    }

    //called on an update to the DB version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
        // Create tables again
        onCreate(db);
    }

    //populate the database with all the objects in our store!
    //don't actually make Product objects until they are actually retrieved.
    public void populateProductsDatabase(){
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, 1);
        values.put(KEY_NAME, "Forg the Unstoppable");
        values.put(KEY_AGE, 4);
        values.put(KEY_YEARS_PLAYED, 10);
        values.put(KEY_DESCRIPTION, "From the back swamps of Nantucket, this frog just keeps hopping!");
        values.put(KEY_POSITION, "Center Midfield");
        values.put(KEY_IMAGE, R.drawable.forg);
        values.put(KEY_ON_ROSTER, true);
        database.insert(TABLE_PLAYERS, null, values);

        /*
        values = new ContentValues();
        values.put(KEY_ID, 2);
        values.put(KEY_NAME, "Exterminatorinator");
        values.put(KEY_PRICE, 10);
        values.put(KEY_SELLER, "NotDeadYet Services");
        values.put(KEY_DESCRIPTION, "The all new 2056 best-in-class extermination machine. Gets rid of anything by launching it into deep space!");
        values.put(KEY_IMAGE, R.drawable.exterminator);
        database.insert(TABLE_PRODUCTS, null, values);
        */

        database.close();
    }

    //return all the products as a list of Product objects.
    public List<Player> getAllPlayers(){
        List<Player> players = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_PLAYERS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through all rows and add to the list
        if (cursor.moveToFirst()) {
            do {
                //load up all data into local variables
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DESCRIPTION));
                String position = cursor.getString(cursor.getColumnIndexOrThrow(KEY_POSITION));
                int years_played = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_YEARS_PLAYED));
                int age = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_AGE));
                int image = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_IMAGE));
                int on_roster = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ON_ROSTER));

                //use local variables to make a Product object
                Player player = new Player(id, name, description, position, years_played, age, image, on_roster);
                //System.out.println(product);
                //System.out.println(("PRODUCT IMAGE: " + product.getImageResourceID()));

                //add that product to the return list
                players.add(player);
            } while (cursor.moveToNext()); //do this for all products available
        }

        //close the database
        cursor.close();
        db.close();

        //return all product objects
        return players;
    }

    public void getByCategory(){
        //There is no category attribute in the database. Not implemented.
    }

}
