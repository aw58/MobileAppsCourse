package com.example.practice3_sqldatabase;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SecondActivity extends AppCompatActivity {

    //local variables for xml elements
    private Button send_email_button;

    private Button back_button;

    private ProductAdapter second_productAdapter;

    private RecyclerView second_recyclerView;

    //local copy of selectedProducts coming in over the intent
    private List<Product> selectedProducts;

    private static final int EMAIL_REQUEST_CODE = 1;

    private ActivityResultLauncher<Intent> emailLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //default content
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //set local variables to views in the xml
        send_email_button = findViewById(R.id.send_email_button);
        back_button = findViewById(R.id.back_button);
        //get the Product information from the intent.
        this.selectedProducts = (List<Product>) getIntent().getSerializableExtra("selected_products");
        //System.out.println(this.selectedProducts);

        //find and populate the recyclerview with an Adapter based on the incoming products
        second_recyclerView = findViewById(R.id.second_selected_products_list);
        second_productAdapter = new ProductAdapter(this.selectedProducts);
        second_productAdapter.setSelectable(false);
        second_recyclerView.setAdapter(second_productAdapter);
        second_recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Register the email launcher
        emailLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    //Don't care what the result is since different email platforms respond with different codes.
                    //Just toast that the email process was started.
                    Toast.makeText(this, "Email Client Used Successfully.", Toast.LENGTH_SHORT).show();

                    //Remove any items from the secondary list.
                    second_productAdapter.clearProducts();

                    //Don't let the user send another email without going back to the home page.
                    send_email_button.setEnabled(false);
                }
        );

        send_email_button.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            //use a custom function to send the email. Abstracted for clarity
            public void onClick(View view){
                sendEmail();
            }
        });

        back_button.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view){
                //create an intent to navigate back to the mainActivity
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void sendEmail() {
        // Prepare email content
        StringBuilder emailContent = new StringBuilder();
        emailContent.append("<html><body>");
        emailContent.append("<h1>Selected Products:</h1>");

        //load up text for each product into the email
        for (Product product : this.selectedProducts) {
            emailContent.append("<h2>").append(product.getProduct_name()).append("</h2>");
            emailContent.append("<p>Price: ").append(product.getProduct_cost()).append(" ZipCoins</p>");
            emailContent.append("<p>Seller: ").append(product.getProduct_seller()).append("</p>");
            emailContent.append("<p>").append(product.getProduct_description()).append("</p>");
            emailContent.append("<br/><br/>");
        }
        emailContent.append("</body></html>");

        // Create an intent to send email
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html"); // Email MIME type

        // Set recipient (you can set multiple recipients separated by comma)
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"sweng888mobileapps@gmail.com "});

        // Set subject
        intent.putExtra(Intent.EXTRA_SUBJECT, "Selected Products");

        // Set email body
        intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(emailContent.toString(), Html.FROM_HTML_MODE_LEGACY));


        // Start the email client chooser
        emailLauncher.launch(Intent.createChooser(intent, "Send Email"));
    }


}