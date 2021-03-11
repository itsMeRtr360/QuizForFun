package com.example.quizforfun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class viewPoints extends AppCompatActivity {
    TextView viewPointUsername;
    TextView attemptsView;
    databasehelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setTitle("Your score details");
        setContentView(R.layout.activity_view_points);
        viewPointUsername=(TextView)findViewById(R.id.viewPointUsername);
        attemptsView=(TextView)findViewById(R.id.attemptsView);
        viewPointUsername.setText("Hi "+MainActivity.userName+",");
        myDB=new databasehelper(getApplicationContext(),null,null,1);
        setAttempts();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.submenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sortDate:
                Cursor res = myDB.viewAllRecordsScoreByArea(MainActivity.userName);
                StringBuffer buffer = new StringBuffer();
                int i = 1;
                buffer.append("You have earned " +
                        myDB.getTotalScore(MainActivity.userName) + " points in the following attempts.\n");
                if (res.getColumnCount() != 0) {
                    while (res.moveToNext()) {
                        buffer.append(i + "." + '"' + res.getString(1) + '"' + " area \n");
                        buffer.append("   attempt started on :" + res.getString(3) + "\n");
                        buffer.append("   points earned :" + res.getString(2) + "\n");
                        i++;
                    }
                    attemptsView.setText(buffer.toString());
                } else {
                    attemptsView.setText("You are yet to earn points. Attempt quiz to earn points.");
                }
               return true;
            case R.id.sortArea:
                Cursor res1 = myDB.viewAllRecordsScoreSortedByDate(MainActivity.userName);
                StringBuffer buffer1 = new StringBuffer();
                i = 1;
                buffer1.append("You have earned " +
                        myDB.getTotalScore(MainActivity.userName) + " points in the following attempts.\n");
                if (res1.getColumnCount() != 0) {
                    while (res1.moveToNext()) {
                        buffer1.append(i + "." + '"' + res1.getString(1) + '"' + " area \n");
                        buffer1.append("   attempt started on :" + res1.getString(3) + "\n");
                        buffer1.append("   points earned :" + res1.getString(2) + "\n");
                        i++;
                    }
                    attemptsView.setText(buffer1.toString());
                } else {
                    attemptsView.setText("You are yet to earn points. Attempt quiz to earn points.");
                }
                return true;
            case R.id.back:
                Intent intent=new Intent(this,startScreen.class);
                startActivity(intent);
                finish();
                return true;
        }
        return true;
    }
    private void setAttempts(){
        Cursor res=myDB.viewRecordScoreTable(MainActivity.userName);
        StringBuffer buffer = new StringBuffer();
        int i=1;
        buffer.append("You have earned "+
                myDB.getTotalScore(MainActivity.userName)+" points in the following attempts.\n");
        if(res.getColumnCount()!=0)
        {
            while(res.moveToNext())
            {
                buffer.append(i+"."+'"'+res.getString(1)+'"'+" area \n");
                buffer.append("   attempt started on :"+res.getString(3)+"\n");
                buffer.append("   points earned :"+res.getString(2)+"\n");
                i++;
            }
            attemptsView.setText(buffer.toString());
        }
        else
        {
            attemptsView.setText("You are yet to earn points. Attempt quiz to earn points.");
        }

    }
}