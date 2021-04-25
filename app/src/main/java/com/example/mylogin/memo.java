package com.example.mylogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;


public class memo extends AppCompatActivity {

    public String fname=null;
    public String str=null;
    public CalendarView calendarView;
    public Button btn_edit, btn_del, btn_save;
    public TextView diaryTextView, recordText;
    public EditText editText;
    //ImageView back_memo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        calendarView=findViewById(R.id.calendarView);
        diaryTextView=findViewById(R.id.diaryTextView);
        btn_save =findViewById(R.id.btn_save);
        btn_del =findViewById(R.id.btn_del);
        btn_edit =findViewById(R.id.btn_edit);
        recordText =findViewById(R.id.recordText);
        editText =findViewById(R.id.editText);

        //User can scroll the record screen
        recordText.setMovementMethod(new ScrollingMovementMethod());

        /*//로그인 및 회원가입 엑티비티에서 이름을 받아옴
        Intent intent=getIntent();
        String name=intent.getStringExtra("userName");
        final String userID=intent.getStringExtra("userID");
        textView3.setText(name+"님의 달력 일기장");*/


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
                diaryTextView.setText(String.format("Record on %d/%d/%d",year,month+1,dayOfMonth));
                editText.setText("");
                checkDay(year,month,dayOfMonth);
            }
        });

        //setting record structure when 'SAVE' button is pressed.
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDiary(fname);
                str= editText.getText().toString();
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
    //When value of exercise list is implemented, will implement that this function will be connected with Firebase.
    public void  checkDay(int cYear,int cMonth,int cDay){
        fname=cYear+"-"+(cMonth+1)+""+"-"+cDay+".txt";
        FileInputStream fis=null;

        try{
            fis=openFileInput(fname);

            byte[] fileData=new byte[fis.available()];
            fis.read(fileData);
            fis.close();

            str=new String(fileData);

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
            if(recordText.getText()==null){
                recordText.setVisibility(View.INVISIBLE);
                diaryTextView.setVisibility(View.VISIBLE);
                btn_save.setVisibility(View.VISIBLE);
                btn_edit.setVisibility(View.INVISIBLE);
                btn_del.setVisibility(View.INVISIBLE);
                editText.setVisibility(View.VISIBLE);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //All contents with record is removed.
    @SuppressLint("WrongConstant")
    public void removeDiary(String readDay){
        FileOutputStream fos=null;

        try{
            fos=openFileOutput(readDay,MODE_NO_LOCALIZED_COLLATORS);
            String content="";
            fos.write((content).getBytes());
            fos.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Save the changed part of contents
    @SuppressLint("WrongConstant")
    public void saveDiary(String readDay){
        FileOutputStream fos=null;

        try{
            fos=openFileOutput(readDay,MODE_NO_LOCALIZED_COLLATORS);
            String content= editText.getText().toString();
            fos.write((content).getBytes());
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}