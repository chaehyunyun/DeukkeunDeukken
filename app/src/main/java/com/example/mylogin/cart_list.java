package com.example.mylogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class cart_list extends AppCompatActivity {

    private Adapter_cart adapter;
    private Adapter_cart count_up_adapter;
    private Adapter_cart count_down_adapter;
    private Adapter_cart set_up_adapter;
    private Adapter_cart set_down_adapter;
    private Adapter_cart textCount_adapter;
    private Adapter_cart textSet_adapter;

    ItemTouchHelper helper;
    int count1 = 0;
    int count2 = 0;

    ImageButton btn_count_up, btn_count_down, btn_set_up, btn_set_down;
    TextView textView_count, textView_set;
    int count = 0;
    int set = 0;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_list);
        init();

        getData();

        //운동시작 누르면 Exercise 로
        ImageView start = (ImageView) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cart_list.this, Exercise.class);
                startActivity(intent);
            }
        });

        //하단바
        ImageView home = (ImageView) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cart_list.this, home.class);
                startActivity(intent);
            }
        });

        ImageView scale = (ImageView) findViewById(R.id.scale);
        scale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cart_list.this, BodyProfile.class);
                startActivity(intent);
            }
        });

        ImageView memo = (ImageView) findViewById(R.id.calendar);
        memo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cart_list.this, Calendar.class);
                startActivity(intent);
            }
        });

        ImageView mypage = (ImageView) findViewById(R.id.mypage);
        mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cart_list.this, Mypage.class);
                startActivity(intent);
            }
        });

        //횟수 및 세트 조절
        adapter.setOnItemClickListener(new Adapter_cart.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if(v.getId()==R.id.btn_count_down)
                {
                    count--;
                    //textView_count.setText(Integer.toString(count));
                    textView_count.setText(Integer.toString(count));
                }
                else if(v.getId()==R.id.btn_count_up)
                {
                    count++;
                    //textView_count.setText(Integer.toString(count));
                }
                else if(v.getId()==R.id.btn_set_down)
                {
                    set--;
                    //textView_set.setText(Integer.toString(set));
                    textView_count.setText(Integer.toString(count));
                }
                else if(v.getId()==R.id.btn_set_down)
                {
                    set++;
                    textView_set.setText(Integer.toString(set));
                }
                else if(v.getId()==R.id.btn_set_up)
                {
                    set++;
                    //textView_count.setText(Integer.toString(set));
                    textView_count.setText(Integer.toString(set));
                }
            }
        });

        /*//횟수 및 세트 조절
        textView_count = findViewById(R.id.textView_count);
        textView_set = findViewById(R.id.textView_set);

        btn_count_up = findViewById(R.id.btn_count_up);
        btn_count_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = count + 1;
                textView_count.setText(Integer.toString(count));
            }
        });
        btn_count_down = findViewById(R.id.btn_count_down);
        btn_count_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count > 0) {
                    count = count - 1;
                    textView_count.setText(Integer.toString(count));
                }
            }
        });
        btn_set_up = findViewById(R.id.btn_set_up);
        btn_set_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set = set + 1;
                textView_set.setText(Integer.toString(set));
            }
        });
        btn_set_down = findViewById(R.id.btn_set_down);
        btn_set_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (set > 0) {
                    set = set - 1;
                    textView_set.setText(Integer.toString(set));
                }
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
            data.setCount(count);
            data.setSet(set);
            // 각 값이 들어간 data를 adapter에 추가합니다.
            adapter.addItem(data);
        }



        // adapter의 값이 변경되었다는 것을 알려줍니다.
        adapter.notifyDataSetChanged();
    }
}

