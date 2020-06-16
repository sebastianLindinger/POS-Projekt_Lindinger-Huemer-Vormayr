package com.example.sunfinder.DataAdministration;

import java.util.Comparator;

public class SortByBestWeatherComparator implements Comparator<City> {
    @Override
    public int compare(City o1, City o2) {
        return (int)(o2.weatherData.main.temp-o1.weatherData.main.temp);
    }
}
