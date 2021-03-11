package com.example.quizforfun;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class summary extends AppCompatActivity {
    static TextView totalScore,rightAns,wrongAns,overAll;
    static Button okButton;
    databasehelper myDB;
    ImageView anmBox;
    AnimationDrawable anm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setTitle("Summary");
        setContentView(R.layout.activity_summary);
        Intent secondIntent = getIntent();
        String score = secondIntent.getStringExtra("SCORE");
        String right = secondIntent.getStringExtra("RIGHT");
        String wrong = secondIntent.getStringExtra("WRONG");
        totalScore = (TextView) findViewById(R.id.scoreTotal);
        rightAns = (TextView) findViewById(R.id.correctAns);
        wrongAns = (TextView) findViewById(R.id.wrongAns);
        okButton = (Button) findViewById(R.id.OKButton);
        overAll=(TextView)findViewById(R.id.overAll);
        anmBox=(ImageView)findViewById(R.id.animationBox);
        anmBox.setBackgroundResource(R.drawable.animation);
        anm=(AnimationDrawable)anmBox.getBackground();
        anm.start();
        myDB=new databasehelper(getApplicationContext(),null,null,1);

        totalScore.setText(String.valueOf(score));
        rightAns.setText(String.valueOf(right));
        wrongAns.setText(String.valueOf(wrong));
        overAll.setText(String.valueOf( myDB.getTotalScore(MainActivity.userName)));
        okButton.setOnClickListener(listener);
    }

     View.OnClickListener listener=
            (v -> {
                switch(v.getId())
                {
                    case R.id.OKButton:
                        Intent intent=new Intent(this,startScreen.class);
                        startActivity(intent);
                        finish();
                }

            });
    @Override
    public void onBackPressed() {
       Intent intent=new Intent(this,startScreen.class);
       startActivity(intent);
       finish();
    }

}