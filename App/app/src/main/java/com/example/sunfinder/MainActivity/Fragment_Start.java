package com.example.sunfinder.MainActivity;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.sunfinder.R;


public class Fragment_Start extends Fragment implements View.OnClickListener {

    private static final String TAG = Fragment_Start.class.getSimpleName();
    private EditText editText_town;
    private EditText editText_plz;
    private Button button_useGPS;
    private ImageButton button_findSun;
    private OnSunClickedListener mListener;
    private Double lon = null;
    private Double lat = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: entered");

        View view = inflater.inflate(R.layout.fragment_fragment__start, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view)
    {
        Log.d(TAG, "initializeViews: entered");
        editText_town = view.findViewById(R.id.editText_Start_Town);
        editText_plz = view.findViewById(R.id.editText_Start_PLZ);
        button_useGPS = view.findViewById(R.id.button_Start_UseGPS);
        button_findSun = view.findViewById(R.id.button_Start_FindSun);
        button_useGPS.setOnClickListener(this);
        button_findSun.setOnClickListener(this);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSunClickedListener) {
            mListener = (OnSunClickedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == button_findSun.getId()) {
            convertAddressToCoordinates(editText_town.getText().toString(), editText_plz.getText().toString());
            mListener.onSunClicked(lon, lat);
        }
        else if(v.getId() == button_useGPS.getId())useGPS();
    }
    private void useGPS()
    {
        //implement this..
    }
    private void convertAddressToCoordinates(String town, String plz)
    {
        //implement this..
    }
}
