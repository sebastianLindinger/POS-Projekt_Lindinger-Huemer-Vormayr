package com.example.sunfinder.DataAdministration.Comparators;

import com.example.sunfinder.DataAdministration.City;

import java.util.Comparator;

public class SortByHighestTemperatureComparator implements Comparator<City> {
    @Override
    public int compare(City o1, City o2) {
        if (o1.getWeatherData().main.temp < o2.getWeatherData().main.temp) return 1;
        else return -1;
    }
}
