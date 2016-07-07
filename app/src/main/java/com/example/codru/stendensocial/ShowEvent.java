package com.example.codru.stendensocial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

public class ShowEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event);

        String day = getIntent().getStringExtra("day");
        String eventName = getIntent().getStringExtra("name");

        TextView date = (TextView) findViewById(R.id.date);
        TextView name = (TextView) findViewById(R.id.name);

        if(day == null){
            Calendar g = Calendar.getInstance();

            date.setText(g.get(Calendar.DAY_OF_MONTH) + "/" + (g.get(Calendar.MONTH)+1)
                    + "/" + g.get(Calendar.YEAR));
            name.setText(eventName);
        }
        else {
            date.setText(day);
            name.setText(eventName);
        }

    }

    public void attending(View h){

    }

    public void viewAttending(View i){
        Intent j = new Intent(ShowEvent.this,Attending.class);
        startActivity(j);
    }

    public void maybeAttending(View k){

    }
}
