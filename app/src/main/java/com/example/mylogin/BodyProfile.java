package com.example.mylogin;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BodyProfile extends AppCompatActivity {

    EditText valueHeight, valueWeight;
    TextView BMILevel, valueBMI;
    Button btnBMI, btnInsert;
    DatabaseReference reference;
    String height, weight, BMI, level;
    ArrayAdapter<String> adapter;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    SeekBar seekbar1, seekbar2;
    ImageView back;
    TextView textDate;
    Boolean cheakBMI;
    String date, uid;

    static ArrayList<String> arrayIndex = new ArrayList<String>();
    static ArrayList<String> arrayData = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_profile);

        // Get the ID of the currently connected user
        DatabaseReference mDatabase;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // Get information of logged in user
        uid = user != null ? user.getUid() : null; // Get the unique uid of the logged-in user

        // Get date
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        date = simpleDate.format(mDate);

        valueHeight = findViewById(R.id.valueHeight);
        valueWeight = findViewById(R.id.valueWeight);
        valueBMI = findViewById(R.id.valueBMI);
        BMILevel = findViewById(R.id.BMILevel);
        btnBMI = findViewById(R.id.btnBMI);
        btnInsert = findViewById(R.id.btnInsert);
        seekbar1 = findViewById(R.id.seekbar1);
        seekbar2 = findViewById(R.id.seekbar2);
        back = findViewById(R.id.back);
        cheakBMI = false;

        // Setting today's date
        textDate = findViewById(R.id.textDate);
        textDate.setText(date);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        height = preferences.getString("height", "180");
        valueHeight.setText(height);
        weight = preferences.getString("weight", "70");
        valueWeight.setText(weight);
        BMI = preferences.getString("BMI", "21.60");
        valueBMI.setText(BMI);
        level = preferences.getString("level", "Normal");
        BMILevel.setText(level);
        findBmiResult();

        // Back button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Save data to firebase
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cheakBMI == false) {
                    showToast("BMI 확인 후 저장하세요");
                    Log.d("BodyProfile", "bmi 확인 후 저장");
                } else {
                    save();
                    editor.putString("height", String.valueOf(valueHeight.getText()));
                    editor.putString("weight", String.valueOf(valueWeight.getText()));
                    editor.putString("BMI", String.valueOf(valueBMI.getText()));
                    editor.putString("level", String.valueOf(BMILevel.getText()));
                    editor.apply();
                    showToast("입력되었습니다.");
                    Log.d("BodyProfile", "저장 완료");
                }
            }
        });

        // Height setting using seekbar
        seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                cheakBMI = false;
                height = String.valueOf(progress);
                valueHeight.setText(height);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Weight setting using seekbar
        seekbar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                cheakBMI = false;
                weight = String.valueOf(progress);
                valueWeight.setText(weight);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // BMI Confirm button
        btnBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cheakBMI = true;
                findBmiResult();
            }
        });

    } // End of onCreate method


    // BMI calculation and setting
    public void findBmiResult() {
        String strNum = height;
        double height = Integer.parseInt(strNum);
        strNum = weight;
        double weight = Integer.parseInt(strNum);
        double result = weight / height / height * 10000;
        strNum = String.format("%.2f", result);
        BMI = strNum;
        valueBMI.setText(strNum);

        if (result < 18.5) {
            BMILevel.setText("Underweight");
            BMILevel.setTextColor(Color.parseColor("#FF0026FF"));
        }
        else if (18.5 <= result && result <= 22.9) {
            BMILevel.setText("Normal");
            BMILevel.setTextColor(Color.parseColor("#FF299E00"));
        } else if (23 <= result && result <= 24.9) {
            BMILevel.setText("Overweight");
            BMILevel.setTextColor(Color.parseColor("#FFFFB300"));
        } else if (25 <= result && result <= 29.9) {
            BMILevel.setText("Obese I");
            BMILevel.setTextColor(Color.parseColor("#FFFF6600"));
        } else if (30 <= result && result <= 34.9) {
            BMILevel.setText("Obese II");
            BMILevel.setTextColor(Color.parseColor("#FFFF3300"));
        } else if (30 <= result) {
            BMILevel.setText("Obese III");
            BMILevel.setTextColor(Color.parseColor("#FFCC0000"));
        }

    }

    // Data storage and modification method
    public void postFirebaseDataBase(boolean add) {
        reference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;
        if (add) {
            FirebasePost post = new FirebasePost(height, weight, BMI);
            postValues = post.toMap();
        }
        childUpdates.put("/User_BodyProfile_list/" + uid + "/" + date + "/", postValues);
        reference.updateChildren(childUpdates);
    }

//    // Firebase's data lookup method
//    public void getFirebaseDataBase() {
//        ValueEventListener eventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                arrayData.clear();
//                arrayIndex.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    String key = dataSnapshot.getKey();
//                    FirebasePost get = dataSnapshot.getValue(FirebasePost.class);
//                    String info[] = {get.height, get.weight, get.BMI};
//                    String result = info[0] + " " + info[1] + " " + info[2];
//                    arrayData.add(result);
//                    arrayIndex.add(key);
//                }
//                adapter.clear();
//                adapter.addAll(arrayData);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                showToast("데이터베이스 로드 실패!!");
//            }
//        };
//        Query data = FirebaseDatabase.getInstance().getReference().
//                child("id_list").orderByChild("id");
//        data.addListenerForSingleValueEvent(eventListener);
//    }

    // Save to Firebase
    public void save() {
        height = String.valueOf(valueHeight.getText());
        weight = String.valueOf(valueWeight.getText());
        BMI = String.valueOf(valueBMI.getText());
        postFirebaseDataBase(true);
        cheakBMI = true;
    }

    // Toast method
    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}