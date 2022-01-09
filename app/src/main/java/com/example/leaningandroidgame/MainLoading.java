package com.example.leaningandroidgame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;


public class MainLoading extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hiding the margins of the activity
        getSupportActionBar().hide();
        //Hiding the system bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //In this instance the handler is used to set the delay for the load time
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //Creating the instance of the new activity
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        }, 4000); //delay got set

    }

}
