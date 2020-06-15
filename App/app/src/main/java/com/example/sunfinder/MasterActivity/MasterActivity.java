package com.example.sunfinder.MasterActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sunfinder.DataAdministration.DataStorage;
import com.example.sunfinder.DetailActivity.DetailActivity;
import com.example.sunfinder.MainActivity.Fragment_Start;
import com.example.sunfinder.R;
import com.example.sunfinder.Preferences.SettingsActivity;

public class MasterActivity extends AppCompatActivity implements OnTownSelectedListener {

    private SharedPreferences prefs;
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
    private static final String TAG = MasterActivity.class.getSimpleName();
    private Fragment_Master fragment_master;
    private DataStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                Log.d(TAG, "entered: preferencesChanged");
                preferenceChanged(sharedPreferences, key);
            }
        };
        prefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener);
        loadPreferences();

        storage = (DataStorage) getIntent().getSerializableExtra("storage");

        fragment_master = (Fragment_Master) getSupportFragmentManager().findFragmentById(R.id.fragment_master);
        fragment_master.setStorage(storage);
        fragment_master.setTextViews();
    }

    @Override
    public void townSelected(int position) {
        callDetailActivity(position);
    }

    private void callDetailActivity(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("city",storage.getCityByIndex(position));
        startActivity(intent);
    }


    //getPreferences(only for testing)...Implement this
    private void loadPreferences() {
        int amountOfTowns = Integer.parseInt(prefs.getString("preference_amountOfTowns", "10"));
        String sortingBy = prefs.getString("preference_sorting", "near");

        Log.d(TAG, "amount: " + amountOfTowns);
        Log.d(TAG, "soring: " + sortingBy);
    }

    //Reaction to Preference change
    private void preferenceChanged(SharedPreferences sharedPrefs, String key) {
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
