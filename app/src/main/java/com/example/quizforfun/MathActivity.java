package com.example.quizforfun;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MathActivity extends AppCompatActivity {
    TextView qn1,qn2,qn3,qn4,qn5;
    static String time;
    databasehelper myDB;
    Button submitButton;
    Random rand;
    Button mathSubmitButton;
    private static String[] answerArray=new String[5];
    int right=0,wrong=0,score=0;
    static EditText an1;
    static EditText an2;
    static EditText an3;
    static EditText an4;
    static EditText an5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setTitle("Maths");
        setContentView(R.layout.activity_math);
        qn1=(TextView)findViewById(R.id.qn1View);
        qn2=(TextView)findViewById(R.id.qn2View);
        qn3=(TextView)findViewById(R.id.qn3View);
        qn4=(TextView)findViewById(R.id.qn4View);
        qn5=(TextView)findViewById(R.id.qn5View);
        an1=(EditText)findViewById(R.id.ans1);
        an2=(EditText)findViewById(R.id.ans2);
        an3=(EditText)findViewById(R.id.ans3);
        an4=(EditText)findViewById(R.id.ans4);
        an5=(EditText)findViewById(R.id.ans5);
        mathSubmitButton=(Button)findViewById(R.id.mathSubmitButton);
        mathSubmitButton.setOnClickListener(clicklistener);
        rand=new Random();
        myDB=new databasehelper(getApplicationContext(),null,null,1);
        setMathsQuestionList();
    }
    public void setMathsQuestionList() {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        time = date.format(dateFormat);
        Set<Cursor> questionSet = new HashSet<Cursor>();
        Random rand = new Random();
        ArrayList<Integer>trackList=new ArrayList <Integer>();
        while (questionSet.size() < 5) {
            int x = rand.nextInt(14)+1;
            if(!trackList.contains(x))
                questionSet.add(myDB.getMathQuestion(x));
            trackList.add(x);
        }
        Cursor[] uniqueArray = new Cursor[questionSet.size()];
        int i = 0;
        for (Cursor q : questionSet) {
            if (q != null && q.moveToFirst()) {
                uniqueArray[i] = q;
                answerArray[i] = q.getString(2);
                i++;
            }
        }
        qn1.setText(String.format("1) %s",uniqueArray[0].getString(1)));
        qn2.setText(String.format("2) %s",uniqueArray[1].getString(1)));
        qn3.setText(String.format("3) %s",uniqueArray[2].getString(1)));
        qn4.setText(String.format("4) %s",uniqueArray[3].getString(1)));
        qn5.setText(String.format("5) %s",uniqueArray[4].getString(1)));
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MathActivity.this);
        builder.setMessage("Are you sure to leave and submit?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No",dialogClickListener).show();
    }
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    verifyEntry();
                    break;


                case DialogInterface.BUTTON_NEGATIVE:
                    dialog.dismiss();
                    break;
            }
        }
    };

        private void verifyEntry() {
            boolean valid=true;
            try {
                Integer.parseInt(an1.getText().toString());
                an1.setBackgroundColor(Color.GREEN);
            }
            catch (NumberFormatException e) {
                if(!an1.getText().toString().trim().equals("")) {
                    an1.setBackgroundColor(Color.RED);
                    an1.requestFocus();
                    Toast.makeText(MathActivity.this, "Not a valid entry", Toast.LENGTH_LONG).show();
                    valid = false;
                }
            }
            try {
                Integer.parseInt(an2.getText().toString());
                an2.setBackgroundColor(Color.GREEN);
            }
            catch (NumberFormatException e) {
                if(!an2.getText().toString().trim().equals("")) {
                    an2.setBackgroundColor(Color.RED);
                    an2.requestFocus();
                    Toast.makeText(MathActivity.this, "Not a valid entry", Toast.LENGTH_LONG).show();
                    valid = false;
                }
            }
            try {
                Integer.parseInt(an3.getText().toString());
                an3.setBackgroundColor(Color.GREEN);
            }
            catch (NumberFormatException e) {
                if(!an3.getText().toString().trim().equals("")) {
                    an3.setBackgroundColor(Color.RED);
                    an3.requestFocus();
                    Toast.makeText(MathActivity.this, "Not a valid entry", Toast.LENGTH_LONG).show();
                    valid = false;
                }
            }
            try {
                Integer.parseInt(an4.getText().toString());
                an4.setBackgroundColor(Color.GREEN);
            }
            catch (NumberFormatException e) {
                if(!an4.getText().toString().trim().equals("")) {
                    an4.setBackgroundColor(Color.RED);
                    an4.requestFocus();
                    Toast.makeText(MathActivity.this, "Not a valid entry", Toast.LENGTH_LONG).show();
                    valid = false;
                }
            }
            try {
                Integer.parseInt(an5.getText().toString());
                an5.setBackgroundColor(Color.GREEN);
            }
            catch (NumberFormatException e) {
                if(!an5.getText().toString().trim().equals("")) {
                    an5.setBackgroundColor(Color.RED);
                    an5.requestFocus();
                    Toast.makeText(MathActivity.this, "Not a valid entry", Toast.LENGTH_LONG).show();
                    valid = false;
                }
            }
            if(valid)
                CalculateScore();
        }


    private View.OnClickListener clicklistener=
            (v)->{
                switch(v.getId())
                {
                    case R.id.mathSubmitButton:
                        AlertDialog.Builder builder = new AlertDialog.Builder(MathActivity.this);
                        builder.setMessage("Are you sure to submit?").setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No",dialogClickListener).show();
                        break;
                }
            };
    public void CalculateScore() {
        if (an1.getText().toString().equals(answerArray[0]))
            right += 1;
        else
            wrong += 1;

        if (an2.getText().toString().equals(answerArray[1]))
            right += 1;
        else
            wrong += 1;
        if (an3.getText().toString().equals(answerArray[2]))
            right += 1;
        else
            wrong += 1;
        if (an4.getText().toString().equals(answerArray[3]))
            right += 1;
        else
            wrong += 1;
        if (an5.getText().toString().equals(answerArray[4]))
            right += 1;
        else
            wrong += 1;
    score=right*5+wrong*-2;
    String CORRECT=String.valueOf(right);
    String WRONG=String.valueOf(wrong);
    String TOTAL=String.valueOf(score);
        myDB.insertDataScoreTable(MainActivity.userName,startScreen.selection,MathActivity.time,score);
    Intent intent=new Intent(MathActivity.this,summary.class);
        intent.putExtra("SCORE",TOTAL);
        intent.putExtra("RIGHT",CORRECT);
        intent.putExtra("WRONG",WRONG);
    startActivity(intent);
    finish();;
    }
}