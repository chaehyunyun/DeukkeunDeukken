package com.example.mylogin;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.view.View;

import android.widget.Button;

import android.widget.TextView;

import java.util.ArrayList;

import android.os.Bundle;


import java.util.Arrays;
import java.util.List;

public class ex_list extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    int count1 = 0;
    int count2 = 0;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex_list);
        TextView tv1 = findViewById(R.id.v1_c1);
        ImageButton p1 = findViewById(R.id.v1_p1);
        ImageButton m1 = findViewById(R.id.v1_m1);
        TextView tv2 = findViewById(R.id.v1_c2);
        ImageButton p2 = findViewById(R.id.v2_p2);
        ImageButton m2 = findViewById(R.id.v2_m2);


    /*    p1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                count1++;
                tv1.setText("" + count1);
            }
        });
        m1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                count1--;
                tv1.setText("" + count1);
            }
        });
        p2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                count2++;
                tv2.setText("" + count2);
            }
        });
        m2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                count2--;
                tv2.setText("" + count2);
            }
        });*/
        recyclerView = findViewById(R.id.recycler_view);

        // 리사이클러뷰의 notify()처럼 데이터가 변했을 때 성능을 높일 때 사용한다.
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String[] textSet =  {"겸군님","티스토리","입니다","g-y-e-o-m.tistory.com"};
        int[] imgSet = {R.drawable.lunge,R.drawable.lunge,R.drawable.lunge,R.drawable.lunge};
        int[] btnSet = {R.drawable.logo,R.drawable.logo,R.drawable.logo,R.drawable.logo};

        // 어댑터 할당, 어댑터는 기본 어댑터를 확장한 커스텀 어댑터를 사용할 것이다.
        adapter = new MyAdapter(imgSet,btnSet);
        recyclerView.setAdapter(adapter);




    }



}

