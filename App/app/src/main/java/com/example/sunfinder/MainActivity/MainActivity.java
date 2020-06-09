package com.example.sunfinder.MainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.example.sunfinder.Service.NotificationService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnSunClickedListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private final String URL_geo = "http://varchar42.me:3000/sunFinder/getByCoord?lat=<lat>&lon=<lon>";
    private final String URL_nameAndPostcode = "http://varchar42.me:3000/sunFinder/getByNameAndPostcode?name=<name>&postcode=<postcode>";

    Fragment_Start fragment_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        fragment_start = (Fragment_Start) getSupportFragmentManager().findFragmentById(R.id.fragment_start);
        startNotificationService();
    }

    @Override
    public void onSunClicked(double lon, double lat, String city, String postcode) {
        Log.d(TAG, "onSunClicked: entered");

        ServerTask getDataFromServer = new ServerTask(new OnTaskFinishedListener() {
            @Override
            public void onTaskFinished(String response) {
                Log.d(TAG, response);
                //parse responseJsonArray (do this in a method) (hob grod kan bock dase oa klasse dafir erstö)
                Gson gson = new Gson();
                TypeToken<List<City>> token = new TypeToken<List<City>>() {
                };
                try {
                    DataStorage storage = new DataStorage((List<City>) gson.fromJson(response, token.getType()));
                    Toast.makeText(MainActivity.this, storage.getCityByIndex(0).getName(), Toast.LENGTH_LONG).show();

                    callMasterActivity(storage);
                } catch (Exception e) {
                    //city was not found
                    Log.e(TAG, response);
                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                }
            }
        });

        if (fragment_start.useGPS) {
            if(lon != 0.0 && lat != 0.0) getDataFromServer.execute("GET", URL_geo.replace("<lat>", String.valueOf(lat)).replace("<lon>", String.valueOf(lon)));
        } else if(!city.equals("") || !postcode.equals("")){
            getDataFromServer.execute("GET", URL_nameAndPostcode.replace("<name>", city).replace("<postcode>", postcode));
        } else {
            Toast.makeText(MainActivity.this, "Kein Ort eingegeben!", Toast.LENGTH_LONG).show();
        }
    }
    private void startNotificationService()
    {
        Log.d(TAG, "entered: startNotificationService");
        Intent intent = new Intent(this, NotificationService.class);
        startService(intent);
    }

    private void callMasterActivity(DataStorage storage) {
        Intent intent = new Intent(this, MasterActivity.class);
        intent.putExtra("storage", storage);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == fragment_start.LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "not granted");
            } else {
                Log.d(TAG, "granted - 2");
                fragment_start.useGPS = true;
                fragment_start.button_useGPS.setText("STANDORT PER EINGABE ERMITTELN");
                fragment_start.createLocationListener();
            }
        }
    }
}
