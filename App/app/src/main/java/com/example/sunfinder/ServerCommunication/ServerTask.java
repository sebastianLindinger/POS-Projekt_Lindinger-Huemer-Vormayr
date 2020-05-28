package com.example.sunfinder.ServerCommunication;

import android.os.AsyncTask;

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

    @Override
    protected String doInBackground(String... strings) {
        // workhorse methode
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        // called after doInBackground finishes
        listener.onTaskFinished();
    }

}
