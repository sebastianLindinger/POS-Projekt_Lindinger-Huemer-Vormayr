package com.example.sunfinder;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class Fragment_Detail extends Fragment implements View.OnClickListener {
    private TextView textView_yourTown;
    private TextView textView_clouds;
    private TextView textView_wind;
    private TextView textView_temp;
    private TextView textView_tempFeels;
    private TextView textView_tempMax;
    private TextView textView_tempMin;
    private TextView textView_humidity;
    private TextView textView_pessure;
    private Button button_viewFacts;
    private Button button_openMap;
    private ListView listView;
    private static final String TAG = Fragment_Start.class.getSimpleName();
    OnViewFactsListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: entered");
        View view = inflater.inflate(R.layout.fragment_fragment__detail, container, false);
        initializeViews(view);
        return view;
    }
    private void initializeViews(View view)
    {
        button_viewFacts = view.findViewById(R.id.button_Detail_ViewFacs);
        button_viewFacts.setOnClickListener(this);
        //implement this..
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnViewFactsListener) {
            mListener = (OnViewFactsListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == button_viewFacts.getId())
        {
            mListener.viewFactsClicked();
        }
    }
}
