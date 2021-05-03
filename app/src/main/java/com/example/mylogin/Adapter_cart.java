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
import java.util.List;

public class Adapter_cart extends RecyclerView.Adapter<Adapter_cart.ItemViewHolder> implements ItemTouchHelperListener {
    // adapter에 들어갈 list 입니다.
    private ArrayList<Data> ex_list = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(ex_list.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return ex_list.size();
    }

    void addItem(Data data) {
        // 외부에서 item을 추가시킬 함수입니다.
        ex_list.add(data);
    }
    @Override public boolean onItemMove(int from_position, int to_position) {
        Data data = ex_list.get(from_position);
        ex_list.remove(from_position);
        ex_list.add(to_position,data);
        notifyItemMoved(from_position,to_position); return true;
    }
    @Override
    public void onItemSwipe(int position) {
        ex_list.remove(position); notifyItemRemoved(position);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView index;

        ItemViewHolder(View itemView) {
            super(itemView);

            index = itemView.findViewById(R.id.index);
            imageView = itemView.findViewById(R.id.image);
        }


        void onBind(Data data) {
            index.setText(Integer.toString(data.getIndex()));
            imageView.setImageResource(data.getResId());
        }
    }
}