package com.example.mylogin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class fragment_cardio extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private CheckBox cb1, cb2, cb3, cb4;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private DatabaseReference index = firebaseDatabase.getReference("index");
    Adapter adapter;
    String cardio_ex ="";
    String cardio = "cardio";
    int num = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //앱을 껐다가 다시 접속했을 때 전에 올려뒀던 파이어베이스를 가져오기 때문에
        //파이어베이스를 초기화해줌

        View view = inflater.inflate(R.layout.fragment_cardio, container, false);

        //If you click the exercise picture, you can move to the exercise detail page.
        //버피
        ImageView iv1 = (ImageView) view.findViewById(R.id.iv1);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //어떤 exercise가 눌렸는지 번들에 담아서 보내줌
                Bundle bundle_ex = new Bundle();
                cardio_ex = "cardio_iv1";
                bundle_ex.putString("Type", cardio);
                bundle_ex.putString("cardio_ex", cardio_ex);

                Intent intent = new Intent(getActivity(), ex_detail.class);
                intent.putExtras(bundle_ex);
                startActivity(intent);
            }
        });

        cb1 = (CheckBox) view.findViewById(R.id.ex1);
        cb2 = (CheckBox) view.findViewById(R.id.ex2);
        cb3 = (CheckBox) view.findViewById(R.id.ex3);
        cb4 = (CheckBox) view.findViewById(R.id.ex4);
        cb1.setOnCheckedChangeListener(this);
        cb2.setOnCheckedChangeListener(this);
        cb3.setOnCheckedChangeListener(this);
        cb4.setOnCheckedChangeListener(this);

        return view;
    } //onCreateView 끝

    //A 'Callback' method called when the State of CheckBox changes by clicking
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // 체크박스를 클릭해서 상태가 바꾸었을 경우 호출되는 콜백 메서드

        //Delete the current exercise list in the real-time database
        // because the exercise list has been updated.




        adapter = new fragment_cardio.Adapter();
        cb1.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    databaseReference.child("fragment_ExList").child("burpi").setValue("burpi");
                    databaseReference.child("fragment_ExList2").child("burpi").setValue("버피");
                    String name = "";
                    name ="burpi";
                    adapter.addItem(new ExerciseItem(name));
                    adapter.notifyDataSetChanged();
                } else
                {
                    DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference()
                            .child("fragment_ExList2").child("burpi");
                    mPostReference.removeValue();
                    DatabaseReference mPostReference2 = FirebaseDatabase.getInstance().getReference()
                            .child("fragment_ExList").child("burpi");
                    mPostReference2.removeValue();
                    adapter.notifyDataSetChanged();

                }
            }
        }) ;
        cb2.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    databaseReference.child("fragment_ExList").child("leftright").setValue("leftright");
                    databaseReference.child("fragment_ExList2").child("leftright").setValue("좌우 뛰기");
                    String name = "";
                    name ="leftright";
                    adapter.addItem(new ExerciseItem(name));
                    adapter.notifyDataSetChanged();
                } else
                {
                    DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference()
                            .child("fragment_ExList2").child("leftright");
                    mPostReference.removeValue();
                    DatabaseReference mPostReference2 = FirebaseDatabase.getInstance().getReference()
                            .child("fragment_ExList").child("leftright");
                    mPostReference2.removeValue();
                    adapter.notifyDataSetChanged();

                }
            }
        }) ;
        cb3.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    databaseReference.child("fragment_ExList").child("headclap").setValue("headclap");
                    databaseReference.child("fragment_ExList2").child("headclap").setValue("머리 위로 박수치기");
                    String name = "";
                    name ="headclap";
                    adapter.addItem(new ExerciseItem(name));
                    adapter.notifyDataSetChanged();
                } else
                {
                    DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference()
                            .child("fragment_ExList2").child("headclap");
                    mPostReference.removeValue();
                    DatabaseReference mPostReference2 = FirebaseDatabase.getInstance().getReference()
                            .child("fragment_ExList").child("headclap");
                    mPostReference2.removeValue();
                    adapter.notifyDataSetChanged();

                }
            }
        }) ;
        cb4.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    databaseReference.child("fragment_ExList").child("highknees").setValue("highknees");
                    databaseReference.child("fragment_ExList2").child("highknees").setValue("하이니즈");
                    String name = "";
                    name ="highknees";
                    adapter.addItem(new ExerciseItem(name));
                    adapter.notifyDataSetChanged();
                } else
                {
                    DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference()
                            .child("fragment_ExList2").child("highknees");
                    mPostReference.removeValue();
                    DatabaseReference mPostReference2 = FirebaseDatabase.getInstance().getReference()
                            .child("fragment_ExList").child("highknees");
                    mPostReference2.removeValue();
                    adapter.notifyDataSetChanged();

                }
            }
        });

        //Push() the updated exercise list to the real-time database.


        adapter.notifyDataSetChanged();

    }

    //'Adapter class' acting as data management
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
            //Reuse View Objects
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