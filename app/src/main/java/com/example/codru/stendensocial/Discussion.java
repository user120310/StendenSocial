package com.example.codru.stendensocial;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

/*
 * Made by Marin Codrut, Tim Vaneker, Smith Chen and Flavian Gall
 */

public class Discussion extends ListActivity {

    ArrayList<String> discussion = new ArrayList<String>();
    ArrayList<Integer> msgId = new ArrayList<>();
    ArrayList<Integer> userIds = new ArrayList<>();
    ArrayList<String> usernames = new ArrayList<>();
    ArrayList<String> mess = new ArrayList<>();
    ArrayAdapter<String> adapter;
    String username,StendenMail,TopicName,TopicID,userID,tusername,messages,Topicmsgs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);
        messages = getIntent().getStringExtra("Messages");
        Thread read = new Thread(new Runnable() {
            int i = 0;
            int m = 0;
            String t = "";
            @Override
            public void run() {
                Looper.prepare();
                while (true) {
                    try {
                        String login_url = "http://192.168.43.164/message1.php";

                        URL url = new URL(login_url);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);
                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                        String post_data = URLEncoder.encode("itemID","UTF-8")+"="+URLEncoder.encode(TopicID,"UTF-8");
                        bufferedWriter.write(post_data);
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        outputStream.close();
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
                        AlertDialog d = new AlertDialog.Builder(Discussion.this).create();
                        d.setTitle("DatabaseUpload");
                        d.setMessage(ex.getMessage());
                        d.show();
                    }
                    i++;
                    if(i == 2)
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(messages == null || (!messages.equals(t) || m == 0))
                                {
                                    m++;
                                    messages = t;
                                    msgId.clear();
                                    userIds.clear();
                                    usernames.clear();
                                    mess.clear();
                                    if(messages != null) {
                                        String[] msgs = messages.split("</br>");
                                        int n = msgs.length;
                                        int k = 0;
                                        if(n > 1) {
                                            for (int j = 0; j < n; j++) {
                                                switch (k) {
                                                    case 0:
                                                        msgId.add(Integer.parseInt(msgs[j]));
                                                        break;
                                                    case 1:
                                                        userIds.add(Integer.parseInt(msgs[j]));
                                                        break;
                                                    case 2:
                                                        usernames.add(msgs[j]);
                                                        break;
                                                    case 3:
                                                        mess.add(msgs[j]);
                                                        break;
                                                }
                                                k++;
                                                k = k % 4;
                                            }
                                            discussion.clear();
                                            for(int l = 0; l < mess.size(); l++)
                                            {
                                                discussion.add(usernames.get(l) + ": \r\n " +mess.get(l));
                                                adapter.notifyDataSetChanged();
                                            }
                                        }
                                    }
                                }
                            }
                        });
                        i = i % 2;
                    }
                }
            }
        });
        read.start();
        username = getIntent().getStringExtra("Username");
        StendenMail = getIntent().getStringExtra("StendenMail");
        TopicName = getIntent().getStringExtra("TopicName");
        TopicID = getIntent().getStringExtra("TopicID");
        userID = getIntent().getStringExtra("UserID");
        tusername = getIntent().getStringExtra("TUserName");
        Topicmsgs = getIntent().getStringExtra("Topicmsgs");
        TextView sub = (TextView) findViewById(R.id.Subject);
        TextView userName = (TextView) findViewById(R.id.UserName);

        if(userName != null)
        {
            userName.setText(tusername);
        }
        if (sub != null)
        {
            sub.setText(TopicName);
        }
        adapter = new ArrayAdapter<String>(getListView().getContext(), android.R.layout.simple_list_item_1, discussion);
        getListView().setAdapter(adapter);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
             if (keyCode == KeyEvent.KEYCODE_BACK) {

           // Intent i = new Intent(Discussion.this,MessageBoard.class);
           // i.putExtra("Username",username);
           // i.putExtra("StedenMail",username);
            //     i.putExtra("Messages",Topicmsgs);
            finish();
           // startActivity(i);
                 Messages msg = new Messages(this);
                 msg.execute("topic",username,StendenMail);

        }
        return true;
    }

    public void addComment(View view)
    {
        EditText comm = (EditText) findViewById(R.id.UserMsg);
        if(comm != null && (comm.getText().toString()).length() > 0)
        {
            DissMsgs dis = new DissMsgs(this);
            String comment = comm.getText().toString();
            if(comment.length() > 0)
            {
                dis.execute(userID,comment,TopicID,StendenMail);
                comm.setText("");
            }
        }
    }
}
