package com.example.mylogin;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class fragment_whole extends AppCompatActivity {

    VideoView vv;
    private FragmentManager fragmentManager;
    private Fragment fw, fc, fp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_whole);


        vv= findViewById(R.id.videoView2);
        //Video Uri
        Uri videoUri= Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.muscle1);

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

        fragmentManager = getSupportFragmentManager();

        //When you click on the weight ImageButton, you can see weight exercises first.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //앞 액티비티에서 보낸 번들을 받음
        Intent passedIntent = getIntent();
        if(passedIntent!=null) {
            Bundle bundle = getIntent().getExtras();
            String whatbtn = "";
            whatbtn = bundle.getString("whatbtn");

            if(whatbtn.equals("btn1")) {
                fragmentTransaction.replace(R.id.frameLayout, new fragment_weight());
                fragmentTransaction.commit();
            }
            if(whatbtn.equals("btn2")) {
                fragmentTransaction.replace(R.id.frameLayout, new fragment_cardio());
                fragmentTransaction.commit();
            }
            if(whatbtn.equals("btn3")) {
                fragmentTransaction.replace(R.id.frameLayout, new fragment_part());
                fragmentTransaction.commit();
            }
        }


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

        //하단바
//        ImageView home = (ImageView) findViewById(R.id.home);
//        home.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), home.class);
//                startActivity(intent);
//            }
//        });
//
//        ImageView scale = (ImageView) findViewById(R.id.scale);
//        scale.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), BodyProfile.class);
//                startActivity(intent);
//            }
//        });
//
//        ImageView memo = (ImageView) findViewById(R.id.calendar);
//        memo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), Calendar.class);
//                startActivity(intent);
//            }
//        });
//
//        ImageView mypage = (ImageView) findViewById(R.id.mypage);
//        mypage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), Mypage.class);
//                startActivity(intent);
//            }
//        });

    } //onCreate end

}
