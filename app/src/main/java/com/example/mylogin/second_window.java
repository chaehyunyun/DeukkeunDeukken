package com.example.mylogin;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class second_window extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    ScrollView scroll;
    BitmapDrawable bitmap;
    private TextView tv;
    private CheckBox cb1;
    private CheckBox cb2;
    int [] ImageId = { R.drawable.lunge_unchecked, R.drawable.lunge_checked};
    ImageView iv;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_window);
        cb1 = (CheckBox)findViewById(R.id.ex1);
        cb2 = (CheckBox)findViewById(R.id.ex2);
        tv = (TextView)findViewById(R.id.tv1);
        cb1.setOnCheckedChangeListener(this);
        cb2.setOnCheckedChangeListener(this);
        ImageView v1 = (ImageView) findViewById(R.id.v1);
        ImageView cart = (ImageView) findViewById(R.id.cart);
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(second_window.this, ex_detail.class);
                startActivity(intent);
                finish();
            }
        });

        //파이어베이스 데이터베이스에 올리기
        cart.setOnClickListener(new View.OnClickListener() {
            String msg;
            @Override
            public void onClick(View v) {
                if(cb1.isChecked()) {
                    msg ="런지1";
                    databaseReference.child("User").push().setValue(msg);
                }
                if(cb2.isChecked()) {
                    msg ="런지2";
                    databaseReference.child("User").push().setValue(msg);
                }
            }
        });
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // 체크박스를 클릭해서 상태가 바꾸었을 경우 호출되는 콜백 메서드

        String result = ""; // 문자열 초기화는 빈문자열로 하자

//        if(isChecked) tv.setText("체크했음");
//        else tv.setText("체크안했슴");

        // 혹은 3항연산자
        //tx.setText(isChecked?"체크했슴":"체크안했뜸");

        if(cb1.isChecked())
        {result = result + cb1.getText().toString() + "런지1\n";
        }
        if(cb2.isChecked())
        {result = result + cb2.getText().toString() + "런지2\n";
        }

        tv.setText("담은 항목\n" + result);
    }

}
