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

import com.example.sunfinder.DataAdministration.City;
import com.example.sunfinder.DataAdministration.DataStorage;
import com.example.sunfinder.MasterActivity.MasterActivity;
import com.example.sunfinder.R;
import com.example.sunfinder.Preferences.SettingsActivity;
import com.example.sunfinder.ServerCommunication.OnTaskFinishedListener;
import com.example.sunfinder.ServerCommunication.ServerTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

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
    public void onSunClicked(double lon, double lat, String city, String postcode) {
        Log.d(TAG, "onSunClicked: entered");

        ServerTask getDataFromServer = new ServerTask(new OnTaskFinishedListener() {
            @Override
            public void onTaskFinished(String response) {
                //parse responseJsonArray
                Gson gson = new Gson();
                TypeToken<List<City>> token = new TypeToken<List<City>>() {
                };
                try {
                    DataStorage storage = new DataStorage((List<City>) gson.fromJson(response, token.getType()));
                    Toast.makeText(MainActivity.this, storage.getCityByIndex(0).getName(), Toast.LENGTH_LONG).show();

                    callMasterActivity();
                } catch (Exception e) {
                    //city was not found
                    Log.e(TAG, response);
                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                }
            }
        });

        if (lon != 0.0 && lat != 0.0) getDataFromServer.execute("GET", URL_geo);
        else {
            getDataFromServer.execute("GET", URL_nameAndPostcode.replace("<name>", city).replace("<postcode>", postcode));
        }
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
