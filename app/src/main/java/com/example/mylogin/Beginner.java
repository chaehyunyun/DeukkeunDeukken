package com.example.mylogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Beginner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beginner);
    }

    public void viewRoutine(View view) {
        Intent intent = new Intent(getApplicationContext(), Beginner_Routine.class);
        startActivity(intent);
    }
}
