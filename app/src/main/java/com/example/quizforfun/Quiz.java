package com.example.quizforfun;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.style.QuoteSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static java.util.Collections.shuffle;

public class Quiz extends AppCompatActivity {


    private static String[] answerArray;
    TextView q1View,q2View,q3View,q4View,q5View;
    RadioGroup Q1Options,Q2Options,Q3Options,Q4Options,Q5Options;
    RadioButton Q1OptionsA,Q1OptionsB,Q1OptionsC,Q1OptionsD;
    RadioButton Q2OptionsA,Q2OptionsB,Q2OptionsC,Q2OptionsD;
    RadioButton Q3OptionsA,Q3OptionsB,Q3OptionsC,Q3OptionsD;
    RadioButton Q4OptionsA,Q4OptionsB,Q4OptionsC,Q4OptionsD;
    RadioButton Q5OptionsA,Q5OptionsB,Q5OptionsC,Q5OptionsD;
    static String time;
    databasehelper myDB;
    Button submitButton;
    int right=0;
    int wrong=0;
    int score=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        super.setTitle(startScreen.selection);
        q1View = (TextView) findViewById(R.id.q1View);
       // q1Options=(TextView)findViewById(R.id.q1Options);

        Q1Options=(RadioGroup)findViewById(R.id.Q1Options);
        Q1OptionsA=(RadioButton)findViewById(R.id.Q1OptionsA);
        Q1OptionsB=(RadioButton)findViewById(R.id.Q1OptionsB);
        Q1OptionsC=(RadioButton)findViewById(R.id.Q1OptionsC);
        Q1OptionsD=(RadioButton)findViewById(R.id.Q1OptionsD);



        q2View=(TextView)findViewById(R.id.q2View);
        Q2Options=(RadioGroup) findViewById(R.id.Q2Options);
        Q2OptionsA=(RadioButton)findViewById(R.id.Q2OptionsA);
        Q2OptionsB=(RadioButton)findViewById(R.id.Q2OptionsB);
        Q2OptionsC=(RadioButton)findViewById(R.id.Q2OptionsC);
        Q2OptionsD=(RadioButton)findViewById(R.id.Q2OptionsD);

        q3View=(TextView)findViewById(R.id.q3View);
        Q3Options=(RadioGroup) findViewById(R.id.Q3Options);
        Q3OptionsA=(RadioButton)findViewById(R.id.Q3OptionsA);
        Q3OptionsB=(RadioButton)findViewById(R.id.Q3OptionsB);
        Q3OptionsC=(RadioButton)findViewById(R.id.Q3OptionsC);
        Q3OptionsD=(RadioButton)findViewById(R.id.Q3OptionsD);

        q4View=(TextView)findViewById(R.id.q4View);
        Q4Options=(RadioGroup) findViewById(R.id.Q4Options);
        Q4OptionsA=(RadioButton)findViewById(R.id.Q4OptionsA);
        Q4OptionsB=(RadioButton)findViewById(R.id.Q4OptionsB);
        Q4OptionsC=(RadioButton)findViewById(R.id.Q4OptionsC);
        Q4OptionsD=(RadioButton)findViewById(R.id.Q4OptionsD);

        q5View=(TextView)findViewById(R.id.q5View);
        Q5Options=(RadioGroup) findViewById(R.id.Q5Options);

        Q5OptionsA=(RadioButton)findViewById(R.id.Q5OptionsA);
        Q5OptionsB=(RadioButton)findViewById(R.id.Q5OptionsB);
        Q5OptionsC=(RadioButton)findViewById(R.id.Q5OptionsC);
        Q5OptionsD=(RadioButton)findViewById(R.id.Q5OptionsD);

        submitButton=(Button)findViewById(R.id.finishAttemptButton);
        myDB=new databasehelper(getApplicationContext(),null,null,1);

        submitButton.setOnClickListener(clicklistener);
        answerArray=new String[5];
        startQuiz();

    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    CalculateScore();
                    break;


