package com.example.sunfinder.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.sunfinder.MasterActivity.MasterActivity;
import com.example.sunfinder.R;
import com.example.sunfinder.Preferences.SettingsActivity;
import com.example.sunfinder.ServerCommunication.OnTaskFinishedListener;
import com.example.sunfinder.ServerCommunication.ServerTask;

public class MainActivity extends AppCompatActivity implements OnSunClickedListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onSunClicked(Double lon, Double lat) {
        Log.d(TAG, "onSunClicked: entered");
        Log.d(TAG, "lon="+lon+" lat="+lat);

        ServerTask task = new ServerTask(new OnTaskFinishedListener() {
            @Override
            public void onTaskFinished() {
                //do something when task is finished
            }
        });
        task.execute(); //enter some params

        /*if(lon == null || lat == null)
        {
            Toast.makeText(this, "Sie müssen zuerst einen Ort angeben!", Toast.LENGTH_LONG).show();
        }
        else {
            callMasterActivity();
        }*/
        callMasterActivity();
    }
    private void callMasterActivity() {
        Intent intent = new Intent(this, MasterActivity.class);
        //Implement this --> intent.putExtra();
        startActivity(intent);
    }


    //Implementation of the Preference menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.preferences_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Reaction to a selection in the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_settings:
                Intent intent = new Intent(this,
                        SettingsActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
