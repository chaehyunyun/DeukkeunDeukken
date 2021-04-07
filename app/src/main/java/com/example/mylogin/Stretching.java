package com.example.mylogin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Stretching extends AppCompatActivity {

    Button morning;
    Button before;
    Button after;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stretching);

        morning = (Button) findViewById(R.id.morning);
        before = (Button) findViewById(R.id.before);
        after = (Button) findViewById(R.id.after);

        morning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=%EC%95%84%EC%B9%A8+%EC%8A%A4%ED%8A%B8%EB%A0%88%EC%B9%AD"));
                startActivity(intent);
                finish();
            }
        });

        before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=%EC%9A%B4%EB%8F%99+%EC%A0%84+%EC%8A%A4%ED%8A%B8%EB%A0%88%EC%B9%AD"));
                startActivity(intent);
                finish();
            }
        });

        after.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=%EC%9A%B4%EB%8F%99+%ED%9B%84+%EC%8A%A4%ED%8A%B8%EB%A0%88%EC%B9%AD"));
                startActivity(intent);
                finish();
            }
        });

    }
}
