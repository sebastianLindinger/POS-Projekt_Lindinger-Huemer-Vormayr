package com.example.sunfinder.FactsActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sunfinder.MainActivity.Fragment_Start;
import com.example.sunfinder.R;


public class Fragment_Facts extends Fragment {

    private static final String TAG = Fragment_Start.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: entered");
        View view = inflater.inflate(R.layout.fragment_fragment__facts, container, false);
        initializeViews(view);
        return view;
    }
    private void initializeViews(View view)
    {

    }
    public void showinformation()
    {
        //implement this...
    }
}
