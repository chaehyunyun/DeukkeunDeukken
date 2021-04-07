package com.example.mylogin;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Weight extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private fragment_weight fragment1;
    private fragment_cardio fragment2;
    private fragment_part fragment3;
    private Object ex_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weight);


        //상단바?! 프래그먼트로 만들기
        fragmentManager = getSupportFragmentManager();
        fragment1 = new fragment_weight();
        fragment2 = new fragment_cardio();
        fragment3 = new fragment_part();

        transaction = fragmentManager.beginTransaction();
        //처음에는 weight 화면 띄우기
        transaction.replace(R.id.frameLayout, fragment1).commitAllowingStateLoss();

    }

    //이미지뷰 눌리면 프래그먼트 변경하기
    public void clickHandler(View view)
    {
        transaction = fragmentManager.beginTransaction();

        switch(view.getId())
        {
            case R.id.weightIV:
                transaction.replace(R.id.frameLayout, fragment1).commitAllowingStateLoss();
                break;
            case R.id.cardioIV:
                transaction.replace(R.id.frameLayout, fragment2).commitAllowingStateLoss();
                break;
            case R.id.partIV:
                transaction.replace(R.id.frameLayout, fragment3).commitAllowingStateLoss();
                break;

        }
    }



}
