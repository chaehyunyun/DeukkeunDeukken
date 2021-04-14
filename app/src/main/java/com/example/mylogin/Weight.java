package com.example.mylogin;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Weight extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weight);


        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new fragment_weight());
        fragmentTransaction.commit();

        FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
        fragmentTransaction2.replace(R.id.bottomFrameLayout, new fragment_cart()).commit();

    }

    //이미지뷰 눌리면 프래그먼트 변경하기
    public void clickHandler(View view)
    {
        transaction = fragmentManager.beginTransaction();

        switch(view.getId())
        {
            case R.id.weightIV:
                transaction.replace(R.id.frameLayout, new fragment_weight()).commitAllowingStateLoss();
                break;
            case R.id.cardioIV:
                transaction.replace(R.id.frameLayout, new fragment_cardio()).commitAllowingStateLoss();
                break;
            case R.id.partIV:
                transaction.replace(R.id.frameLayout, new fragment_part()).commitAllowingStateLoss();
                break;

        }
    }



}
