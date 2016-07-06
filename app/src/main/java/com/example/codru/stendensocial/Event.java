package com.example.codru.stendensocial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

public class Event extends AppCompatActivity {

    CalendarView calendar;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        calendar = (CalendarView) findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override

            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                date = dayOfMonth + "/" + month + "/" + year;
            }
        });
    }

        public void add(View a){
            Intent b = new Intent(Event.this, AddEvent.class);
            b.putExtra("date", date);
            startActivity(b);
    }
    }