                case DialogInterface.BUTTON_NEGATIVE:
                    dialog.dismiss();
                    break;
            }
        }
    };

    private View.OnClickListener clicklistener=
            (v)->{
                switch(v.getId())
                {
                    case R.id.finishAttemptButton:
                        AlertDialog.Builder builder = new AlertDialog.Builder(Quiz.this);
                        builder.setMessage("Are you sure to submit?").setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No",dialogClickListener).show();
                        break;
                }
            };
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Quiz.this);
        builder.setMessage("Are you sure to leave and submit?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No",dialogClickListener).show();
    }

    public void CalculateScore() {
        int selectedID1=-1,selectedID2=-1,selectedID3=-1,selectedID4=-1,selectedID5=-1;
        if(startScreen.selection=="Geography"||startScreen.selection=="Literature") {
            selectedID1=Q1Options.getCheckedRadioButtonId();
            selectedID2=Q2Options.getCheckedRadioButtonId();
            selectedID3=Q3Options.getCheckedRadioButtonId();
            selectedID4=Q4Options.getCheckedRadioButtonId();
            selectedID5=Q5Options.getCheckedRadioButtonId();
            RadioButton a1=(RadioButton)findViewById(selectedID1);
            RadioButton a2=(RadioButton)findViewById(selectedID2);
            RadioButton a3=(RadioButton)findViewById(selectedID3);
            RadioButton a4=(RadioButton)findViewById(selectedID4);
            RadioButton a5=(RadioButton)findViewById(selectedID5);

        if(selectedID1!=-1) {
            if (a1.getText().equals(answerArray[0]))
                right += 1;
            else
                wrong += 1;
        }
        else
            wrong+=1;
        if(selectedID2!=-1) {
            if (a2.getText().toString().equals(answerArray[1]))
                right += 1;
            else
                wrong += 1;
        }
        else
            wrong+=1;
        if(selectedID3!=-1) {
            if (a3.getText().toString().equals(answerArray[2]))
                right += 1;
            else
                wrong += 1;
        }
        else
            wrong+=1;
        if(selectedID4!=-1) {
            if (a4.getText().toString().equals(answerArray[3]))
                right += 1;
            else
                wrong += 1;
        }
        else
            wrong+=1;
        if(selectedID5!=-1) {
            if (a5.getText().toString().equals(answerArray[4]))
                right += 1;
            else
                wrong += 1;
        }
        else
            wrong+=1;
        }
        score=right*5+wrong*-2;
        String CORRECT=String.valueOf(right);
        String WRONG=String.valueOf(wrong);
        String TOTAL=String.valueOf(score);
        myDB.insertDataScoreTable(MainActivity.userName,startScreen.selection,Quiz.time,score);
        Intent intent=new Intent(this,summary.class);
        intent.putExtra("SCORE",TOTAL);
        intent.putExtra("RIGHT",CORRECT);
        intent.putExtra("WRONG",WRONG);
        startActivity(intent);
        finish();
    }


    private void startQuiz() {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        time = date.format(dateFormat);
        if (startScreen.selection == "Geography") {
            Set<Cursor> questionSet = new HashSet<Cursor>();
            Random rand = new Random();
            ArrayList<Integer>trackList=new ArrayList <Integer>();
            while (questionSet.size() < 5) {
                int x = rand.nextInt(14)+1;
                if(!trackList.contains(x))
                    questionSet.add(myDB.getGeoQuestion(x));
                trackList.add(x);
            }
            Cursor[] uniqueArray = new Cursor[questionSet.size()];
            int i = 0;
            for (Cursor q : questionSet) {
                if (q != null && q.moveToFirst()) {
                    uniqueArray[i] = q;
                    answerArray[i] = q.getString(6);
                    i++;
                }
            }
            q1View.setText(String.format("1)%s", uniqueArray[0].getString(1)));
            //StringBuffer buffer = new StringBuffer();
            //String [] options1= uniqueArray[0].getString(2);
            Q1OptionsA.setText(uniqueArray[0].getString(2));
            Q1OptionsB.setText(uniqueArray[0].getString(3));
            Q1OptionsC.setText(uniqueArray[0].getString(4));
            Q1OptionsD.setText(uniqueArray[0].getString(5));

            //q1Options.setText(buffer.toString());
            // Q1OptionsA.setText("Hello");
            // Q1OptionsB.setText("Hii");
            // buffer.delete(0, buffer.length());

            q2View.setText(String.format("2)%s", uniqueArray[1].getString(1)));
            //String [] options2= uniqueArray[1].getOptions();
            Q2OptionsA.setText(uniqueArray[1].getString(2));
            Q2OptionsB.setText(uniqueArray[1].getString(3));
            Q2OptionsC.setText(uniqueArray[1].getString(4));
            Q2OptionsD.setText(uniqueArray[1].getString(5));

            // buffer.delete(0, buffer.length());

            q3View.setText(String.format("3)%s", uniqueArray[2].getString(1)));
            //String [] options3= uniqueArray[2].getOptions();
            Q3OptionsA.setText(uniqueArray[2].getString(2));
            Q3OptionsB.setText(uniqueArray[2].getString(3));
            Q3OptionsC.setText(uniqueArray[2].getString(4));
            Q3OptionsD.setText(uniqueArray[2].getString(5));
            // buffer.delete(0, buffer.length());

            q4View.setText(String.format("4)%s", uniqueArray[3].getString(1)));
            //String [] options4= uniqueArray[3].getOptions();
            Q4OptionsA.setText(uniqueArray[3].getString(2));
            Q4OptionsB.setText(uniqueArray[3].getString(3));
            Q4OptionsC.setText(uniqueArray[3].getString(4));
            Q4OptionsD.setText(uniqueArray[3].getString(5));

            //buffer.delete(0, buffer.length());

            q5View.setText(String.format("5)%s", uniqueArray[4].getString(1)));
            //String [] options5= uniqueArray[4].getOptions();
            Q5OptionsA.setText(uniqueArray[4].getString(2));
            Q5OptionsB.setText(uniqueArray[4].getString(3));
            Q5OptionsC.setText(uniqueArray[4].getString(4));
            Q5OptionsD.setText(uniqueArray[4].getString(5));
        }
        if (startScreen.selection == "Literature") {
            Set<Cursor> questionSet = new HashSet<Cursor>();
            Random rand = new Random();
            ArrayList<Integer>trackList=new ArrayList <Integer>();
            while (questionSet.size() < 5) {
                int x = rand.nextInt(14)+1;
                if(!trackList.contains(x))
                    questionSet.add(myDB.getLitQuestion(x));
                trackList.add(x);
            }
            Cursor[] uniqueArray = new Cursor[questionSet.size()];
            int i = 0;
            for (Cursor q : questionSet) {
                if (q != null && q.moveToFirst()) {
                    uniqueArray[i] = q;
                    answerArray[i] = q.getString(6);
                    i++;
                }
            }
            q1View.setText(String.format("1)%s", uniqueArray[0].getString(1)));
            //StringBuffer buffer = new StringBuffer();
            //String [] options1= uniqueArray[0].getString(2);
            Q1OptionsA.setText(uniqueArray[0].getString(2));
            Q1OptionsB.setText(uniqueArray[0].getString(3));
            Q1OptionsC.setText(uniqueArray[0].getString(4));
            Q1OptionsD.setText(uniqueArray[0].getString(5));

            //q1Options.setText(buffer.toString());
            // Q1OptionsA.setText("Hello");
            // Q1OptionsB.setText("Hii");
            // buffer.delete(0, buffer.length());

            q2View.setText(String.format("2)%s", uniqueArray[1].getString(1)));
            //String [] options2= uniqueArray[1].getOptions();
            Q2OptionsA.setText(uniqueArray[1].getString(2));
            Q2OptionsB.setText(uniqueArray[1].getString(3));
            Q2OptionsC.setText(uniqueArray[1].getString(4));
            Q2OptionsD.setText(uniqueArray[1].getString(5));

            // buffer.delete(0, buffer.length());

            q3View.setText(String.format("3)%s", uniqueArray[2].getString(1)));
            //String [] options3= uniqueArray[2].getOptions();
            Q3OptionsA.setText(uniqueArray[2].getString(2));
            Q3OptionsB.setText(uniqueArray[2].getString(3));
            Q3OptionsC.setText(uniqueArray[2].getString(4));
            Q3OptionsD.setText(uniqueArray[2].getString(5));
            // buffer.delete(0, buffer.length());

            q4View.setText(String.format("4)%s", uniqueArray[3].getString(1)));
            //String [] options4= uniqueArray[3].getOptions();
            Q4OptionsA.setText(uniqueArray[3].getString(2));
            Q4OptionsB.setText(uniqueArray[3].getString(3));
            Q4OptionsC.setText(uniqueArray[3].getString(4));
            Q4OptionsD.setText(uniqueArray[3].getString(5));

            //buffer.delete(0, buffer.length());

            q5View.setText(String.format("5)%s", uniqueArray[4].getString(1)));
            //String [] options5= uniqueArray[4].getOptions();
            Q5OptionsA.setText(uniqueArray[4].getString(2));
            Q5OptionsB.setText(uniqueArray[4].getString(3));
            Q5OptionsC.setText(uniqueArray[4].getString(4));
            Q5OptionsD.setText(uniqueArray[4].getString(5));
        }
    }

    public void showMessage(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Quiz.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();
        builder.setOnCancelListener(l->{
            Intent intent = new Intent(this, startScreen.class);
            startActivity(intent);
        });

    }

}



