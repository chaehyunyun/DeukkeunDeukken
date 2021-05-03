package com.example.mylogin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class fragment_cardio extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //앱을 껐다가 다시 접속했을 때 전에 올려뒀던 파이어베이스를 가져오기 때문에
        //파이어베이스를 초기화해줌
        firebaseDatabase.getReference().child("fragment_ExList").removeValue();
        View view = inflater.inflate(R.layout.fragment_cardio, container, false);

        //If you click the exercise picture, you can move to the exercise detail page.
        /*
        ImageView v1 = (ImageView) view.findViewById(R.id.v1);
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ex_detail.class);
                startActivity(intent);
            }
        });*/

        return view;
    } //onCreateView 끝

    //A 'Callback' method called when the State of CheckBox changes by clicking
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // 체크박스를 클릭해서 상태가 바꾸었을 경우 호출되는 콜백 메서드

        //Delete the current exercise list in the real-time database
        // because the exercise list has been updated.
        firebaseDatabase.getReference().child("fragment_ExList").removeValue();

        String name = "";
        adapter = new fragment_cardio.Adapter();

        /*
        if(cb1.isChecked()) {
            name+=cb1.getText().toString()+"런지1\n";
        }

        if(cb2.isChecked()) {
            name+=cb2.getText().toString()+"런지2\n";
        }*/

        //Push() the updated exercise list to the real-time database.
        databaseReference.child("fragment_ExList").push().setValue(name);
        adapter.addItem(new ExerciseItem(name));
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
