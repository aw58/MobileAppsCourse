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

    private RecyclerView recyclerView;

    private ProductDatabaseHelper databaseHelper;

    private ProductAdapter productAdapter;

    private Button select_button;

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

        select_button = findViewById(R.id.select_button);
        recyclerView = findViewById(R.id.productRecyclerView);
        databaseHelper = new ProductDatabaseHelper(this);

        this.deleteDatabase(("product_database"));
        List<Product> products = databaseHelper.getAllProducts();

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
        productAdapter = new ProductAdapter(products);
        recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        select_button.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view){
                System.out.print("ADAPTER PRODUCTS: ");
                productAdapter.printSelected();
                if(productAdapter.getSelectedCount() < 3){
                    Toast myToast = Toast.makeText(view.getContext(), R.string.please_select_at_least_3_items, Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else{
                    Set<Product> selectedItems = productAdapter.getSelectedProducts();
                    ArrayList<Product> selectedProducts = new ArrayList<>(selectedItems);
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putParcelableArrayListExtra("selected_products", selectedProducts);
                    startActivity(intent);
                }
            }

        });

    }
}