package com.example.mylogin;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ex_detail extends AppCompatActivity {
    VideoView vv;
    Button btnStart, btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex_detail);

        //Click using the action bar to return to the previous window.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //액션바 제목설정 actionBar.setTitle("안녕하세요");

        vv= findViewById(R.id.vv);
        btnStart = (Button) findViewById(R.id.btnPlay);
        btnStop = (Button) findViewById(R.id.btnStop);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vv.start();
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vv.pause();
            }
        });

        //미디어컨트롤러 추가하는 부분
        MediaController controller = new MediaController(ex_detail.this);
        vv.setMediaController(controller);

        //비디오뷰 포커스를 요청함
        vv.requestFocus();

        //Video Uri
        //Put the video in the res folder into a variable.
        Uri videoUri=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.squat);

        //비디오뷰의 재생, 일시정지 등을 할 수 있는 '컨트롤바'를 붙여주는 작업
        vv.setMediaController(new MediaController(this));

        //VideoView가 보여줄 동영상의 경로 주소(Uri) 설정하기
        //Set the path-address(Uri) for the video you want VideoView to show
        vv.setVideoURI(videoUri);
        vv.start();


    }//onCreate ..


}