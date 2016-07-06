package com.example.codru.stendensocial;

import android.app.AlertDialog;
import android.content.Context;
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
import java.util.ArrayList;

/*
 * Made by Marin Codrut, Tim Vaneker, Smith Chen and Flavian Gall
 */
public class UploadMessages extends AsyncTask<String,Void, String> {
    Context context;
    MessageBoard msg = new MessageBoard();
    public UploadMessages(Context ctx)
    {
        context = ctx;
    }
    String mail;
    protected String doInBackground(String... params) {
        String type = params[0];
       if(type == "Topic") {
           try {
               // String login_url = "http://www.bunte-bloecke.de/htdocsit4c/PHP/login.php";
               String login_url = "http://192.168.1.85/uploadMessage.php";
               String stenden_mail = params[1];
               String message = params[2];
               URL url = new URL(login_url);
               HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
               httpURLConnection.setRequestMethod("POST");
               httpURLConnection.setDoOutput(true);
               httpURLConnection.setDoInput(true);
               OutputStream outputStream = httpURLConnection.getOutputStream();
               BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
               String post_data = URLEncoder.encode("stenden_mail", "UTF-8") + "=" + URLEncoder.encode(stenden_mail, "UTF-8") + "&"
                       + URLEncoder.encode("message", "UTF-8") + "=" + URLEncoder.encode(message, "UTF-8")+ "&"
                       + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8");
               bufferedWriter.write(post_data);
               bufferedWriter.flush();
               bufferedWriter.close();
               outputStream.close();
               InputStream inputStream = httpURLConnection.getInputStream();
               BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
               String result = "";
               String line;
               while ((line = bufferedReader.readLine()) != null) {
                   result += line;
               }
               bufferedReader.close();
               inputStream.close();
               httpURLConnection.disconnect();
               return result;
           } catch (Exception ex) {

           }
       }

        if(type == "Message")
        {
            try {
                // String login_url = "http://www.bunte-bloecke.de/htdocsit4c/PHP/login.php";
                String login_url = "http://1192.168.1.85/uploadMessage.php";
                String stenden_mail = params[1];
                String message = params[2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("stenden_mail", "UTF-8") + "=" + URLEncoder.encode(stenden_mail, "UTF-8") + "&"
                        + URLEncoder.encode("message", "UTF-8") + "=" + URLEncoder.encode(message, "UTF-8")+ "&"
                        + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (Exception ex) {

            }
        }
        return null;
    }

    AlertDialog alertDialog;
    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Error");
    }

    @Override
    protected void onPostExecute(String aVoid) {
        if(aVoid != null) {
            alertDialog.setMessage(aVoid);
           // alertDialog.show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
