package com.example.sunfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MasterAcitvity extends AppCompatActivity implements OnTownSelectedListener{

    private SharedPreferences prefs;
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
    private static final String TAG = MasterAcitvity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_master);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        preferenceChangeListener =  new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                Log.d(TAG, "entered: preferencesChanged");
                preferenceChanged(sharedPreferences, key);
            }
        };
        prefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener);
        loadPreferences();
    }

    @Override
    public void townSelected(int position) {
        callDetailActivity();
    }
    private void callDetailActivity()
    {
        Intent intent = new Intent(this, DetailActivity.class);
        //Implement this --> intent.putExtra();
        startActivity(intent);
    }


    //getPreferences(only for testing)...Implement this
    private void loadPreferences()
    {
        int amountOfTowns = Integer.parseInt(prefs.getString("preference_amountOfTowns", "10"));
        String sortingBy = prefs.getString("preference_sorting", "near");

        Log.d(TAG, "amount: "+amountOfTowns);
        Log.d(TAG, "soring: "+sortingBy);
    }

    //Reaction to Preference change
    private void preferenceChanged(SharedPreferences sharedPrefs, String key)
    {
        loadPreferences();
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
