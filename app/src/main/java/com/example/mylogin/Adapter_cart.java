package com.example.mylogin;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Adapter_cart extends RecyclerView.Adapter<Adapter_cart.ItemViewHolder> implements ItemTouchHelperListener {
    // adapter에 들어갈 list 입니다.
    private ArrayList<Data> ex_list = new ArrayList<>();
    DatabaseReference reference;

    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mReference = mDatabase.getReference();
    String curTotal, newTotal;


    public interface OnItemClickListener {
        //void onItemClick(View v, int position);
        void onItemClicked(int position);

        void onSetUpClick(int position);

        void onSetDownClick(int position);

        void onItemChanged();
    }

    private OnItemClickListener mListener;

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
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

        if (mListener != null) {
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

    void modifyItem(int pos, boolean option)
    {
        int set = ex_list.get(pos).getSet();

        if(option==true)
        {
            set++;
        }
        else
        {
            if(set==0)
            {
                set=0;
            }
            else
            {
                set--;
            }
        }

        ex_list.get(pos).setSet(set);
    }

    @Override
    public boolean onItemMove(int from_position, int to_position) {
        Data data = ex_list.get(from_position);
        ex_list.remove(from_position);
        ex_list.add(to_position, data);
        notifyItemMoved(from_position, to_position);
        for (int i = 0; i < getItemCount(); i++) {
            data = ex_list.get(i);
            data.setIndex(i + 1);
        }
        mListener.onItemChanged();
        return true;
    }

    @Override
    public void onItemSwipe(int position) {
        ex_list.remove(position);
        notifyItemRemoved(position);
        for (int i = 0; i < getItemCount(); i++) {
            Data data = ex_list.get(i);
            data.setIndex(i + 1);
        }
        mListener.onItemChanged();
    }

    public void postFirebaseDataBase(boolean add) {

        // Get date
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleTime = new SimpleDateFormat("HH:mm");
        String date = simpleDate.format(mDate);
        String time = simpleTime.format(mDate);

        // Get the ID of the currently connected user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // Get information of logged in user
        reference = FirebaseDatabase.getInstance().getReference();
        String uid = user != null ? user.getUid() : null; // Get the unique uid of the logged-in user
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, String> ex = new HashMap<>(); // exercies + set hashmap

        ex.put("TIME", time);
        newTotal = "TIME: " + time + "\n";
        for (int i = 0; i < ex_list.size(); i++) {
            Data data = ex_list.get(i);
            String exercise = data.getName();
            String set = String.valueOf(data.getSet());
            String es = exercise + ": " + set + "\n";
            newTotal = newTotal + es + "\n";
            ex.put(exercise, set);
            ex.put("zTotal", newTotal);
        }
        childUpdates.put("/User_Ex_list/" + uid + "/" + date + "/", ex);
        reference.updateChildren(childUpdates);

    }

    // 운동 기록 (운동 이름 + 세트) 파베에 저장하기
    public void postFirebaseDataBase2(boolean add) {
        // Get date
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleTime = new SimpleDateFormat("HH:mm");
        String date = simpleDate.format(mDate);
        String time = simpleTime.format(mDate);

        // Get the ID of the currently connected user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // Get information of logged in user
        reference = FirebaseDatabase.getInstance().getReference();
        String uid = user != null ? user.getUid() : null; // Get the unique uid of the logged-in user
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, String> ex = new HashMap<>(); // exercies + set hashmap

        mReference.child("User_Ex_list").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(date)) {

                    for (DataSnapshot snapshot : dataSnapshot.child(date).getChildren()) {
                        String key = snapshot.getKey();

                        if (key.equals("zTotal")) {
                            curTotal = "" + snapshot.getValue();
                            if (add) {
                                ex.put("TIME", time);
                                newTotal = curTotal + "TIME: " + time + "\n";
                                for (int i = 0; i < ex_list.size(); i++) {
                                    Data data = ex_list.get(i);
                                    String exercise = data.getName();
                                    String set = String.valueOf(data.getSet());
                                    String es = exercise + ": " + set + "SET";
                                    newTotal = newTotal + es + "\n";
                                    ex.put(exercise, set);
                                    ex.put("zTotal", newTotal);
                                }
                                childUpdates.put("/User_Ex_list/" + uid + "/" + date + "/", ex);
                                reference.updateChildren(childUpdates);
                            }

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
            btn_set_down = itemView.findViewById(R.id.btn_set_down);
            btn_set_up = itemView.findViewById(R.id.btn_set_up);

        }

        void onBind(Data data) {
            index.setText(Integer.toString(data.getIndex()));
            imageView.setImageResource(data.getResId());
            textView_set.setText(Integer.toString(data.getSet()));
        }

    }

}