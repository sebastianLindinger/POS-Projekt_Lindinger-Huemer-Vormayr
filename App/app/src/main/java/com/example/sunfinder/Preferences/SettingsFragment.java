package com.example.sunfinder.Preferences;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.sunfinder.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
