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

public class Adapter_cart extends RecyclerView.Adapter<Adapter_cart.ItemViewHolder> implements ItemTouchHelperListener {
    // adapter에 들어갈 list 입니다.
    private ArrayList<Data> ex_list = new ArrayList<>();

    public interface OnItemClickListener{
        //void onItemClick(View v, int position);
        void onItemClicked(int position);
        void onCountUpClick(int position);
        void onCountDownClick(int position);
        void onSetUpClick(int position);
        void onSetDownClick(int position);
        void onItemChanged();
    }

    private OnItemClickListener mListener;

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener ;
    }

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

        holder.onBind(ex_list.get(position));

        if(mListener!=null)
        {
            final int pos = position;

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClicked(pos);
                }
            });

            holder.btn_set_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onSetUpClick(pos);
                }
            });

            holder.btn_set_down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onSetDownClick(pos);
                }
            });

        }

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
        notifyItemMoved(from_position,to_position);
        for(int i=0; i<getItemCount(); i++) {
            data = ex_list.get(i);
            data.setIndex(i+1);
        }
        mListener.onItemChanged();
        return true;
    }

    @Override
    public void onItemSwipe(int position) {
        ex_list.remove(position);
        notifyItemRemoved(position);
        for(int i=0; i<getItemCount(); i++) {
            Data data = ex_list.get(i);
            data.setIndex(i+1);
        }
        mListener.onItemChanged();
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView index;
        public TextView textView_set;
        public ImageButton btn_set_up, btn_set_down;

        ItemViewHolder(View itemView) {
            super(itemView);

            index = itemView.findViewById(R.id.index);
            imageView = itemView.findViewById(R.id.image);
            textView_set = itemView.findViewById(R.id.textView_set);
            btn_set_down=itemView.findViewById(R.id.btn_set_down);
            btn_set_up=itemView.findViewById(R.id.btn_set_up);

        }

        void onBind(Data data) {
            index.setText(Integer.toString(data.getIndex()));
            imageView.setImageResource(data.getResId());
            textView_set.setText(Integer.toString(data.getSet()));
        }

    }
}