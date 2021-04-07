package com.example.mylogin;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class fragment_weight extends Fragment implements CompoundButton.OnCheckedChangeListener {
    ScrollView scroll;
    BitmapDrawable bitmap;
    private TextView tv;
    private CheckBox cb1;
    private CheckBox cb2;
    int [] ImageId = { R.drawable.lunge_unchecked, R.drawable.lunge_checked};
    ImageView iv;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_weight, container, false);

        //운동을 누르면 운동 상세 페이지로 이동
        ImageView v1 = (ImageView) rootView.findViewById(R.id.v1);
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ex_detail.class);
                startActivity(intent);
            }
        });

        cb1 = (CheckBox)rootView.findViewById(R.id.ex1);
        cb2 = (CheckBox)rootView.findViewById(R.id.ex2);
        cb1.setOnCheckedChangeListener(this);
        cb2.setOnCheckedChangeListener(this);


        tv = (TextView)rootView.findViewById(R.id.tv1);






        return rootView;

    } //onCreateView 끝



    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // 체크박스를 클릭해서 상태가 바꾸었을 경우 호출되는 콜백 메서드

        String result = ""; // 문자열 초기화는 빈문자열로 하자

//        if(isChecked) tv.setText("체크했음");
//        else tv.setText("체크안했슴");

        // 혹은 3항연산자
        //tx.setText(isChecked?"체크했슴":"체크안했뜸");

        if(cb1.isChecked()) {
            result = result + cb1.getText().toString() + "런지1\n";
        }
        if(cb2.isChecked()) {
            result = result + cb2.getText().toString() + "런지2\n";
        }

        tv.setText("담은 항목\n" + result);
    }

}