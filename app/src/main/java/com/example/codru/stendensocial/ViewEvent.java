package com.example.codru.stendensocial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewEvent extends AppCompatActivity {

    ArrayList<String> discussion = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    EditText item;
    String day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        discussion.add("eventOne");

        day = getIntent().getStringExtra("date");

        ListView items = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(items.getContext(), android.R.layout.simple_list_item_1, discussion);
        items.setAdapter(adapter);
        item = (EditText) findViewById(R.id.item);

        items.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent g = new Intent(ViewEvent.this,ShowEvent.class);
                g.putExtra("name",discussion.get(i));
                g.putExtra("day",day);
                startActivity(g);
            }

        });
    }
}
