package com.example.sunfinder.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.sunfinder.ServerCommunication.OnTaskFinishedListener;
import com.example.sunfinder.ServerCommunication.ServerTask;

public class AlertReceiver extends BroadcastReceiver {
    private final String URL_numberOfSunnyPlaces = "http://varchar42.me:3000/sunFinder/getNumberOfSunnyPlaces";

    @Override
    public void onReceive(Context context, Intent intent) {
        final NotificationHelper notificationHelper = new NotificationHelper(context);

        ServerTask getDataFromServer = new ServerTask(new OnTaskFinishedListener() {
            @Override
            public void onTaskFinished(String response) {
                int numberOfSunnyPlaces = Integer.parseInt(response);

                NotificationCompat.Builder nb = notificationHelper.getChannelNotification(numberOfSunnyPlaces);
                notificationHelper.getManager().notify(1, nb.build());
            }
        });
        getDataFromServer.execute("GET", URL_numberOfSunnyPlaces);

    }
}
