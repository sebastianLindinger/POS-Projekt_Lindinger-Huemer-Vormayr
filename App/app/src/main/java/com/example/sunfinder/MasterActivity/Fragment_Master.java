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
import com.example.sunfinder.DataAdministration.Functions;
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

    private void setAdapter(Context context) {
        adapter = new ItemListAdapter(storage.getSunnyCities(), R.layout.listview_item_master, context);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void setTextViews() {
        City city = storage.getCityByIndex(0);
        switch (Functions.getWeather(city)) {
            case SUN:
                imageView_weatherIcon.setImageResource(R.drawable.sun);
                break;

            case RAIN:
                //TODO: use other icon
                imageView_weatherIcon.setImageResource(R.drawable.cloud);
                break;

            case THUNDERSTORM:
                //TODO: use other icon
                imageView_weatherIcon.setImageResource(R.drawable.cloud);
                break;

            case CLOUD:
                imageView_weatherIcon.setImageResource(R.drawable.cloud);
                break;
        }

        textView_yourTown.setText(city.getName());
        textView_clouds.setText(city.getWeatherData().clouds.all + "%");
        textView_wind.setText(city.getWeatherData().wind.speed + " Km/h");
        textView_temp.setText(Functions.kelvinToDegrees(city.getWeatherData().main.temp) + "°C");
        textView_tempFeels.setText(Functions.kelvinToDegrees(city.getWeatherData().main.feels_like) + "°C");
        textView_tempMax.setText(Functions.kelvinToDegrees(city.getWeatherData().main.temp_max) + "°C");
        textView_tempMin.setText(Functions.kelvinToDegrees(city.getWeatherData().main.temp_min) + "°C");

        setAdapter(ctx);
    }

    private void initializeViews(View view) {
        imageView_weatherIcon = view.findViewById(R.id.imageView_weatherIcon);
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

    }

    public void setStorage(DataStorage storage) {
        this.storage = storage;
    }


}
