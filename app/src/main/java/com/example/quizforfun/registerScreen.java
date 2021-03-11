package com.example.quizforfun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class registerScreen extends AppCompatActivity {
    Button submitButton;
    EditText regUsername;
    EditText regMail;
    EditText regPassword;
    EditText regNameField;
    databasehelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setTitle("Register");
        setContentView(R.layout.activity_register_screen);

        submitButton=(Button)findViewById(R.id.submitButton);
        regUsername=(EditText)findViewById(R.id.regUserNameField);
        regMail=(EditText)findViewById(R.id.regEmailField);
        regPassword=(EditText)findViewById(R.id.regPasswordField);
        regNameField=(EditText)findViewById(R.id.regNameField);
        myDB=new databasehelper(getApplicationContext(),null,null,1);

        submitButton.setOnClickListener(v -> {
            if(!regMail.getText().toString().trim().equals("") && !regPassword.getText().toString().trim().equals("")
                    && !regUsername.getText().toString().trim().equals("") && !regNameField.getText().toString().trim().equals(""))
            {
                Cursor res=myDB.viewRecordsUserTableByEmail(regMail.getText().toString().trim());
                if (!res.moveToFirst()) {
                    Cursor res1 = myDB.viewRecordUserTable(regUsername.getText().toString());
                    {
                        if (!res1.moveToFirst()) {
                            myDB.insertDataUserTable(regUsername.getText().toString(), regNameField.getText().toString(),
                                    regPassword.getText().toString(), regMail.getText().toString());
                            Toast.makeText(registerScreen.this,"Registration success! Please proceed to log in!",Toast.LENGTH_LONG).show();
                            resetFields();
                            Intent intent=new Intent(this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else
                            Toast.makeText(registerScreen.this,"Username already taken!! Please choose a new one",Toast.LENGTH_LONG).show();
                    }
                }
                else
                    Toast.makeText(registerScreen.this, "Email account already registered!!", Toast.LENGTH_LONG).show();
            }
            else
            Toast.makeText(registerScreen.this,"Please fill in all required fields",Toast.LENGTH_LONG).show();
        });
    }

    private void resetFields() {
        regNameField.setText("");
        regUsername.setText("");
        regPassword.setText("");
        regMail.setText("");
    }
}