package com.example.mylogin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> implements ItemTouchHelperListener

{

    // adapter에 들어갈 list 입니다.
    private ArrayList<Data> listData = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }

    void addItem(Data data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }
    @Override public boolean onItemMove(int from_position, int to_position) {
        Data data = listData.get(from_position);
        listData.remove(from_position);
        listData.add(to_position,data);
        notifyItemMoved(from_position,to_position); return true;
    }
    @Override
    public void onItemSwipe(int position) {
        listData.remove(position); notifyItemRemoved(position);
    }


    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView c1;
        public TextView c2;
        public ImageButton m1;
        public ImageButton m2;
        public ImageButton p1;
        public ImageButton p2;

        ItemViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.v1);
            c1 = itemView.findViewById(R.id.v1_c1);
            c2 = itemView.findViewById(R.id.v1_c2);
            m1 = itemView.findViewById(R.id.v1_m1);
            m2 = itemView.findViewById(R.id.v1_m2);
            p1 = itemView.findViewById(R.id.v1_p1);
            p2 = itemView.findViewById(R.id.v1_p2);
        }

        void onBind(Data data) {
            imageView.setImageResource(data.getResId());
            p1.setImageResource(data.getBtnId());
            p2.setImageResource(data.getBtnId());
            m1.setImageResource(data.getBtnId());
            m2.setImageResource(data.getBtnId());

        }
    }
}