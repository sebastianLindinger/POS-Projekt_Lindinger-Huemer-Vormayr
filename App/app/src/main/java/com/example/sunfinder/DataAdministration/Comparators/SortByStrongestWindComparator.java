package com.example.sunfinder.DataAdministration.Comparators;

import com.example.sunfinder.DataAdministration.City;

import java.util.Comparator;

public class SortByStrongestWindComparator implements Comparator<City> {
    @Override
    public int compare(City o1, City o2) {
        if (o1.getWeatherData().wind.speed < o2.getWeatherData().wind.speed) return -1;
        else return 1;
    }
}
