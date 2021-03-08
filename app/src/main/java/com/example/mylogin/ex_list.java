package com.example.mylogin;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
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


    private RecyclerAdapter adapter;
    ItemTouchHelper helper;
    int count1 = 0;
    int count2 = 0;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex_list);
        init();

        getData();




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


    }



    private void init() {

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
        helper = new ItemTouchHelper(new ItemTouchHelperCallback(adapter));
        helper.attachToRecyclerView(recyclerView);
    }

    private void getData() {
        // 임의의 데이터입니다.

        List<Integer> listResId = Arrays.asList(
                R.drawable.lunge,R.drawable.squat,R.drawable.squat,R.drawable.squat,R.drawable.lunge,R.drawable.squat,R.drawable.squat,R.drawable.squat,R.drawable.lunge,R.drawable.squat,R.drawable.squat,R.drawable.squat
        );
        List<Integer> listBtnId= Arrays.asList(
                R.drawable.logo,R.drawable.logo,R.drawable.logo,R.drawable.logo,R.drawable.logo,R.drawable.logo,R.drawable.logo,R.drawable.logo,R.drawable.logo,R.drawable.logo,R.drawable.logo,R.drawable.logo
        );
        for (int i = 0; i < listResId.size(); i++) {
            // 각 List의 값들을 data 객체에 set 해줍니다.
            Data data = new Data();

            data.setResId(listResId.get(i));
            data.setBtnId(listBtnId.get(i));
            // 각 값이 들어간 data를 adapter에 추가합니다.
            adapter.addItem(data);
        }

        // adapter의 값이 변경되었다는 것을 알려줍니다.
        adapter.notifyDataSetChanged();
    }
}

