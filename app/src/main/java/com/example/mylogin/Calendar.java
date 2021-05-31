package com.example.mylogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompatSideChannelService;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Calendar extends AppCompatActivity {

    public String fname = null;
    public String str = null;
    public String workout_list;
    public CalendarView calendarView;
    public Button btn_edit, btn_del, btn_save;
    public TextView diaryTextView, recordText, txtBodyRecord, txtWorkoutRecord;
    public EditText editText;
    ImageView back;

    String uid;
    String bf, wl;
    String changeDate;

    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mReference = mDatabase.getReference();
    private ChildEventListener mChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        calendarView = findViewById(R.id.calendarView);
        diaryTextView = findViewById(R.id.diaryTextView);
        btn_save = findViewById(R.id.btn_save);
        btn_del = findViewById(R.id.btn_del);
        btn_edit = findViewById(R.id.btn_edit);
        recordText = findViewById(R.id.recordText);
        editText = findViewById(R.id.editText);
        back = findViewById(R.id.back);
        txtBodyRecord = findViewById(R.id.txtBodyRecord);
        txtWorkoutRecord = findViewById(R.id.txtWorkoutRecord);

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy//MM/dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        String getTime = sdf.format(date);
        String curDate = sdf2.format(date);
        diaryTextView.setText(String.format("Record on " + getTime));

        // back button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //User can scroll the record screen
        txtBodyRecord.setMovementMethod(new ScrollingMovementMethod());
        txtWorkoutRecord.setMovementMethod(new ScrollingMovementMethod());
        recordText.setMovementMethod(new ScrollingMovementMethod());

        /*//로그인 및 회원가입 엑티비티에서 이름을 받아옴
        Intent intent=getIntent();
        String name=intent.getStringExtra("userName");
        final String userID=intent.getStringExtra("userID");
        textView3.setText(name+"님의 달력 일기장");*/

        workout_list = "";

        // Get the ID of the currently connected user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // Get information of logged in user
        uid = user != null ? user.getUid() : null; // Get the unique uid of the logged-in user

        mReference.child("User_BodyProfile_list").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(curDate)) {

                    for (DataSnapshot snapshot : dataSnapshot.child(curDate).getChildren()) {

                        String key = snapshot.getKey();

                        if (key.equals("zBF")) {
                            Log.d("test", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+bf);
                            bf = "" + snapshot.getValue();
                            txtBodyRecord.setText("BODY PROFILE RECORD\n" + bf);
                        } else {
                            txtBodyRecord.setText("BODY PROFILE RECORD\nNO RECORD");
                        }
                    }

                } else {
                    txtBodyRecord.setText("BODY PROFILE RECORD\nNO RECORD");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mReference.child("User_Ex_list").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(curDate)) {

                    for (DataSnapshot snapshot : dataSnapshot.child(curDate).getChildren()) {

                        String key = snapshot.getKey();

                        if (key.equals("zTotal")) {
                            wl = "" + snapshot.getValue();
                            txtWorkoutRecord.setText("WORKOUT RECORD\n" + wl);
                        } else {
                            String workout = "WORKOUT RECORD\n" + "NO RECORD";
                            txtWorkoutRecord.setText(workout);
                        }
                    }

                } else {
                    String workout = "WORKOUT RECORD\n" + "NO RECORD";
                    txtWorkoutRecord.setText(workout);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //setting record structure
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            //The record is set in selected date by user
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                diaryTextView.setVisibility(View.VISIBLE);
                btn_save.setVisibility(View.VISIBLE);
                editText.setVisibility(View.VISIBLE);
                recordText.setVisibility(View.INVISIBLE);
                btn_edit.setVisibility(View.INVISIBLE);
                btn_del.setVisibility(View.INVISIBLE);
                diaryTextView.setText(String.format("Record on %d/%d/%d", year, month + 1, dayOfMonth));
                if (month + 1 < 10) {
                    if (dayOfMonth < 10) {
                        changeDate = String.format("%d-0%d-0%d", year, month + 1, dayOfMonth);
                    } else {
                        changeDate = String.format("%d-0%d-%d", year, month + 1, dayOfMonth);
                    }
                } else {
                    if (dayOfMonth < 10) {
                        changeDate = String.format("%d-%d-0%d", year, month + 1, dayOfMonth);
                    } else {
                        changeDate = String.format("%d-%d-%d", year, month + 1, dayOfMonth);
                    }
                }

                // Get the ID of the currently connected user
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // Get information of logged in user
                uid = user != null ? user.getUid() : null; // Get the unique uid of the logged-in user

                mReference.child("User_BodyProfile_list").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChild(changeDate)) {

                            for (DataSnapshot snapshot : dataSnapshot.child(changeDate).getChildren()) {

                                String key = snapshot.getKey();

                                if (key.equals("zBF")) {
                                    bf = "" + snapshot.getValue();
                                    txtBodyRecord.setText("BODY PROFILE RECORD\n" + bf);
                                } else {
                                    txtBodyRecord.setText("BODY PROFILE RECORD\nNO RECORD");
                                }
                            }

                        } else {
                            txtBodyRecord.setText("BODY PROFILE RECORD\nNO RECORD");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                mReference.child("User_Ex_list").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChild(changeDate)) {

                            for (DataSnapshot snapshot : dataSnapshot.child(changeDate).getChildren()) {

                                String key = snapshot.getKey();

                                if (key.equals("zTotal")) {
                                    wl = "" + snapshot.getValue();
                                    txtWorkoutRecord.setText("WORKOUT RECORD\n" + wl);
                                } else {
                                    String workout = "WORKOUT RECORD\n" + "NO RECORD";
                                    txtWorkoutRecord.setText(workout);
                                }
                            }

                        } else {
                            String workout = "WORKOUT RECORD\n" + "NO RECORD";
                            txtWorkoutRecord.setText(workout);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                checkDay(year, month, dayOfMonth);
            }
        });

        //setting record structure when 'SAVE' button is pressed.
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDiary(fname);
                str = editText.getText().toString();
                recordText.setText(str);
                btn_save.setVisibility(View.INVISIBLE);
                btn_edit.setVisibility(View.VISIBLE);
                btn_del.setVisibility(View.VISIBLE);
                editText.setVisibility(View.INVISIBLE);
                recordText.setVisibility(View.VISIBLE);

            }
        });
    }

    //contents is saved through file-stream.
    //When value of exercise list is implemented,
    //will implement that this function will be connected with Firebase.
    public void checkDay(int cYear, int cMonth, int cDay) {
        fname = cYear + "-" + (cMonth + 1) + "" + "-" + cDay + ".txt";
        FileInputStream fis = null;

        try {
            fis = openFileInput(fname);
            byte[] fileData = new byte[fis.available()];
            fis.read(fileData);
            fis.close();

            str = new String(fileData);
            editText.setVisibility(View.INVISIBLE);
            recordText.setVisibility(View.VISIBLE);
            recordText.setText(str);

            btn_save.setVisibility(View.INVISIBLE);
            btn_edit.setVisibility(View.VISIBLE);
            btn_del.setVisibility(View.VISIBLE);

            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editText.setVisibility(View.VISIBLE);
                    recordText.setVisibility(View.INVISIBLE);
                    editText.setText(str);

                    btn_save.setVisibility(View.VISIBLE);
                    btn_edit.setVisibility(View.INVISIBLE);
                    btn_del.setVisibility(View.INVISIBLE);
                    recordText.setText(editText.getText());
                }

            });

            btn_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recordText.setVisibility(View.INVISIBLE);
                    editText.setText("");
                    editText.setVisibility(View.VISIBLE);
                    btn_save.setVisibility(View.VISIBLE);
                    btn_edit.setVisibility(View.INVISIBLE);
                    btn_del.setVisibility(View.INVISIBLE);
                    removeDiary(fname);
                }
            });
            if (recordText.getText() == null) {
                recordText.setVisibility(View.INVISIBLE);
                diaryTextView.setVisibility(View.VISIBLE);
                btn_save.setVisibility(View.VISIBLE);
                btn_edit.setVisibility(View.INVISIBLE);
                btn_del.setVisibility(View.INVISIBLE);
                editText.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //All contents with record is removed.
    @SuppressLint("WrongConstant")
    public void removeDiary(String readDay) {
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS);
            String content = "";
            fos.write((content).getBytes());
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Save the changed part of contents
    @SuppressLint("WrongConstant")
    public void saveDiary(String readDay) {
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS);
            String content = editText.getText().toString();
            fos.write((content).getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}