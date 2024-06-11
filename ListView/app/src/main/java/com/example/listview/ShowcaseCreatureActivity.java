package com.example.listview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import kotlinx.coroutines.ObsoleteCoroutinesApi;

public class ShowcaseCreatureActivity extends AppCompatActivity {

    //Create local variables for visual elements
    private TextView mTextViewName;
    private TextView mTextViewType;
    private TextView mTextViewAge;
    private TextView mTextViewDescription;

    private ImageView mImage;

    private ConstraintLayout mbackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_showcasecreature);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Create local variable for navigation button
        Button backButton = findViewById(R.id.backToMain);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Send an explicit intent to navigate back to the main activity.
                //Since this activity was launched with a "startForResult" all we have to call is finish()
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

        //Get the selected WoodlandCreature from the intent from the MainActivity
        WoodlandCreature selectedCreature = (WoodlandCreature) getIntent().getSerializableExtra("Selected_Creature");

        //Populate up local textview variables from the xml layout
        mTextViewName = findViewById(R.id.textView_Name);
        mTextViewType = findViewById(R.id.textView_Type);
        mTextViewAge = findViewById(R.id.textView_Age);
        mTextViewDescription = findViewById(R.id.textView_Description);
        mbackground = findViewById(R.id.main);
        mImage = findViewById(R.id.imageView);

        //Set the xml text views with the creature's data by using its getters.
        assert selectedCreature != null;
        mTextViewName.setText(selectedCreature.getName());
        mTextViewType.setText(selectedCreature.getType());
        mTextViewAge.setText(String.valueOf(selectedCreature.getAge()));
        mTextViewDescription.setText(selectedCreature.getDescription());
        mbackground.setBackgroundColor(selectedCreature.getColor());
        mImage.setImageResource(selectedCreature.getImageResourceID());
    }
}