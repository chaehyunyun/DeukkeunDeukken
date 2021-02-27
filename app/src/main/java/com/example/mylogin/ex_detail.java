package com.example.mylogin;


import android.media.MediaPlayer;
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

        //뒤로가기 누르면 main으로 돌아가기
        ActionBar actionBar =getSupportActionBar();
        //액션바 제목설정 actionBar.setTitle("안녕하세요");
        actionBar.setDisplayHomeAsUpEnabled(true);


        vv= findViewById(R.id.vv);
        btnStart = (Button) findViewById(R.id.btnPlay);
        btnStop = (Button) findViewById(R.id.btnStop);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartButton();
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StopButton();
            }
        });

        //미디어컨트롤러 추가하는 부분
        MediaController controller = new MediaController(ex_detail.this);
        vv.setMediaController(controller);

        //비디오뷰 포커스를 요청함
        vv.requestFocus();


        //Video Uri
        //Uri videoUri= Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");

        //내 res 폴더 안에 동영상이 있을 경우
        Uri videoUri=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.squat);


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
                //vv.start();
            }
        });

    }//onCreate ..

    //화면에 안보일때...
    @Override
    protected void onPause() {
        super.onPause();

        //비디오 일시 정지
        if(vv!=null && vv.isPlaying()) vv.pause();
    }
    //액티비티가 메모리에서 사라질때..
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //
        if(vv!=null) vv.stopPlayback();
    }


    //시작 버튼 onClick Method
    public void StartButton() {
        //비디오를 처음부터 재생할 때 0으로 시작(파라메터 sec)
        //vv.seekTo(0);
        vv.start();
    }

    //정지 버튼 onClick Method
    public void StopButton() {
        //비디오 재생 잠시 멈춤
        vv.pause();
    }

}