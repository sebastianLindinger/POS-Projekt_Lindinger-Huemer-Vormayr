package com.example.sunfinder.ServerCommunication;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ServerTask extends AsyncTask<String, Integer, String> {
    private OnTaskFinishedListener listener;

    public ServerTask(OnTaskFinishedListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        // here we could do some UI manipulation before the worker
        // thread starts
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        // do some UI manipulation while progress is modified
        super.onProgressUpdate(values);
    }

    //strings[0] = request-method
    //strings[1] = URL
    @Override
    protected String doInBackground(String... strings) {
        String sJson = "";
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(strings[1]).openConnection();
            connection.setRequestMethod(strings[0]);
            //connection.setRequestProperty("Content−Type", "application/json");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                sJson = readResponseStream(reader);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sJson;

    }

    @Override
    protected void onPostExecute(String response) {
        // called after doInBackground finishes
        listener.onTaskFinished(response);
    }

    private String readResponseStream(BufferedReader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }

}
