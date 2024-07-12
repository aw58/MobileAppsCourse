package com.example.practiceiv_firebase_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private TextView register_page_title;
    private TextInputEditText register_email;
    private TextInputEditText register_password;
    private Button register_back_button;
    private Button register_send_button;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.register_activity);

        //set local variables
        register_page_title = findViewById(R.id.register_page_title);
        register_email = findViewById(R.id.register_username_textView);
        register_password = findViewById(R.id.register_password_textView);
        register_back_button = findViewById(R.id.regsiter_back_button);
        register_send_button = findViewById(R.id.register_send_button);

        firebaseAuth =FirebaseAuth.getInstance();

        register_send_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Check valid inputs
                if(TextUtils.isEmpty(register_email.getText())){
                    Toast noUsername = Toast.makeText(RegisterActivity.this, "Username is empty", Toast.LENGTH_SHORT);
                    noUsername.show();
                    return;
                }

                if(TextUtils.isEmpty(register_password.getText())){
                    Toast noUsername = Toast.makeText(RegisterActivity.this, "Password is empty", Toast.LENGTH_SHORT);
                    noUsername.show();
                    return;
                }

                //Call upon firebase to authenticate the user
                String email = String.valueOf(register_email.getText());
                String password = String.valueOf(register_password.getText());
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast good_register = Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT);
                                    good_register.show();
                                } else {
                                    // Registration failed
                                    Toast bad_register = Toast.makeText(RegisterActivity.this, "Registration failed.", Toast.LENGTH_SHORT);
                                    bad_register.show();
                                }
                            }
                        });
            }
        });


        register_back_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
