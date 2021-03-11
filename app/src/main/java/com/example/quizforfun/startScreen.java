package com.example.quizforfun;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class startScreen extends AppCompatActivity {
    TextView welcomeNote;
    databasehelper myDB;
    FrameLayout math;
    FrameLayout geography;
    FrameLayout literature;
    static String selection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setTitle("QUIZ FOR FUN");
        setContentView(R.layout.start_screen);
        welcomeNote=(TextView)findViewById(R.id.welcomeNote);
        welcomeNote.setText("Welcome "+MainActivity.userName);
        math=(FrameLayout)findViewById(R.id.math);
        literature=(FrameLayout)findViewById(R.id.literature);
        geography=(FrameLayout)findViewById(R.id.geography);
        math.setOnClickListener(listener);
        literature.setOnClickListener(listener);
        geography.setOnClickListener(listener);
        myDB=new databasehelper(getApplicationContext(),null,null,1);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public void onBackPressed() {
       Intent intent=new Intent(this,MainActivity.class);
       startActivity(intent);
       finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.viewPoints:
                Intent intent = new Intent(this, viewPoints.class);
                startActivity(intent);
                return true;
            case R.id.changePassword:
                Intent intent1 = new Intent(this, passwordChange.class);
                startActivity(intent1);
                return true;
            case R.id.logOff:
                String message=MainActivity.userName+", you have overall ";
                int score=myDB.getTotalScore(MainActivity.userName);
                    if(score==0)
                        showMessage("Summary",message+String.format("0 points.\nAttempt quiz to get points"));
                    else {
                        showMessage("Summary", message + score + " points.\nSee you soon!");
                    }
                return true;
            case R.id.help:
                AlertDialog.Builder builder = new AlertDialog.Builder(startScreen.this);
                builder.setCancelable(true);
                builder.setTitle("Instructions/Rules");
                builder.setMessage(String.format("Start by choosing the Area(Math, Geography or Literature)" +
                        "\nYou will have 5 multiple choice questions for each try.\n5 marks are allocated for each right answer and -2 marks for each " +
                        "wrong answer.\nUn-attempted questions will be considered as wrong answer.\nYour total score will be calculated and displayed once you submit the quiz. \nyou can see the history of your attempts" +
                        " from the options menu. \nHappy quiz time!!"));
                builder.show();
                return true;
                }
        return super.onOptionsItemSelected(item);
    }
    private final View.OnClickListener listener=
            (v)->{
            switch(v.getId())
            {
                case R.id.math:
                    selection="Maths";
                    Intent intent=new Intent(this,MathActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.geography:
                    selection="Geography";
                    Intent intent1=new Intent(this,Quiz.class);
                    startActivity(intent1);
                    finish();
                    break;
                case R.id.literature:
                    selection="Literature";
                    Intent intent2=new Intent(this,Quiz.class);
                    startActivity(intent2);
                    finish();
                    break;
            }

    };
    public void showMessage(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(startScreen.this);
        builder.setCancelable(true);
        builder.setOnCancelListener(l->{
            Intent intent2 = new Intent(this, MainActivity.class);
            startActivity(intent2);
            finish();
        });
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();
    }
}