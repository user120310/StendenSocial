package com.example.codru.stendensocial;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ImageButton;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.net.*;

/*
 * Made by Marin Codrut, Tim Vaneker, Smith Chen and Flavian Gall
 */

    public class MessageBoard extends ListActivity
    {
        ArrayList<Integer> userIds = new ArrayList<Integer>();
        ArrayList<Integer> itemIds = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> messages = new ArrayList<>();
        ArrayList<String> discussion = new ArrayList<String>();
        ArrayAdapter<String> adapter;
        String username;
        String StendenMail;
        String topics;
        int i = 0;
        int j = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_board);
        final String[] takeResult = {""};
        topics = getIntent().getStringExtra("Messages");
        Thread read = new Thread(new Runnable() {
            int i = 0;
            int j = 0;
            String t = "";
            @Override
            public void run() {
                Looper.prepare();
                while (true) {
                    try {
                        String login_url = "http://192.168.43.164/message2.php";

                        URL url = new URL(login_url);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);
                        //OutputStream outputStream = httpURLConnection.getOutputStream();
                        // BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                        // String post_data ="";
                        // bufferedWriter.write(post_data);
                        // bufferedWriter.flush();
                        // bufferedWriter.close();
                        //   outputStream.close();
                        InputStream inputStream = httpURLConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));//iso-8859-1
                        String result = "";
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            result += line;
                        }
                        bufferedReader.close();
                        inputStream.close();
                        httpURLConnection.disconnect();
                        t = result;
                    } catch (Exception ex) {
                        AlertDialog d = new AlertDialog.Builder(MessageBoard.this).create();
                        d.setTitle("DatabaseUpload");
                        d.setMessage(ex.getMessage());
                        d.show();
                    }
                    i++;
                    if(i == 5)
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(!topics.equals(t) || j ==0)
                                {
                                    j++;
                                    topics = t;
                                    messages.clear();
                                    userIds.clear();
                                    itemIds.clear();
                                    names.clear();
                                    if(topics != null) {
                                        String[] msg = topics.split("</br>");
                                        int n = msg.length;
                                        int j = 0;
                                        for (int i = 0; i < n; i++) {
                                            switch (j) {
                                                case 0:
                                                    userIds.add(Integer.parseInt(msg[i]));
                                                    break;
                                                case 1:
                                                    itemIds.add(Integer.parseInt(msg[i]));
                                                    break;
                                                case 2:
                                                    names.add(msg[i]);
                                                    break;
                                                case 3:
                                                    messages.add(msg[i]);
                                                    break;
                                            }
                                            j++;
                                            j = j % 4;
                                        }
                                        discussion.clear();
                                        Iterator<String> it = messages.iterator();
                                        while (it.hasNext()) {
                                            discussion.add(it.next());
                                            adapter.notifyDataSetChanged();
                                        }
                                    }
                                }
                            }
                        });
                        i = i % 5;
                    }
                }
            }
        });
        read.start();
        adapter = new ArrayAdapter<String>(getListView().getContext(), android.R.layout.simple_list_item_1, discussion);
        getListView().setAdapter(adapter);
        username = getIntent().getStringExtra("Username");
        StendenMail = getIntent().getStringExtra("StendenMail");
        final Topic t = new Topic(this);
        ListView discussions = (ListView) findViewById(android.R.id.list);
        discussions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapter.getItem(i);
                String userId = "" + userIds.get(i);
                String itemId = "" + itemIds.get(i);
                String name = names.get(i);
                t.execute("topic",item,userId,itemId,name,StendenMail,username,topic());
               // Intent discussion = new Intent(MessageBoard.this,Discussion.class);
                //discussion.putExtra("Subject",item);
               // discussion.putExtra("UserID",userId);
               // discussion.putExtra("ItemID",itemId);
               // discussion.putExtra("Username",name);
               // startActivity(discussion);
            }
        });
        Toolbar backTool = (Toolbar) findViewById(R.id.toolbar);


    }

        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {

                Intent i = new Intent(MessageBoard.this,Welcome.class);
                i.putExtra("Username",username);
                i.putExtra("StedenMail",username);
                finish();
                startActivity(i);
            }
            return true;
        }


    public void addDiscussion(View v)
    {
        ArrayList<String> revDis = new ArrayList<>();
        Iterator<String> its = discussion.iterator();
        while(its.hasNext())
        {
            revDis.add(its.next());
        }
        EditText item = (EditText) findViewById(R.id.item);
        int i = 0;
        if(item != null && (item.getText().toString()).length() > 0) {
            String discussions = item.getText().toString();
            int sw = 0;
            Iterator<String> it = messages.iterator();
            while(it.hasNext() && sw == 0)
            {
                if(it.next().equals(discussions))
                {
                    sw = 1;
                }
            }

            if(discussions.length() > 0 && sw == 0)
            {
                UploadMessages upmsg = new UploadMessages(this);
                upmsg.execute("Topic",StendenMail,discussions);
                discussion.clear();
                discussion.add(discussions);
                adapter.notifyDataSetChanged();
                for(int j = 0; j < revDis.size(); j++)
                {
                    discussion.add(revDis.get(j));
                    adapter.notifyDataSetChanged();
                }

                item.setText("");
            }else{
                item.setText("");
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Switch error!");
                alertDialog.setMessage("You already added a topic like this please try again with another one if you want!");
                alertDialog.show();
            }
        }
    }

        public void onToolbarClick(View v)
        {
            Intent i = new Intent(MessageBoard.this,Welcome.class);
            i.putExtra("Username",username);
            i.putExtra("StendenMail",StendenMail);
            startActivity(i);
        }

        public String topic()
        {
            String t = "";
            for(int k = 0; k < discussion.size(); k++)
            {
                t += userIds.get(k)+"</br>"+itemIds.get(k)+"</br>"+names.get(k)+"</br>"+discussion.get(k)+"</br>";
            }
            return t;
        }


}
