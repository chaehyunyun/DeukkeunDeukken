package com.example.mylogin;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class ExerciseItemView extends LinearLayout {

    TextView textView;

    // Generate > Constructor

    public ExerciseItemView(Context context) {
        super(context);

        init(context);
    }

    public ExerciseItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }



    // singer_item.xml을 inflation
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.exercise_item, this, true);

        textView = (TextView) findViewById(R.id.textView);
    }

    public void setName(String mobile) {
        textView.setText(mobile);
    }

}
