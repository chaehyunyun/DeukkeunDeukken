package com.example.mylogin;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Exercise_Start extends AppCompatActivity {
    ImageView back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_start);

        back = findViewById(R.id.back);
        // Back button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        VideoView Video1 = (VideoView) findViewById(R.id.startEx_vv);
        Uri video = Uri.parse("android.resource://" + getPackageName() +"/"+R.raw.burpi);

        MediaController mediacontroller = new MediaController(this);
        Video1.setMediaController(mediacontroller);
        Video1.requestFocus();
        Video1.setVideoURI(video);
        Video1.start();

        //onPrepareListener는 vv.start() 후에 호출됩니다.
        Video1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });



        //
        TextView textview_second = (TextView) findViewById(R.id.second);
        String stringSec = textview_second.getText().toString();

        TextView textview_set = (TextView) findViewById(R.id.setCount);
        String stringSet = textview_set.getText().toString();

        //stringSec = "000010";
        countDown(stringSec, stringSet);




        //stringSec.setText




    } //onCreate end



    public void countDown(String time, String set) {

        long conversionTime = 0;
        String originalTime = time;
        int setCount = Integer.parseInt(set);

        // 1000 단위가 1초
        // 60000 단위가 1분
        // 60000 * 3600 = 1시간

        TextView textview_second = (TextView) findViewById(R.id.second);
        TextView textview_set = (TextView) findViewById(R.id.setCount);


        // "00"이 아니고, 첫번째 자리가 0 이면 제거
        if (time.substring(0, 1) == "0") {
            time = time.substring(1, 2);
        }

        // 변환시간
        conversionTime = Long.valueOf(time) * 1000;

        // 첫번쨰 인자 : 원하는 시간 (예를들어 30초면 30 x 1000(주기))
        // 두번쨰 인자 : 주기( 1000 = 1초)
        new CountDownTimer(conversionTime, 1000) {

            // 특정 시간마다 뷰 변경
            public void onTick(long time) {

                // 초단위
                String second = String.valueOf((time % (60 * 1000)) / 1000); // 나머지

                // 초가 한자리면 0을 붙인다
                if (second.length() == 1) {
                    second = "0" + second;
                }

                textview_second.setText(second);
            }

            // 제한시간 종료시
            public void onFinish() {

                // 변경 후
                //세트 숫자 줄어들게 하는거 (숫자가 0이 되면 다음 운동으로 넘어가게)
                textview_set.setText(String.valueOf(setCount-1));

                countDown(originalTime, String.valueOf(setCount-1));

            }
        }.start();

    }
}