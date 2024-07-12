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

public class LoginActivity extends AppCompatActivity {

    private TextView login_page_title;
    private TextInputEditText login_email;
    private TextInputEditText login_password;
    private Button login_button;
    private Button register_button;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_activity);

        //set local variables
        login_page_title = findViewById(R.id.login_page_title);
        login_email = findViewById(R.id.username_textView);
        login_password = findViewById(R.id.password_textView);
        login_button = findViewById(R.id.login_button);
        register_button = findViewById(R.id.register_button);

        firebaseAuth =FirebaseAuth.getInstance();

        login_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Check valid inputs
                if(TextUtils.isEmpty(login_email.getText())){
                    Toast noUsername = Toast.makeText(LoginActivity.this, "Username is empty", Toast.LENGTH_SHORT);
                    noUsername.show();
                    return;
                }

                if(TextUtils.isEmpty(login_password.getText())){
                    Toast noUsername = Toast.makeText(LoginActivity.this, "Password is empty", Toast.LENGTH_SHORT);
                    noUsername.show();
                    return;
                }

                //Call upon firebase to authenticate the user
                String email = String.valueOf(login_email.getText());
                String password = String.valueOf(login_password.getText());
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.putExtra("user_email", email);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Sign in failed
                                    Toast failLogin = Toast.makeText(LoginActivity.this, "Login failed.", Toast.LENGTH_SHORT);
                                    failLogin.show();
                                }
                            }
                        });
            }
        });


        register_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}
