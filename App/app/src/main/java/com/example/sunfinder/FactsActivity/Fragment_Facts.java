package com.example.sunfinder.FactsActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.example.sunfinder.MainActivity.Fragment_Start;
import com.example.sunfinder.R;


public class Fragment_Facts extends Fragment implements View.OnClickListener{

    private static final String TAG = Fragment_Start.class.getSimpleName();

    private Button button_addFact;
    private OnAddFactListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: entered");
        View view = inflater.inflate(R.layout.fragment_fragment__facts, container, false);
        initializeViews(view);
        return view;
    }
    private void initializeViews(View view) {
        button_addFact = view.findViewById(R.id.button_Facts_WriteFact);
        button_addFact.setOnClickListener(new AdapterView.OnClickListener(){
            @Override
            public void onClick(View v) {
               mListener.addFactListener();
            }
        });

    }
    public void showinformation() {
        //implement this...
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == button_addFact.getId())
        {
            mListener.addFactListener();
        }
    }
}
