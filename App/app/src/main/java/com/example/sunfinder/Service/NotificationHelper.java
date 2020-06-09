package com.example.sunfinder.Service;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.sunfinder.DataAdministration.City;
import com.example.sunfinder.DataAdministration.DataStorage;
import com.example.sunfinder.MainActivity.MainActivity;
import com.example.sunfinder.R;
import com.example.sunfinder.ServerCommunication.OnTaskFinishedListener;
import com.example.sunfinder.ServerCommunication.ServerTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";
    private NotificationManager mManager;
    private static String TAG = NotificationHelper.class.getSimpleName();

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
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

    public NotificationCompat.Builder getChannelNotification() {
        int cities = getAmountofSunnyCities();
        NotificationCompat.Builder ncb= new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("SunFinder");

        if(cities == 0)
        {
            ncb.setContentText("Es gibt momentan keinen Ort in Österreich, der Sonnenschein hat!");
            ncb.setSmallIcon(R.drawable.cloud);
        }
        else
        {
            ncb.setContentText("Momentan haben "+cities+" Orte in Österreich Sonnenschein!");
            ncb.setSmallIcon(R.drawable.sun);
        }
        return ncb;
    }
    private int getAmountofSunnyCities()
    {
        //implement this
        return 420;
    }
}
