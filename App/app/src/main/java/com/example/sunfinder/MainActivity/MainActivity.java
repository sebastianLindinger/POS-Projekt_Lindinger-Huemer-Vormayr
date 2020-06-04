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
import android.widget.Toast;

import com.example.sunfinder.MasterActivity.MasterActivity;
import com.example.sunfinder.R;
import com.example.sunfinder.Preferences.SettingsActivity;
import com.example.sunfinder.ServerCommunication.OnTaskFinishedListener;
import com.example.sunfinder.ServerCommunication.ServerTask;

public class MainActivity extends AppCompatActivity implements OnSunClickedListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private final String URL_geo = "not implemented yet";
    private final String URL_nameAndPostcode = "http://varchar42.me:3000/sunFinder/getByNameAndPostcode?name=<name>&postcode=<postcode>";

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

        ServerTask getDataFromServer = new ServerTask(new OnTaskFinishedListener() {
            @Override
            public void onTaskFinished(String response) {
                //do something when task is finished...
                Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
            }
        });

        if(checkGeoPermission()) getDataFromServer.execute("GET", URL_geo);
        else getDataFromServer.execute("GET", URL_nameAndPostcode.replace("<name>", "Meggenhofen").replace("<postcoe>", "4714"));

        //callMasterActivity();
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

    private boolean checkGeoPermission() {
        //not implemented yet
        return false;
    }
}
