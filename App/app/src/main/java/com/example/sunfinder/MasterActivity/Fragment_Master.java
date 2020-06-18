package com.example.sunfinder.MasterActivity;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sunfinder.DataAdministration.City;
import com.example.sunfinder.DataAdministration.DataStorage;
import com.example.sunfinder.MainActivity.Fragment_Start;
import com.example.sunfinder.R;

import java.util.ArrayList;
import java.util.List;


public class Fragment_Master extends Fragment {
    private static final String TAG = Fragment_Start.class.getSimpleName();

    private ImageView imageView_weatherIcon;
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
    private List<City> cities = new ArrayList<>();
    private Context ctx;
    private DataStorage storage;
    private String sortBy;
    private int amountOfCities;

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

    public void setAdapter(Context context) {
        adapter = new ItemListAdapter(storage.getSunnyCitiesSortedBy(amountOfCities, sortBy), R.layout.listview_item_master, context);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void setTextViews() {
        City city = storage.getMyCity();
        switch (city.getWeather()) {
            case SUN:
                imageView_weatherIcon.setImageResource(R.drawable.sun);
                break;

            case RAIN:
                imageView_weatherIcon.setImageResource(R.drawable.rain);
                break;

            case THUNDERSTORM:
                imageView_weatherIcon.setImageResource(R.drawable.thunderstorm);
                break;

            case CLOUD:
                imageView_weatherIcon.setImageResource(R.drawable.cloud);
                break;
        }

        textView_yourTown.setText(city.getName());
        textView_clouds.setText(city.getWeatherData().clouds.all + "%");
        textView_wind.setText(city.getWeatherData().wind.speed + " Km/h");
        textView_temp.setText(city.getTempInDegrees() + "°C");
        textView_tempFeels.setText(city.getTempFeelsLikeInDegrees() + "°C");
        textView_tempMax.setText(city.getTempMaxInDegrees() + "°C");
        textView_tempMin.setText(city.getTempMinInDegrees() + "°C");

        if(storage.getSunnyCities().size() == 0) {
            View footer = getLayoutInflater().inflate(R.layout.listview_footer, null);
            listView.addFooterView(footer, null, false);
        }
        setAdapter(ctx);
    }

    private void initializeViews(View view) {
        listView = view.findViewById(R.id.listView_Master);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.townSelected(position);
            }
        });

        View header = getLayoutInflater().inflate(R.layout.listview_header, null);
        listView.addHeaderView(header, null, false);

        imageView_weatherIcon = header.findViewById(R.id.imageView_weatherIcon);
        textView_yourTown = header.findViewById(R.id.textView_Master_YourTown);
        textView_clouds = header.findViewById(R.id.textView_Master_Clouds);
        textView_wind = header.findViewById(R.id.textView_Master_Wind);
        textView_temp = header.findViewById(R.id.textView_Master_Temp);
        textView_tempFeels = header.findViewById(R.id.textView_Master_TempFeels);
        textView_tempMax = header.findViewById(R.id.textView_Master_MaxTemp);
        textView_tempMin = header.findViewById(R.id.textView_Master_MinTemp);
    }

    public void setStorage(DataStorage storage) {
        this.storage = storage;
    }
    public void setPreferences(int amountOfCities, String sortBy)
    {
        this.amountOfCities = amountOfCities;
        this.sortBy = sortBy;
    }


}
