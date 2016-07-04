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

/*
 * Made by Marin Codrut, Tim Vaneker, Smith Chen and Flavian Gall
 */
public class DissMsgs  extends AsyncTask<String,Void, String> {
    Context context;
    MessageBoard msg = new MessageBoard();
    public DissMsgs(Context ctx)
    {
        context = ctx;
    }
    String mail;
    protected String doInBackground(String... params) {

            try {
                // String login_url = "http://www.bunte-bloecke.de/htdocsit4c/PHP/login.php";
                String UserID = params[0];
                String login_url = "http://192.168.43.164/discussion.php";
                String msg = params[1];
                String itemID = params[2];
                String StedenMAIL = params[3];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(UserID, "UTF-8") + "&"
                        + URLEncoder.encode("Message", "UTF-8") + "=" + URLEncoder.encode(msg, "UTF-8")+ "&"
                        + URLEncoder.encode("itemID", "UTF-8") + "=" + URLEncoder.encode(itemID, "UTF-8")+ "&"
                        + URLEncoder.encode("StedenMail", "UTF-8") + "=" + URLEncoder.encode(StedenMAIL, "UTF-8");
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
