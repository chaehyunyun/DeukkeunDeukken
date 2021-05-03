package com.example.mylogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class cart_list extends AppCompatActivity {


    private Adapter_cart adapter;
    ItemTouchHelper helper;
    int count1 = 0;
    int count2 = 0;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_list);
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

        adapter = new Adapter_cart();
        recyclerView.setAdapter(adapter);
        helper = new ItemTouchHelper(new ItemTouchHelperCallback(adapter));
        helper.attachToRecyclerView(recyclerView);
    }

    private void getData() {
        List<Integer> listRes = new ArrayList<>();
        int resID = getResources().getIdentifier("@drawable/leftright", "drawable", this.getPackageName());
        listRes.add(resID);

        for (int i = 0; i < listRes.size(); i++) {
            // 각 List의 값들을 data 객체에 set 해줍니다.
            Data data = new Data();
            data.setIndex(i+1);
            data.setResId(listRes.get(i));
            // 각 값이 들어간 data를 adapter에 추가합니다.
            adapter.addItem(data);
        }

        // adapter의 값이 변경되었다는 것을 알려줍니다.
        adapter.notifyDataSetChanged();
    }
}

