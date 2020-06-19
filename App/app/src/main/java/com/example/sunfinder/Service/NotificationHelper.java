package com.example.sunfinder.Service;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.sunfinder.R;

public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";
    private NotificationManager mManager;
    private static String TAG = NotificationHelper.class.getSimpleName();

    public NotificationHelper(Context base) {
        super(base);
        createChannel();
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification(int numberOfSunnyPlaces) {
        NotificationCompat.Builder ncb = new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("SunFinder");
        if (numberOfSunnyPlaces < 1) {
            ncb.setContentText("Es gibt momentan keinen Ort in Österreich, der Sonnenschein hat!");
            ncb.setSmallIcon(R.drawable.cloud);
        } else {
            ncb.setSmallIcon(R.drawable.sun);
            if (numberOfSunnyPlaces > 1) ncb.setContentText("Momentan haben " + numberOfSunnyPlaces + " Orte in Österreich Sonnenschein!");
            else ncb.setContentText("Momentan hat " + numberOfSunnyPlaces + " Ort in Österreich Sonnenschein!");
        }
        return ncb;
    }
}
