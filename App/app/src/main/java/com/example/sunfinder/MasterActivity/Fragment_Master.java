package com.example.sunfinder.MasterActivity;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sunfinder.DataAdministration.City;
import com.example.sunfinder.MainActivity.Fragment_Start;
import com.example.sunfinder.R;

import java.util.ArrayList;
import java.util.List;


public class Fragment_Master extends Fragment {
    private static final String TAG = Fragment_Start.class.getSimpleName();
    private TextView textView_yourTown;
    private TextView textView_clouds;
    private TextView textView_wind;
    private TextView textView_temp;
    private TextView textView_tempFeels;
    private TextView textView_tempMax;
    private TextView textView_tempMin;
    private ListView listView;
    private OnTownSelectedListener mListener;
    private ItemListAdapter adapter;
    private List<City>cities = new ArrayList<>();
    private Context ctx;

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       Log.d(TAG, "onCreateView: entered");
       View view = inflater.inflate(R.layout.fragment_fragment__master, container, false);
       initializeViews(view);
       return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTownSelectedListener) {
            mListener = (OnTownSelectedListener) context;
            ctx = context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    private void setAdapter(Context context)
    {
        adapter = new ItemListAdapter(cities, R.layout.listview_item_master, context);
        listView.setAdapter(adapter);
    }
    private void setTextViews(City city)
    {
        textView_yourTown.setText(city.getName());
        textView_clouds.setText(city.getWeatherData().clouds.all);
        textView_wind.setText(city.getWeatherData().wind.speed+" Km/h");
        textView_temp.setText(city.getWeatherData().main.temp+"°C");
        textView_tempFeels.setText(city.getWeatherData().main.feels_like+"°C");
        textView_tempMax.setText(city.getWeatherData().main.temp_max+"°C");
        textView_tempMin.setText(city.getWeatherData().main.temp_min+"°C");
    }
    private void initializeViews(View view)
    {
        textView_yourTown = view.findViewById(R.id.textView_Master_YourTown);
        textView_clouds = view.findViewById(R.id.textView_Master_Clouds);
        textView_wind = view.findViewById(R.id.textView_Master_Wind);
        textView_temp = view.findViewById(R.id.textView_Master_Temp);
        textView_tempFeels = view.findViewById(R.id.textView_Master_TempFeels);
        textView_tempMax = view.findViewById(R.id.textView_Master_MaxTemp);
        textView_tempMin = view.findViewById(R.id.textView_Master_MinTemp);

        listView = view.findViewById(R.id.listView_Master);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.townSelected(position);
            }
        });

        //this is for testing----
        City tempCity = new City();
        tempCity.setName("Meggenhofen");
        cities.add(tempCity);
        //----

        setAdapter(ctx);
    }


}
