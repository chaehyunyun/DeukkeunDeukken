package com.example.mylogin;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class ex_detail extends AppCompatActivity {
    VideoView vv;
    Uri videoUri;
    Button btnStart, btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex_detail);

        ImageView ex_imageView = (ImageView) findViewById(R.id.ex_imageView);
        String resName = "";
        String weight_ex, cardio_ex, part_ex = "";

        //weight 액티비티에서 보낸 번들을 받음
        Intent intent = getIntent();
        Bundle bundle_ex = intent.getExtras();

        String type = bundle_ex.getString("Type");

        //Toast.makeText(getApplicationContext(), type, Toast.LENGTH_SHORT).show();


        if (intent != null) {
            //weight
            if (type.equals("weight")) {
                weight_ex = bundle_ex.getString("weight_ex");

                if (weight_ex.equals("weight_iv1")) { //스쿼트
                    resName = "@drawable/squat_detail";
                    //Video Uri, Put the video in the res folder into a variable.
                    videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.squat_2);
                }
                if (weight_ex.equals("iv2")) { //마운틴 클라이머

                }
                if (weight_ex.equals("iv3")) { //리버스 크런치

                }
                if (weight_ex.equals("iv4")) { //백 런지

                }

            }

            //cardio
            if (type.equals("cardio")) {
                cardio_ex = bundle_ex.getString("cardio_ex");

                if (cardio_ex.equals("cardio_iv1")) { //버피
                    resName = "@drawable/burpi_detail";
                    //Video Uri, Put the video in the res folder into a variable.
                    videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.burpi);
                }
                if (cardio_ex.equals("iv2")) { //좌우뛰기

                }
                if (cardio_ex.equals("iv3")) { //머리 위로 박수치기

                }
                if (cardio_ex.equals("iv4")) { //하이니즈

                }
            }

            //part-exercise
            if (type.equals("part")) {
                part_ex = bundle_ex.getString("part_ex");

                if (part_ex.equals("part_iv1")) { //스탠딩내전근
                    resName = "@drawable/standinginner_detail";
                    //Video Uri, Put the video in the res folder into a variable.
                    videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.standinginner);
                }
                if (part_ex.equals("iv2")) { //브릿지

                }
                if (part_ex.equals("iv3")) { //옆으로 누워 다리 올리기

                }
                if (part_ex.equals("iv4")) { //벽 푸시업

                }
            }
        }

        //해당하는 이미지뷰로 셋해주기
        int iResId = getResources().getIdentifier(resName, "drawable", this.getPackageName());
        ex_imageView.setImageResource(iResId);

        //해당하는 비디오로 셋해주기
        vv = findViewById(R.id.vv);
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

        MediaController controller = new MediaController(ex_detail.this);
        vv.setMediaController(controller);
        vv.requestFocus();


        //ControllerBar 비디오뷰의 재생, 일시정지 등을 할 수 있는 '컨트롤바'를 붙여주는 작업
        vv.setMediaController(new MediaController(this));
        vv.requestFocus();

        //Set the path-address(Uri) for the video you want VideoView to show
        vv.setVideoURI(videoUri);
        vv.start();


        //onPrepareListener는 vv.start() 후에 호출됩니다.
        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });


    }//onCreate ..


}