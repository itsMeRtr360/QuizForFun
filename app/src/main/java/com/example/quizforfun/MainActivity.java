package com.example.quizforfun;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static java.lang.System.exit;

public class MainActivity extends AppCompatActivity {
    databasehelper myDB;
    Button logInButton;
    Button registerButton;
    EditText userNameField;
    EditText passwordField;
    public static String userName;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setTitle("Welcome");
        setContentView(R.layout.activity_main);
        myDB=new databasehelper(getApplicationContext(),null,null,1);
        logInButton=(Button)findViewById(R.id.logInButton);
        registerButton=(Button)findViewById(R.id.registerButton);
        userNameField=(EditText)findViewById(R.id.userNameField);
        passwordField=(EditText)findViewById(R.id.passwordField);
        logInButton.setOnClickListener(listener);
        registerButton.setOnClickListener(listener);
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter dateFormat=DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate=date.format(dateFormat);
    }
    private  final View.OnClickListener listener=
            (v)->
            {
                switch(v.getId()) {
                    case R.id.logInButton:
                        logIn();
                        break;
                    case R.id.registerButton:
                        register();
                        break;

                }
            };

    private void register() {
        Intent intent=new Intent(this,registerScreen.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("No", null).show();
    }
    private void logIn()
    {
        Cursor res=myDB.viewRecordUserTable(userNameField.getText().toString());
            if(res.moveToFirst())
            {
                if(passwordField.getText().toString().trim().equals(res.getString(3).trim()))
                {
                    userName=userNameField.getText().toString();
                    Intent intent=new Intent(this,startScreen.class);
                    startActivity(intent);
                    finish();
                }
                else
                    Toast.makeText(MainActivity.this,"Password not correct!! Try again..",Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(MainActivity.this,"Username doesn't exist!!",Toast.LENGTH_LONG).show();
        }
    }
