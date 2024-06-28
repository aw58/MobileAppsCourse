package com.example.practice3_sqldatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ProductDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "products_database";

    private static final int DATABASE_VERSION = 4;

    private static final String TABLE_PRODUCTS = "products";

    private static final String KEY_ID = "id";

    private static final String KEY_NAME = "name";

    private static final String KEY_DESCRIPTION = "description";

    private static final String KEY_SELLER = "seller";

    private static final String KEY_PRICE = "price";

    private static final String KEY_IMAGE = "image";

    public ProductDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String QUERY_CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + " (" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_NAME + " TEXT, " +
                KEY_DESCRIPTION + " TEXT," +
                KEY_SELLER + " TEXT," +
                KEY_PRICE + " INTEGER, " +
                KEY_IMAGE + " INTEGER" +
                ")";

        db.execSQL(QUERY_CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        // Create tables again
        onCreate(db);
    }

    public void populateProductsDatabase(){
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, 1);
        values.put(KEY_NAME, "Void Generator");
        values.put(KEY_PRICE, 4);
        values.put(KEY_SELLER, "Void Surplus");
        values.put(KEY_DESCRIPTION, "This product will give you all the space you need! Create an endless void today!");
        values.put(KEY_IMAGE, R.drawable.void_blaster);
        database.insert(TABLE_PRODUCTS, null, values);

        values = new ContentValues();
        values.put(KEY_ID, 2);
        values.put(KEY_NAME, "Exterminatorinator");
        values.put(KEY_PRICE, 10);
        values.put(KEY_SELLER, "NotDeadYet Services");
        values.put(KEY_DESCRIPTION, "The all new 2056 best-in-class extermination machine. Gets rid of anything by launching it into deep space!");
        values.put(KEY_IMAGE, R.drawable.exterminator);
        database.insert(TABLE_PRODUCTS, null, values);

        values = new ContentValues();
        values.put(KEY_ID, 3);
        values.put(KEY_NAME, "Intergalactic Telephone");
        values.put(KEY_PRICE, 2);
        values.put(KEY_SELLER, "CallYourMom.glx");
        values.put(KEY_DESCRIPTION, "Connect with all your intergalactic buddies for an incredibly low price! (Interstellar Call Credits not included.)");
        values.put(KEY_IMAGE, R.drawable.telephone);
        database.insert(TABLE_PRODUCTS, null, values);

        values = new ContentValues();
        values.put(KEY_ID, 4);
        values.put(KEY_NAME, "Sun Diver");
        values.put(KEY_PRICE, 400);
        values.put(KEY_SELLER, "CrazSportz Suppliers");
        values.put(KEY_DESCRIPTION, "A ship used to ride solar flares. It might be expensive, the the thrill is worth it!");
        values.put(KEY_IMAGE, R.drawable.sun_diver);
        database.insert(TABLE_PRODUCTS, null, values);

        values = new ContentValues();
        values.put(KEY_ID, 5);
        values.put(KEY_NAME, "Hitchiker's Guide");
        values.put(KEY_PRICE, 2);
        values.put(KEY_SELLER, "Hitchiker's Inc.");
        values.put(KEY_DESCRIPTION, "A must-have guide for all you adventurous klaxon's out there. Never get lost in the galaxy with it!");
        values.put(KEY_IMAGE, R.drawable.hitchiker);
        database.insert(TABLE_PRODUCTS, null, values);

        values = new ContentValues();
        values.put(KEY_ID, 6);
        values.put(KEY_NAME, "Bonsai of the Erdtree");
        values.put(KEY_PRICE, 2);
        values.put(KEY_SELLER, "FromSomewhere");
        values.put(KEY_DESCRIPTION, "A shard of the blessed Erdtree of the Lands Between, grown to perfectly fit in your lavish living room.");
        values.put(KEY_IMAGE, R.drawable.bonsai);
        database.insert(TABLE_PRODUCTS, null, values);

        database.close();
    }

    public List<Product> getAllProducts(){
        List<Product> products = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through all rows and add to the list
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DESCRIPTION));
                String seller = cursor.getString(cursor.getColumnIndexOrThrow(KEY_SELLER));
                int price = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_PRICE));
                int image = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_IMAGE));

                Product product = new Product(id, name, description, seller, price, image);
                System.out.println(product);
                System.out.println(("PRODUCT IMAGE: " + product.getImageResourceID()));
                products.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return products;
    }

    public void getByCategory(){
        //There is no category attribute in the database. Not implemented.
    }

}
