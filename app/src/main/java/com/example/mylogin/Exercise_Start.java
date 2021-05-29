package com.example.mylogin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Exercise_Start extends AppCompatActivity implements MediaPlayer.OnCompletionListener {
    ImageView back;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mChild;
    private FirebaseDatabase mDatabase2;
    private DatabaseReference mReference2;
    private ChildEventListener mChild2;
    VideoView vw;
    ArrayList<Integer> videolist = new ArrayList<>();
    ArrayList<Integer> nextExList=new ArrayList<>();
    ArrayList<String> SetList=new ArrayList<>();
    ArrayList<String> SecondList=new ArrayList<>();
    int currvideo = 0;
    int count=0;
    int count2=0;
    private MediaPlayer mediapalyer;
    int set_int=0;
    CountDownTimer timer;
    String stringSec = "30";
    int videocount=0;
    private boolean mTimerRunning;
    private Intent intent;
    String date;
    String packName;
    int i=0;
    ImageView nextex;
    int nextcount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        packName = this.getPackageName();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_start);

        intent = getIntent();// 인텐트 받아오기
        date = intent.getStringExtra("date");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // Get information of logged in user
        String uid = user != null ? user.getUid() : null; // Get the unique uid of the logged-in user

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference()
                        .child("set");
                mPostReference.removeValue();
                onBackPressed();
            }
        });


        //String[] ex_list = new String[100];

        //파이어베이스에 있는 운동 목록 가져오기
        initDatabase();
        initDatabase2();
        vw = (VideoView)findViewById(R.id.startEx_vv);
        vw.setMediaController(new MediaController(this));
        vw.setOnCompletionListener(this);

  /*      mReference = mDatabase.getReference("ex_name"); // 변경값을 확인할 child 이름
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    String str = messageData.getValue().toString();
                    Log.i("ordertest",str);
                }
            }

            //세트랑 횟수

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        DatabaseReference mReference7 = mDatabase.getReference("next_ex"); // 변경값을 확인할 child 이름
        mReference7.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    String str = messageData.getValue().toString();
                    Log.i("nextex",str);
                    int res = getResources().getIdentifier("@drawable/" + str, "drawable", packName);
                    nextExList.add(res);
                }
            }

            //세트랑 횟수

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mReference = mDatabase.getReference("ex_name"); // 변경값을 확인할 child 이름
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    String str = messageData.getValue().toString();
                    //여기서 리스트 어레이나 스트링배열에 넣어야될듯...?
                    int resID = getResources().getIdentifier(str, "raw", getPackageName());
                    videolist.add(resID);

                    if(count==0)
                    {
                        setVideo(videolist.get(0));
                        count++;
                    }
                }
            }

            //세트랑 횟수

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


       /* DatabaseReference ref = mDatabase.getReference("User_Ex_list").child(uid).child(date); // 변경값을 확인할 child 이름
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    String str = messageData.getValue().toString();
                    Log.i("test",str);
                    SetList.add(str);
                    if(count2==0)
                    {
                        countDown(stringSec, SetList.get(videocount));
                        count2++;
                    }
                }
            }

            //세트랑 횟수

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }); */

        //파이어베이스에서 가져온 운동리스트를 바탕으로
        //순서대로 영상 틀기
       /* VideoView Video1 = (VideoView) findViewById(R.id.startEx_vv);
        Uri video = Uri.parse("android.resource://" + getPackageName() +"/"+R.raw.burpi);


        MediaController mediacontroller = new MediaController(this);
        Video1.setMediaController(mediacontroller);
        Video1.requestFocus();
        Video1.setVideoURI(video);
        Video1.start();

        //onPrepareListener는 vv.start() 후에 호출됩니다.
        Video1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });*/

        //세트랑 횟수 파이어베이스에서 가져오기
        //이렇게 하나 더 만드는게 맞는지 모르겠어... 일단 했음ㅎㅎ
       mReference2 = mDatabase2.getReference("User_Ex_list").child(uid).child(date); // 변경값을 확인할 child 이름
        mReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    String str = messageData.getValue().toString();
                    Log.i("test",str);
                    SetList.add(str);
                    Toast.makeText(getApplicationContext(), SetList.get(0), Toast.LENGTH_SHORT).show();
                    if(count2==0)
                    {
                        countDown(stringSec, SetList.get(videocount));
                        count2++;
                    }
                }
            }

            //세트랑 횟수

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    } //onCreate end

    public void setVideo(int id)
    {

        TextView textview_second = (TextView) findViewById(R.id.second);
        TextView textview_set = (TextView) findViewById(R.id.setCount);
        nextex=(ImageView)findViewById(R.id.nextex);
        nextex.setImageResource(nextExList.get(nextcount));
        nextcount++;
        String uriPath
                = "android.resource://"
                + getPackageName() + "/" + id;
        Uri uri = Uri.parse(uriPath);
        vw.setVideoURI(uri);
        vw.start();
        vw.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        //함수호출


    }



    @Override
    public void onCompletion(MediaPlayer mediapalyer)
    {

        this.mediapalyer = mediapalyer;
        AlertDialog.Builder obj = new AlertDialog.Builder(this);
        obj.setTitle("Playback Finished!");
        obj.setIcon(R.mipmap.ic_launcher);
        MyListener m = new MyListener();
        obj.setPositiveButton("Replay", m);
        obj.setNegativeButton("Next", m);
        obj.setMessage("Want to replay or play next video?");
        obj.show();

    }

    class MyListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            if (which == -1) {
                vw.seekTo(0);
                vw.start();
            }
            else {

                countDown(stringSec, SetList.get(videocount));
                ++currvideo;
                String s=Integer.toString(currvideo);
                Log.i("butter",s);
                if (currvideo == videolist.size())
                    currvideo = 0;
                setVideo(videolist.get(currvideo));
            }
        }
    }

    public void countDown(String time, String set) {

        set_int = 0;
        long conversionTime = 0;
        String originalTime = time;
        int setCount = Integer.parseInt(set);

        // 1000 단위가 1초
        // 60000 단위가 1분
        // 60000 * 3600 = 1시간

        TextView textview_second = (TextView) findViewById(R.id.second);
        TextView textview_set = (TextView) findViewById(R.id.setCount);
        TextView secondtxt = (TextView) findViewById(R.id.secondtxt);
        TextView settxt = (TextView) findViewById(R.id.settxt);
        TextView fighting = (TextView) findViewById(R.id.youcandoit);
        TextView exname = (TextView) findViewById(R.id.exname);
        ImageView nextbtn=(ImageView)findViewById(R.id.nextbtn);
        ImageView rest=(ImageView)findViewById(R.id.rest);

        textview_second.setVisibility(View.VISIBLE);
        textview_set.setVisibility(View.VISIBLE);
        secondtxt.setVisibility(View.VISIBLE);
        settxt.setVisibility(View.VISIBLE);
        fighting.setVisibility(View.VISIBLE);
        exname.setVisibility(View.VISIBLE);
        nextbtn.setVisibility(View.VISIBLE);
        rest.setVisibility(View.INVISIBLE);
        textview_set.setText(set);
if(videocount%2==1)
{ textview_second.setVisibility(View.INVISIBLE);
textview_set.setVisibility(View.INVISIBLE);
    secondtxt.setVisibility(View.INVISIBLE);
    settxt.setVisibility(View.INVISIBLE);
    fighting.setVisibility(View.INVISIBLE);
    exname.setVisibility(View.INVISIBLE);
    rest.setVisibility(View.VISIBLE);
    nextbtn.setVisibility(View.INVISIBLE);
}

        // "00"이 아니고, 첫번째 자리가 0 이면 제거
        if (time.substring(0, 1) == "0") {
            time = time.substring(1, 2);
        }

        // 변환시간
        conversionTime = Long.valueOf(time) * 1000;

        // 첫번쨰 인자 : 원하는 시간 (예를들어 30초면 30 x 1000(주기))
        // 두번쨰 인자 : 주기( 1000 = 1초)
        // 특정 시간마다 뷰 변경
        // 초단위
        // 나머지
        // 초가 한자리면 0을 붙인다
        // 제한시간 종료시
        // 변경 후
        //세트 숫자 줄어들게 하는거 (숫자가 0이 되면 다음 운동으로 넘어가게)
        //세트가 0이 되면 다음운동으로 넘겨야됨
        timer = new CountDownTimer(conversionTime, 1000) {

            // 특정 시간마다 뷰 변경
            @Override
            public void onTick(long time) {

                // 초단위
                String second = String.valueOf((time % (60 * 1000)) / 1000); // 나머지

                // 초가 한자리면 0을 붙인다
                if (second.length() == 1) {
                    second = "0" + second;
                }
                textview_second.setText(second);
            }

            // 제한시간 종료시
            @Override
            public void onFinish() {

                // 변경 후
                //세트 숫자 줄어들게 하는거 (숫자가 0이 되면 다음 운동으로 넘어가게)
                textview_set.setText(String.valueOf(setCount - 1));

                if ((setCount - 1) == 0) { //세트가 0이 되면 다음운동으로 넘겨야됨
                    pauseTimer();
                    onCompletion(mediapalyer);
                    videocount++;
                }
                else{countDown(originalTime, String.valueOf(setCount - 1));}

            }
        }.start();

    }
    private void pauseTimer() {
        timer.cancel();
        mTimerRunning = false;
    }
    private int done()
    {
        return 1;
    }

    private void initDatabase() {

        mDatabase = FirebaseDatabase.getInstance();

        mReference = mDatabase.getReference("log");
        mReference.child("log").setValue("check");

        mChild = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mReference.addChildEventListener(mChild);
    }
    private void initDatabase2() {

        mDatabase2 = FirebaseDatabase.getInstance();

        mReference2 = mDatabase2.getReference("log");
        mReference2.child("log").setValue("check");

        mChild2 = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mReference2.addChildEventListener(mChild2);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}