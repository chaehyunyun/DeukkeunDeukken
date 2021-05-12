package com.example.mylogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class cart_list extends AppCompatActivity implements Adapter_cart.OnItemClickListener {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private Adapter_cart adapter;
    private ArrayList<Data> ex_list = new ArrayList<>();

    Data data;

    ItemTouchHelper helper;

    TextView textView_count, textView_set;
    VideoView vv;
    ImageView back;

    List<Integer> listRes = new ArrayList<>();

    int count = 0;
    int set = 0;
    int resID;
    String msg;
    String[] array;
    String packName;
    int[] count2 = new int[20];
    int[] set2 = new int[20];

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_list);
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("fragment_ExList");
        packName = this.getPackageName();

        for(int i=0; i<20; i++)
        {

            if(i==0)
            {
                count2[i]=0;
                set2[i]=0;
            }
            else
            {
                count2[i]=0;
                set2[i]=0;
            }

        }

        init();

        vv = findViewById(R.id.videoView3);
        //Video Uri
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.muscle1);

        //비디오뷰의 재생, 일시정지 등을 할 수 있는 '컨트롤바'를 붙여주는 작업
        vv.setMediaController(new MediaController(this));

        //VideoView가 보여줄 동영상의 경로 주소(Uri) 설정하기
        vv.setVideoURI(videoUri);

        //동영상을 읽어오는데 시간이 걸리므로..
        //비디오 로딩 준비가 끝났을 때 실행하도록..
        //리스너 설정
        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                //비디오 시작
                vv.start();
            }
        });

        back = findViewById(R.id.back);
        // Back button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


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



        adapter.setOnItemClickListener(this);

        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Repeat as much as the data within the 'child'
                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    msg = messageData.getValue().toString();
                }

                array = msg.split("\n");
                for (int i = 0; i < array.length; i++) {
                    resID = getResources().getIdentifier("@drawable/" + array[i], "drawable", packName);
                    listRes.add(resID);
                }

                /*
                for(int i=0; i<listRes.size(); i++) {

                    count2[i]=0;
                    set2[i]=0;
                }

                 */

                for (int i = 0; i < listRes.size(); i++) {
                    // 각 List의 값들을 data 객체에 set 해줍니다.
                    data = new Data();
                    data.setIndex(i+1);
                    data.setResId(listRes.get(i));
                    //ata.setCount(5);
                    //data.setSet(5);
                    //data.setCount(count2[i]);
                    //data.setSet(set2[i]);
                    // 각 값이 들어간 data를 adapter에 추가합니다.
                    ex_list.add(data);
                    adapter.addItem(ex_list.get(i));
                    //adapter.addItem(data);
                }

                // adapter의 값이 변경되었다는 것을 알려줍니다.
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //adapter.addItem(data);
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

    @Override
    public void onItemChanged() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(int position) {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCountUpClick(int position) {

        Data newData = ex_list.get(position);
        int newIndex = newData.getIndex();

        count2[newIndex-1]++;

        //count++;
        ex_list.get(position).setCount(count2[newIndex-1]);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCountDownClick(int position) {

        Data newData = ex_list.get(position);
        int newIndex = newData.getIndex();

        if(count2[newIndex-1]==0)
        {
            count2[newIndex-1]=0;
        }
        else
        {
            count2[newIndex-1]--;
        }

        //count--;
        //data.setCount(count);
        ex_list.get(position).setCount(count2[newIndex-1]);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSetUpClick(int position) {


        Data newData = ex_list.get(position);
        int newIndex = newData.getIndex();

        set2[newIndex-1]++;

        //count++;
        ex_list.get(position).setSet(set2[newIndex-1]);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSetDownClick(int position) {

        Data newData = ex_list.get(position);
        int newIndex = newData.getIndex();

        if(set2[newIndex-1]==0)
        {
            set2[newIndex-1]=0;
        }
        else
        {
            set2[newIndex-1]--;
        }

        //count--;
        //data.setCount(count);
        ex_list.get(position).setSet(set2[newIndex-1]);
        adapter.notifyDataSetChanged();
    }

    /*
    private void getData() {
        //A 'Listener' that run if there are changes to the entire path
        //A 'Listener' for putting values into list views when data has been added/changed
        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Repeat as much as the data within the 'child'
                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    msg = messageData.getValue().toString();
                }
                array = msg.split("\n");
                for (int i = 0; i < array.length; i++) {
                    resID = getResources().getIdentifier("@drawable/" + array[i], "drawable", packName);
                    listRes.add(resID);
                }
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

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/

}
