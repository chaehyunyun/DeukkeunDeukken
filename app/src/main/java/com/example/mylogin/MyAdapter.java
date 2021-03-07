package com.example.mylogin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    // 이 데이터들을 가지고 각 뷰 홀더에 들어갈 텍스트 뷰에 연결할 것
    private int[] btnSet;
    private int[] imgSet;

    // 생성자
    public MyAdapter(int[] imgSet, int[] btnSet){
        this.btnSet = btnSet;
        this.imgSet = imgSet;
    }

    // 리사이클러뷰에 들어갈 뷰 홀더, 그리고 그 뷰 홀더에 들어갈 아이템들을 지정
    public static class MyViewHolder extends  RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView c1;
        public TextView c2;
        public ImageButton m1;
        public ImageButton m2;
        public ImageButton p1;
        public ImageButton p2;

        public MyViewHolder(View view){
            super(view);
            this.imageView = view.findViewById(R.id.v1);
            this.c1 = view.findViewById(R.id.v1_c1);
            this.c2 = view.findViewById(R.id.v1_c2);
            this.m1 = view.findViewById(R.id.v1_m1);
            this.m2 = view.findViewById(R.id.v1_m2);
            this.p1 = view.findViewById(R.id.v1_p1);
            this.p2 = view.findViewById(R.id.v1_p2);
        }
    }

    // 어댑터 클래스 상속시 구현해야할 함수 3가지 : onCreateViewHolder, onBindViewHolder, getItemCount
    // 리사이클러뷰에 들어갈 뷰 홀더를 할당하는 함수, 뷰 홀더는 실제 레이아웃 파일과 매핑되어야하며, extends의 Adater<>에서 <>안에들어가는 타입을 따른다.
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View holderView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(holderView);
        return myViewHolder;
    }

    // 실제 각 뷰 홀더에 데이터를 연결해주는 함수
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.imageView.setBackgroundResource(this.imgSet[i]);
    }

    // iOS의 numberOfRows, 리사이클러뷰안에 들어갈 뷰 홀더의 개수
    @Override
    public int getItemCount() {
        return btnSet.length > imgSet.length ? btnSet.length : imgSet.length;
    }
}
