package com.example.mylogin;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class fragment_weight extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private CheckBox cb1;
    private CheckBox cb2;
    int [] ImageId = { R.drawable.lunge_unchecked, R.drawable.lunge_checked};
    ImageView iv;


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weight, container, false);


        //운동을 누르면 운동 상세 페이지로 이동
        ImageView v1 = (ImageView) view.findViewById(R.id.v1);
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ex_detail.class);
                startActivity(intent);
            }
        });


        cb1 = (CheckBox)view.findViewById(R.id.ex1);
        cb2 = (CheckBox)view.findViewById(R.id.ex2);
        cb1.setOnCheckedChangeListener(this);
        cb2.setOnCheckedChangeListener(this);


        return view;

    } //onCreateView 끝



    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // 체크박스를 클릭해서 상태가 바꾸었을 경우 호출되는 콜백 메서드

        firebaseDatabase.getReference().child("fragment_weight").removeValue();

        String name = "";
        adapter = new Adapter();

        if(cb1.isChecked()) {
            name+=cb1.getText().toString()+"런지1\n";
        }

        if(cb2.isChecked()) {
            name+=cb2.getText().toString()+"런지2\n";
        }

        databaseReference.child("fragment_weight").push().setValue(name);
        adapter.addItem(new ExerciseItem(name));
        adapter.notifyDataSetChanged();

    }


    class Adapter extends BaseAdapter {
        ArrayList<ExerciseItem> items = new ArrayList<ExerciseItem>();


        // Generate > implement methods
        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(ExerciseItem item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 뷰 객체 재사용
            ExerciseItemView view = null;
            if (convertView == null) {
                view = new ExerciseItemView(getActivity().getApplicationContext());
            } else {
                view = (ExerciseItemView) convertView;
            }

            ExerciseItem item = items.get(position);

            view.setName(item.getName());


            return view;
        }

    }


}