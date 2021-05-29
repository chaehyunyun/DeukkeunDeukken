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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class fragment_weight extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private CheckBox cb1, cb2, cb3, cb4;
    int[] ImageId = {R.drawable.lunge_unchecked, R.drawable.lunge_checked};

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    Adapter adapter;

    String weight_ex = "";
    String weight = "weight";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //앱을 껐다가 다시 접속했을 때 전에 올려뒀던 파이어베이스를 가져오기 때문에
        //파이어베이스를 초기화해줌

        //테스트용


        View view = inflater.inflate(R.layout.fragment_weight, container, false);

        //If you click the exercise picture, you can move to the exercise detail page.
        //스쿼트
        ImageView iv1 = (ImageView) view.findViewById(R.id.iv1);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //어떤 exercise가 눌렸는지 번들에 담아서 보내줌
                Bundle bundle_ex = new Bundle();
                weight_ex = "weight_iv1";
                bundle_ex.putString("Type", weight);
                bundle_ex.putString("weight_ex", weight_ex);

                Intent intent = new Intent(getActivity(), ex_detail.class);
                intent.putExtras(bundle_ex);
                startActivity(intent);
            }
        });
        /*
        //마운틴 클라이머
        ImageView iv2 = (ImageView) view.findViewById(R.id.iv1);
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ex_detail.class);

                //어떤 exercise가 눌렸는지 번들에 담아서 보내줌
                Bundle bundle = new Bundle();
                weight_ex = "weight_iv2";
                bundle.putString("Type", weight);
                bundle.putString("weight_ex", weight_ex);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });*/
        //리버스 크런치


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


        adapter = new Adapter();
        cb1.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    databaseReference.child("fragment_ExList").child("squat").setValue("squat");
                    databaseReference.child("fragment_ExList2").child("squat").setValue("스쿼트");
                    String name = "";
                    name ="squat";
                    adapter.addItem(new ExerciseItem(name));
                    adapter.notifyDataSetChanged();
                } else
                {
                    DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference()
                            .child("fragment_ExList2").child("squat");
                    mPostReference.removeValue();
                    DatabaseReference mPostReference2 = FirebaseDatabase.getInstance().getReference()
                            .child("fragment_ExList").child("squat");
                    mPostReference2.removeValue();
                    adapter.notifyDataSetChanged();

                }
            }
        }) ;
        cb2.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    databaseReference.child("fragment_ExList").child("mountainclimber").setValue("mountainclimber");
                    databaseReference.child("fragment_ExList2").child("mountainclimber").setValue("마운틴 클라이머");
                    String name = "";
                    name ="mountainclimber";
                    adapter.addItem(new ExerciseItem(name));
                    adapter.notifyDataSetChanged();
                } else
                {
                    DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference()
                            .child("fragment_ExList2").child("mountainclimber");
                    DatabaseReference mPostReference2 = FirebaseDatabase.getInstance().getReference()
                            .child("fragment_ExList").child("mountainclimber");
                    mPostReference.removeValue();
                    mPostReference2.removeValue();
                    adapter.notifyDataSetChanged();

                }
            }
        }) ;
        cb3.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    databaseReference.child("fragment_ExList").child("reverscrunch").setValue("reverscrunch");
                    databaseReference.child("fragment_ExList2").child("reverscrunch").setValue("리버스 크런치");
                    String name = "";
                    name ="mountainclimber";
                    adapter.addItem(new ExerciseItem(name));
                    adapter.notifyDataSetChanged();
                } else
                {
                    DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference()
                            .child("fragment_ExList2").child("reverscrunch");
                    mPostReference.removeValue();
                    DatabaseReference mPostReference2 = FirebaseDatabase.getInstance().getReference()
                            .child("fragment_ExList").child("reverscrunch");
                    mPostReference2.removeValue();
                    adapter.notifyDataSetChanged();

                }
            }
        }) ;
        cb4.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    databaseReference.child("fragment_ExList").child("backlunge").setValue("backlunge");
                    databaseReference.child("fragment_ExList2").child("backlunge").setValue("백 런지");
                    String name = "";
                    name ="mountainclimber";
                    adapter.addItem(new ExerciseItem(name));
                    adapter.notifyDataSetChanged();
                } else
                {
                    DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference()
                            .child("fragment_ExList2").child("backlunge");
                    mPostReference.removeValue();
                    DatabaseReference mPostReference2 = FirebaseDatabase.getInstance().getReference()
                            .child("fragment_ExList").child("backlunge");
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