package com.example.sunfinder.DetailActivity;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sunfinder.DataAdministration.City;
import com.example.sunfinder.DataAdministration.Functions;
import com.example.sunfinder.MainActivity.Fragment_Start;
import com.example.sunfinder.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class Fragment_Detail extends Fragment implements View.OnClickListener {
    private TextView textView_yourTown;
    private TextView textView_clouds;
    private TextView textView_wind;
    private TextView textView_temp;
    private TextView textView_tempFeels;
    private TextView textView_tempMax;
    private TextView textView_tempMin;
    private TextView textView_humidity;
    private TextView textView_pressure;
    private Button button_viewFacts;
    private Button button_openMap;
    private ListView listView;
    private ImageView imageView_weatherIcon;
    private static final String TAG = Fragment_Start.class.getSimpleName();
    OnViewFactsListener mListener;

    private City city;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: entered");
        View view = inflater.inflate(R.layout.fragment_fragment__detail, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        imageView_weatherIcon = view.findViewById(R.id.imageView_weatherIcon);
        button_viewFacts = view.findViewById(R.id.button_Detail_ViewFacs);
        button_viewFacts.setOnClickListener(this);
        button_openMap = view.findViewById(R.id.button_Detail_OpenMap);
        textView_yourTown = view.findViewById(R.id.textView_Detail_YourTown);
        textView_clouds = view.findViewById(R.id.textView_Detail_Clouds);
        textView_wind = view.findViewById(R.id.textView_Detail_Wind);
        textView_temp = view.findViewById(R.id.textView_Detail_Temp);
        textView_tempFeels = view.findViewById(R.id.textView_Detail_TempFeels);
        textView_tempMax = view.findViewById(R.id.textView_Detail_MaxTemp);
        textView_tempMin = view.findViewById(R.id.textView_Detail_MinTemp);
        textView_humidity = view.findViewById(R.id.textView_Detail_Humidity);
        textView_pressure = view.findViewById(R.id.textView_Detail_Pressure);
    }

    public void setTextViews() {
        button_openMap.setText(city.getName() + " AUF DER KARTE ANZEIGEN");
        textView_yourTown.setText(city.getName());
        textView_clouds.setText(city.getWeatherData().clouds.all + "%");
        textView_wind.setText(city.getWeatherData().wind.speed + " Km/h");
        textView_temp.setText(Functions.kelvinToDegrees(Double.parseDouble(city.getWeatherData().main.temp + "")) + "°C");
        textView_tempFeels.setText(Functions.kelvinToDegrees(Double.parseDouble(city.getWeatherData().main.feels_like + "")) + "°C");
        textView_tempMax.setText(Functions.kelvinToDegrees(Double.parseDouble(city.getWeatherData().main.temp_max + "")) + "°C");
        textView_tempMin.setText(Functions.kelvinToDegrees(Double.parseDouble(city.getWeatherData().main.temp_min + "")) + "°C");
        textView_humidity.setText(city.getWeatherData().main.humidity + "%");
        textView_pressure.setText(city.getWeatherData().main.pressure + "μPa");

        switch (Functions.getWeather(city)) {
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
        if (v.getId() == button_viewFacts.getId()) {
            mListener.viewFactsClicked();
        }
    }

    public void setCity(City city) {
        this.city = city;
    }
}
