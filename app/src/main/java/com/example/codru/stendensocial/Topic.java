package com.example.codru.stendensocial;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/*
 * Made by Marin Codrut, Tim Vaneker, Smith Chen and Flavian Gall
 */
public class Topic  extends AsyncTask<String,Void, String> {
    Context context;
    AlertDialog alertDialog;
    boolean isThere;
    MessageBoard msg = new MessageBoard();
    public Topic(Context ctx)
    {
        context = ctx;
        isThere = false;
    }
    String item;
    String tusername,username;
    String userID;
    String itemID;
    String StendenMail;
    String topicMSGs;
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];

        if(type == "topic")
        {
            try{
                item = params[1];
                itemID = params[3];
                userID = params[2];
                tusername = params[4];
                username = params[6];
                StendenMail = params[5];
                topicMSGs = params[7];
                //String login_url = "http://www.bunte-bloecke.de/htdocsit4c/PHP/login.php";
                String login_url = "http://192.168.43.164/message1.php";

                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("itemID","UTF-8")+"="+URLEncoder.encode(itemID,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
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
            Intent i = new Intent(context,Discussion.class);
            i.putExtra("Messages",aVoid);
            i.putExtra("TUserName",tusername);
            i.putExtra("StendenMail",StendenMail);
            i.putExtra("TopicName",item);
            i.putExtra("TopicID", itemID);
            i.putExtra("UserID",userID);
            i.putExtra("Username",username);
            i.putExtra("Topicmsgs",topicMSGs);
            context.startActivity(i);
            //msg.userMess(msgs);
            alertDialog.setMessage(mess);
           // alertDialog.show();
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
