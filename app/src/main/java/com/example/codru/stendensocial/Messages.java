package com.example.codru.stendensocial;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;

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
import java.util.StringTokenizer;

/*
 * Made by Marin Codrut, Tim Vaneker, Smith Chen and Flavian Gall
 */
public class Messages extends AsyncTask<String,Void, String> {

    Context context;
    AlertDialog alertDialog;
    String username;
    String StendenMail;
    boolean isThere;
    MessageBoard msg = new MessageBoard();
    public Messages(Context ctx)
    {
        context = ctx;
        isThere = false;
    }
    String user_name;
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];

        if(type == "topic")
        {
            try{
                username = params[1];
                StendenMail = params[2];
                //String login_url = "http://www.bunte-bloecke.de/htdocsit4c/PHP/login.php";
                String login_url = "http://192.168.43.164/message2.php";

                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
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
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));//iso-8859-1
                String result = "";
                String line;
                while((line = bufferedReader.readLine()) != null)
                {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;
            }catch (Exception ex){

            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Error");
    }

    @Override
    protected void onPostExecute(String aVoid) {

        try{
            String[] msgs = aVoid.split("</br>");
            int n = msgs.length;
            String mess = "";
            for(int i = 0; i < n; i++)
            {
                mess += msgs[i] + "\n\r";
            }
            Intent i = new Intent(context,MessageBoard.class);
            i.putExtra("Messages",aVoid);
            i.putExtra("Username",username);
            i.putExtra("StendenMail",StendenMail);
            context.startActivity(i);
            //msg.userMess(msgs);
            alertDialog.setMessage(mess);
            //alertDialog.show();
        }catch (Exception ex){
            alertDialog.setMessage("You are not connected to the internet! Check your connection and try again.");
           // alertDialog.show();
        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}