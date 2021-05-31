package com.example.mylogin;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Beginner_Routine extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    private FirebaseDatabase mDatabase= FirebaseDatabase.getInstance();
    private DatabaseReference mReference = mDatabase.getReference();
    private ChildEventListener mChild;
    private FirebaseDatabase mDatabase2;
    private DatabaseReference mReference2;
    private ChildEventListener mChild2;

    ImageView back;
    VideoView vw;
    ArrayList<Integer> videolist = new ArrayList<>();
    ArrayList<Integer> nextExList=new ArrayList<>();
    ArrayList<String> SetList=new ArrayList<>();
    ArrayList<String> koreanName = new ArrayList<>();
    int currvideo = 0;
    int count=0;
    int count2=0;
    private MediaPlayer mediapalyer;
    int set_int=0;
    CountDownTimer timer;
    String stringSec = "3";
    int videocount=0;
    private boolean mTimerRunning;
    String date;
    String packName;
    int i=0;
    ImageView nextex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.beginner_routine);

        //데이터 올리기

        //하체
        mReference.child("Routine1").child("0").setValue("crosslunge");
        mReference.child("Routine1").child("1").setValue("rest");
        mReference.child("Routine11").child("crosslunge").setValue("크로스 런지");

        mReference.child("Routine1").child("2").setValue("donkeykickleft");
        mReference.child("Routine1").child("3").setValue("rest");
        mReference.child("Routine11").child("donkeykickleft").setValue("동키 킥");

        mReference.child("Routine1").child("4").setValue("lyingraisingleftleg");
        mReference.child("Routine1").child("5").setValue("rest");
        mReference.child("Routine11").child("lyingraisingleftleg").setValue("누워 왼쪽다리 올리기");

        mReference.child("Routine1").child("6").setValue("lyingraisingrightleg");
        mReference.child("Routine1").child("7").setValue("rest");
        mReference.child("Routine11").child("lyingraisingrightleg").setValue("누워 오른쪽다리 올리기");

        mReference.child("Routine1").child("8").setValue("sidelunge");
        mReference.child("Routine1").child("9").setValue("rest");
        mReference.child("Routine11").child("sidelunge").setValue("사이드런지");

        mReference.child("Routine1").child("10").setValue("sumosquat");
        mReference.child("Routine1").child("11").setValue("rest");
        mReference.child("Routine11").child("sumosquat").setValue("스모 스쿼트");


        //routine1_next_ex
        mReference.child("Routine1_next_ex").child("0").setValue("donkeykickleft_next");
        mReference.child("Routine1_next_ex").child("1").setValue("white");
        mReference.child("Routine1_next_ex").child("2").setValue("lyingraisingleftleg_next");
        mReference.child("Routine1_next_ex").child("3").setValue("white");
        mReference.child("Routine1_next_ex").child("4").setValue("lyingraisingrightleg_next");
        mReference.child("Routine1_next_ex").child("5").setValue("white");
        mReference.child("Routine1_next_ex").child("6").setValue("sidelunge_next");
        mReference.child("Routine1_next_ex").child("7").setValue("white");
        mReference.child("Routine1_next_ex").child("8").setValue("sumosquat_next");
        mReference.child("Routine1_next_ex").child("9").setValue("white");
        mReference.child("Routine1_next_ex").child("100").setValue("white");
        mReference.child("Routine1_next_ex").child("111").setValue("white");


        //세트 올리기
        mReference.child("Routine1_set").child("crosslunge").setValue("5");
        mReference.child("Routine1_set").child("crosslunge_rest").setValue("1");
        mReference.child("Routine1_set").child("donkeykickleft").setValue("5");
        mReference.child("Routine1_set").child("donkeykickleft_rest").setValue("1");
        mReference.child("Routine1_set").child("lyingraisingleftleg").setValue("5");
        mReference.child("Routine1_set").child("lyingraisingleftleg_rest").setValue("1");
        mReference.child("Routine1_set").child("lyingraisingrightleg").setValue("5");
        mReference.child("Routine1_set").child("lyingraisingrightleg_rest").setValue("1");
        mReference.child("Routine1_set").child("sidelunge").setValue("5");
        mReference.child("Routine1_set").child("sidelunge_rest").setValue("1");
        mReference.child("Routine1_set").child("sumosquat").setValue("5");
        mReference.child("Routine1_set").child("sumosquat_rest").setValue("1");





        //복부
        mReference.child("Routine2").child("0").setValue("abdominalcrunch");
        mReference.child("Routine2").child("1").setValue("rest");
        mReference.child("Routine22").child("abdominalcrunch").setValue("복부 크런치");

        mReference.child("Routine2").child("2").setValue("heeltouches");
        mReference.child("Routine2").child("3").setValue("rest");
        mReference.child("Routine22").child("heeltouches").setValue("발 뒤꿈치 터치");

        mReference.child("Routine2").child("4").setValue("mountainclimber");
        mReference.child("Routine2").child("5").setValue("rest");
        mReference.child("Routine22").child("mountainclimber").setValue("마운틴 클라이머");

        mReference.child("Routine2").child("6").setValue("reclindobliquetwists");
        mReference.child("Routine2").child("7").setValue("rest");
        mReference.child("Routine22").child("reclindobliquetwists").setValue("리클라인드 오블릭 트위스트");

        mReference.child("Routine2").child("8").setValue("sissors");
        mReference.child("Routine2").child("9").setValue("rest");
        mReference.child("Routine22").child("sissors").setValue("시저 크로스");

        //전신




        packName = this.getPackageName();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // Get information of logged in user
        String uid = user != null ? user.getUid() : null; // Get the unique uid of the logged-in user

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        nextex=(ImageView)findViewById(R.id.nextex);

        initDatabase();
        initDatabase2();
        vw = (VideoView)findViewById(R.id.startEx_vv);
        vw.setMediaController(new MediaController(this));
        vw.setOnCompletionListener(this);




        //다음사진
        DatabaseReference mReference7 = mDatabase.getReference("Routine1_next_ex"); // 변경값을 확인할 child 이름
        mReference7.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    String str = messageData.getValue().toString();
                    Log.i("Routine1_next_ex",str);
                    int res = getResources().getIdentifier("@drawable/" + str, "drawable", packName);
                    nextExList.add(res);
                    if(videocount==0)
                    {nextex.setImageResource(nextExList.get(0));}
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Routine1_next_ex").child("100").setValue("white");
        reference.child("Routine1_next_ex").child("111").setValue("white");



        //운동 한글이름 가져오기
        DatabaseReference mReferenceName = mDatabase.getReference("Routine11"); // 변경값을 확인할 child 이름
        mReferenceName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    String str = messageData.getValue().toString();
                    Log.i("Routine11",str);
                    koreanName.add(str);
                }
            }

            //세트랑 횟수

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //파이어베이스에 있는 Routine1 = '하체 운동 루틴' 가져오기
        mReference = mDatabase.getReference("Routine1"); // 변경값을 확인할 child 이름
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
                        setVideo(videolist.get(0), koreanName.get(currvideo));
                        count++;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        //세트랑 받아오기
        mReference2 = mDatabase2.getReference("Routine1_set"); // 변경값을 확인할 child 이름
        mReference2.addValueEventListener(new ValueEventListener() {
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
        });



    } //oncreate 끝


    public void setVideo(int id, String exkoreanName)
    {

        TextView exname1 = (TextView) findViewById(R.id.exname);
        exname1.setText(exkoreanName);

        String uriPath
                = "android.resource://"
                + getPackageName() + "/" + id;
        Uri uri = Uri.parse(uriPath);
        vw.setVideoURI(uri);
        vw.start();
        vw.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            public void onCompletion(MediaPlayer mp) {

                try{
                    mp.release();

                }catch(Exception e){
                    e.printStackTrace();
                }

            }

        });
        vw.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override

            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }

        });

        //함수호출


    }




    //팝업창 비디오가 끝나면 불림
    @Override
    public void onCompletion(MediaPlayer mediapalyer)
    {

        this.mediapalyer = mediapalyer;
        AlertDialog.Builder obj = new AlertDialog.Builder(this);
        obj.setTitle("Playback Finished!");
        obj.setIcon(R.mipmap.ic_launcher);
        Beginner_Routine.MyListener m = new Beginner_Routine.MyListener();
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
            else { //팝업창에서 next가 눌리면 들어옴
                countDown(stringSec, SetList.get(videocount));
                ++currvideo;
                nextex.setImageResource(nextExList.get(videocount));
                //String s=Integer.toString(currvideo);
                //Log.i("butter",s);
                if (currvideo == videolist.size())
                    currvideo = 0;
                setVideo(videolist.get(currvideo), koreanName.get(currvideo)); //currvideo가 비디오 카운트랑 똑같 index라고 생각하면 될듯
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

        // nextex.setImageResource(nextExList.get(videocount));

        textview_second.setVisibility(View.VISIBLE);
        textview_set.setVisibility(View.VISIBLE);
        secondtxt.setVisibility(View.VISIBLE);
        settxt.setVisibility(View.VISIBLE);
        fighting.setVisibility(View.VISIBLE);
        exname.setVisibility(View.VISIBLE);
        nextbtn.setVisibility(View.VISIBLE);
        rest.setVisibility(View.INVISIBLE);
        textview_set.setText(set);


        if(videocount%2==1) {
            textview_second.setVisibility(View.INVISIBLE);
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

    private int done() {
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