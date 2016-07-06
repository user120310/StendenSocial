package com.example.codru.stendensocial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class AddEvent extends AppCompatActivity {

    String name;
    String place;
    String time;
    String description;
    String date;
    EditText n;
    EditText y;
    EditText c;
    EditText m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        n = (EditText)findViewById(R.id.name);
        y = (EditText)findViewById(R.id.place);
        c = (EditText)findViewById(R.id.time);
        m = (EditText)findViewById(R.id.description);
        String day = getIntent().getStringExtra("date");
        TextView baka = (TextView)findViewById(R.id.Date);
        if(day == null){
            Calendar g = Calendar.getInstance();

            baka.setText(g.get(Calendar.DAY_OF_MONTH) + "-" + (g.get(Calendar.MONTH)+1)
                             + "-" + g.get(Calendar.YEAR)) ;
        }
        else {
            baka.setText(day);
        }
        date = day;
        name = n.getText().toString();
        place = y.getText().toString();
        time = c.getText().toString();
        description = m.getText().toString();
    }

    public void addEvent(View c){

    }
}
