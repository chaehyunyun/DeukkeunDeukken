package com.example.mylogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class Adapter_cart extends RecyclerView.Adapter<Adapter_cart.ItemViewHolder> implements ItemTouchHelperListener {
    // adapter에 들어갈 list 입니다.
    private ArrayList<Data> ex_list = new ArrayList<>();
    private ArrayList<Dictionary> mList;
    private OnItemClickListener mListener = null;
    int count=0, set=0;

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

    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView index;
        public TextView textView_count, textView_set;
        public ImageButton btn_count_up, btn_count_down, btn_set_up, btn_set_down;
        /*public TextView textView_count, textView_set;
        int count = 0;
        int set = 0;*/

        ItemViewHolder(View itemView) {
            super(itemView);

            index = itemView.findViewById(R.id.index);
            imageView = itemView.findViewById(R.id.image);
            textView_count = itemView.findViewById(R.id.textView_count);
            textView_set = itemView.findViewById(R.id.textView_set);
            btn_count_down=itemView.findViewById(R.id.btn_count_down);
            btn_count_up=itemView.findViewById(R.id.btn_count_up);
            btn_set_down=itemView.findViewById(R.id.btn_set_down);
            btn_set_up=itemView.findViewById(R.id.btn_set_up);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(position!=RecyclerView.NO_POSITION){
                        if(mListener!=null){
                            mListener.onItemClick(v,position);
                        }
                    }
                }
            });

            /*
            //횟수 및 세트 조절
            textView_count = itemView.findViewById(R.id.textView_count);
            textView_set = itemView.findViewById(R.id.textView_set);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(position!=RecyclerView.NO_POSITION){
                        if(mListener!=null){
                            mListener.onItemClick(v,position);
                        }
                    }
                }
            });*/

        }

        void onBind(Data data) {
            index.setText(Integer.toString(data.getIndex()));
            imageView.setImageResource(data.getResId());
            //textView_count.setText(Integer.toString(data.getIndex()));
            //textView_set.setText(Integer.toString(data.getIndex()));
        }

        /*public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_count_down:
                    count--;
                    textView_count.setText(Integer.toString(count));
                    break;
                case R.id.btn_count_up:
                    textView_count.setText(Integer.toString(count));
                    count++;
                    break;
                case R.id.btn_set_down:
                    textView_count.setText(Integer.toString(set));
                    set--;
                    break;
                case R.id.btn_set_up:
                    textView_count.setText(Integer.toString(set));
                    set++;
                    break;
            }
        }*/
    }
}