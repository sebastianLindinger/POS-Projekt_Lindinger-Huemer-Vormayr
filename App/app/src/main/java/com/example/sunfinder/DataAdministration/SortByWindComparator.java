package com.example.sunfinder.DataAdministration;

import java.util.Comparator;

public class SortByWindComparator implements Comparator<City> {
    @Override
    public int compare(City o1, City o2) {
        if(o1.weatherData.wind.speed<o2.weatherData.wind.speed)return 1;
        else return -1;
    }
}
