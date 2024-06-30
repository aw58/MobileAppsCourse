package com.example.practice3_sqldatabase;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    //Local variable for recycler view on the xml
    private RecyclerView recyclerView;

    //local variable for a database helper
    private ProductDatabaseHelper databaseHelper;

    //local variable for the product adapter to be used in the recyclerView
    private ProductAdapter productAdapter;

    //local variable for the select button in the bottom right
    private Button select_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //default content
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //assign each local variable to their view on the screen
        select_button = findViewById(R.id.select_button);
        recyclerView = findViewById(R.id.productRecyclerView);
        //create a ProductDatabaseHelper. See custom class
        databaseHelper = new ProductDatabaseHelper(this);

        //If there is a product_database already, delete it
        this.deleteDatabase(("product_database"));
        //call the getAllProducts function to load up the local version of the product list
        List<Product> products = databaseHelper.getAllProducts();

        //if there were no product there (default behavior) then load the products and pull again
        if(products.isEmpty()){
            databaseHelper.populateProductsDatabase();
            products = databaseHelper.getAllProducts();
        }

        /*
        System.out.println("PRODUCTS!*****************************");
        System.out.println(products);
        System.out.println(products.get(1).getProduct_cost());
        System.out.println(products.get(2).getProduct_cost());
        */

        //Create a product adapter which takes in the local list of products
        productAdapter = new ProductAdapter(products);
        //set hte recyclerView's adapter
        recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //Functionality: check whether at least 3 items have been selected and if so, call intent to SecondActivity
        select_button.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view){
                //System.out.print("ADAPTER PRODUCTS: ");
                //productAdapter.printSelected();

                //Check the count of selected items and send a toast if under 3.
                if(productAdapter.getSelectedCount() < 3){
                    Toast myToast = Toast.makeText(view.getContext(), R.string.please_select_at_least_3_items, Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else{
                    //get a new local list of just the selected products
                    Set<Product> selectedItems = productAdapter.getSelectedProducts();
                    ArrayList<Product> selectedProducts = new ArrayList<>(selectedItems);
                    //send those selected products as an extra to the SecondActivity
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putParcelableArrayListExtra("selected_products", selectedProducts);
                    startActivity(intent);
                }
            }

        });

    }
}