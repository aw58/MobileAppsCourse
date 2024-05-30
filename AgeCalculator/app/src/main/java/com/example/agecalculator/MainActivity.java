package com.example.agecalculator;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;

import com.example.agecalculator.databinding.ActivityMainBinding;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.text.ParsePosition;

//Custom imports:
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Currency;
import java.util.Date;
import java.time.LocalDate;
import java.time.Period;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;


    //Custom data fields:
    private Button calculate_butt;
    private EditText first_name;
    private EditText last_name;
    private EditText dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Define local interface view references so I don't have to find them every time they are referenced
        calculate_butt = findViewById(R.id.Calculate_Btn);
        first_name = findViewById(R.id.FirstName);
        last_name = findViewById(R.id.LastName);
        dob = findViewById(R.id.DateOfBirth);

        //Onclick listener for calculation button
        calculate_butt.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                String firstNameInput = first_name.getText().toString();
                String lastNameInput = last_name.getText().toString();
                String dateInput = dob.getText().toString();

                //Convert the date string to a Date object. Allow its own date checking functions to help
                Date dob = stringToDate(dateInput, "MM/dd/yyyy");

                //Null when ever given an invalid date. Do not continue
                if(dob == null){
                    Toast myToast = Toast.makeText(view.getContext(), getString(R.string.invalid_date_of_birth), Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else{
                    //Non-null date, so convert to a LocalDate for calculations
                    LocalDate dob_local = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    //Get the current date
                    LocalDate currentDate = LocalDate.now();

                    //print statements for debugging. Check logcat!
                    System.out.println("First Name given: " + firstNameInput);
                    System.out.println("Last Name given: " + lastNameInput);
                    System.out.println("DOB given: " + dob);
                    System.out.println("Current date: " + currentDate);

                    //Validate other input. Show a toast and stop for any invalid input
                    if (dob_local.isAfter(currentDate)){
                        Toast myToast = Toast.makeText(view.getContext(), getString(R.string.dob_after_today_error), Toast.LENGTH_SHORT);
                        myToast.show();
                    }
                    else if (firstNameInput == null || firstNameInput.equals(getString(R.string.first_name))) { //must change the default text
                        Toast myToast = Toast.makeText(view.getContext(), getString(R.string.please_enter_a_first_name), Toast.LENGTH_SHORT);
                        myToast.show();
                    }
                    else if (lastNameInput == null || lastNameInput.equals(getString(R.string.last_name))) { //must change the default text
                        Toast myToast = Toast.makeText(view.getContext(), getString(R.string.please_enter_a_last_name), Toast.LENGTH_SHORT);
                        myToast.show();
                    }
                    else{ //valid inputs!
                        //Calculate their age!
                        Integer year_diff =  Period.between(dob_local, currentDate).getYears();
                        Integer month_diff =  Period.between(dob_local, currentDate).getMonths();
                        Integer day_diff =  Period.between(dob_local, currentDate).getDays();

                        //Display age in a Toast
                        String text_to_toast = firstNameInput + " " + lastNameInput + ", you are " + year_diff + " years, " + month_diff + " months, and " + day_diff + " days old!";
                        Toast myToast = Toast.makeText(view.getContext(), text_to_toast , Toast.LENGTH_LONG);
                        myToast.show();
                    }
                }
            }
        });
    }

    //Create a Date object representative of the input string and format
    private Date stringToDate(String aDate,String aFormat) {
        if(aDate==null) return null;

        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);

        return simpledateformat.parse(aDate, pos);
    }
}