package com.example.mylogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class home extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        //웨이트 이미지버튼
        //home에서 Weight으로 연결
        ImageButton btn1 = (ImageButton) findViewById(R.id.homeweight);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, Weight.class);
                startActivity(intent);
                finish();
            }
        });

        //스트레칭 이미지버튼
        //home에서 Stretching으로 연결
        ImageButton stretching=(ImageButton)findViewById(R.id.homestretching);
        stretching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, Stretching.class);
                startActivity(intent);
                finish();
            }
        });

        //하단바
        //home에서 mypage으로 연결
        ImageView mypage=(ImageView)findViewById(R.id.mypage);
        mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, Mypage.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
