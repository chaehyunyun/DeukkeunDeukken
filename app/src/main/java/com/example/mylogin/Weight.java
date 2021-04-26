package com.example.mylogin;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Weight extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private Fragment fw, fc, fp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weight);

        fragmentManager = getSupportFragmentManager();

        //When you click on the weight ImageButton, you can see weight exercises first.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new fragment_weight());
        fragmentTransaction.commit();

        //Set up a fragment to show the selected exercises.
        FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
        fragmentTransaction2.replace(R.id.bottomFrameLayout, new fragment_cart()).commit();


        //Change Fragment When ImageView is Clicked
        ImageView weightIV = (ImageView) findViewById(R.id.weightIV);
        weightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fw == null) {
                    fw = new fragment_weight();
                    fragmentManager.beginTransaction().add(R.id.frameLayout, fw).commit();
                }

                //If it's a fragment that's already been made, it'll keep it in shape.
                if(fw != null) fragmentManager.beginTransaction().show(fw).commit();
                if(fc != null) fragmentManager.beginTransaction().hide(fc).commit();
                if(fp != null) fragmentManager.beginTransaction().hide(fp).commit();
            }
        });

        ImageView cardioIV = (ImageView) findViewById(R.id.cardioIV);
        cardioIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fc == null) {
                    fc = new fragment_cardio();
                    fragmentManager.beginTransaction().add(R.id.frameLayout, fc).commit();
                }
                if(fw != null) fragmentManager.beginTransaction().hide(fw).commit();
                if(fc != null) fragmentManager.beginTransaction().show(fc).commit();
                if(fp != null) fragmentManager.beginTransaction().hide(fp).commit();

            }
        });

        ImageView partIV = (ImageView) findViewById(R.id.partIV);
        partIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fp == null) {
                    fp = new fragment_part();
                    fragmentManager.beginTransaction().add(R.id.frameLayout, fp).commit();
                }
                if(fw != null) fragmentManager.beginTransaction().hide(fw).commit();
                if(fc != null) fragmentManager.beginTransaction().hide(fc).commit();
                if(fp != null) fragmentManager.beginTransaction().show(fp).commit();

            }
        });

    }







}
