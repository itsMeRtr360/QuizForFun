package com.example.quizforfun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class passwordChange extends AppCompatActivity {
    EditText oldPasswordField;
    EditText newPasswordField;
    Button changePasswordButton;
    Button changeCancelButton;
    databasehelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setTitle("Change password");
        setContentView(R.layout.activity_password_change);
        oldPasswordField=(EditText)findViewById(R.id.changePasswordOld);
        newPasswordField=(EditText)findViewById(R.id.changePasswordNew);
        changeCancelButton=(Button)findViewById(R.id.changePasswordButton);
        changePasswordButton=(Button)findViewById(R.id.changeCancel);
        changeCancelButton.setOnClickListener(listener);
        changePasswordButton.setOnClickListener(listener);
        myDB=new databasehelper(getApplicationContext(),null,null,1);
    }
    private View.OnClickListener listener=(v)->{
        switch(v.getId()) {
            case R.id.changePasswordButton:
                changePassword();
                break;
            case R.id.changeCancel:
                Intent intent = new Intent(this, startScreen.class);
                startActivity(intent);
        }
    };

    private void changePassword() {
        String currentPassword=oldPasswordField.getText().toString();
        if(currentPassword.equals(getCurrentPassword(MainActivity.userName)))
        {
            if(newPasswordField.getText().toString().trim()!="") {
                myDB.updateUserTable(MainActivity.userName, null, null, newPasswordField.getText().toString());
                Toast.makeText(this,"Password changed successfully", Toast.LENGTH_SHORT).show();
                resetFields();
            }
        }
        Toast.makeText(this,"Wrong current password", Toast.LENGTH_SHORT).show();
    }

    private void resetFields() {
        oldPasswordField.setText("");
        newPasswordField.setText("");
    }

    private String getCurrentPassword(String userName) {
        String password="";
        Cursor res=myDB.viewRecordUserTable(userName);
            if(res.moveToFirst())
            {
                password=res.getString(3);
            }
            return password;
    }
}