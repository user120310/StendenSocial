package com.example.codru.stendensocial;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/*
 * Made by Marin Codrut, Tim Vaneker, Smith Chen and Flavian Gall
 */

public class SignIn extends AppCompatActivity {
    EditText username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.pass);
    }

    public Intent i;

    public void onClick(View v)
    {
        String mail = username.getText().toString();
        String pass = password.getText().toString();
        if(mail.contains("@student.stenden.com") || mail.contains("@stenden.com") || mail.contains("admin"))
        {
            if(pass.length() >= 5 && pass != null)
            {
                String type = "login";
                BackgroundWork backgroundWork = new BackgroundWork(this);
                backgroundWork.execute(type,mail,pass);
            }else{
                AlertDialog ad = new AlertDialog.Builder(this).create();
                ad.setTitle("Pass ERROR");
                ad.setMessage("You typed a password that is less than 5 characters!");
                ad.show();
            }

        }else{
            AlertDialog ad = new AlertDialog.Builder(this).create();
            ad.setTitle("Mail ERROR");
            ad.setMessage("You typed the mail wrong!");
            ad.show();
        }

    }

    public void signUp(View v)
    {
        i = new Intent(SignIn.this, SignUp.class);
        finish();
        startActivity(i);
    }
}
