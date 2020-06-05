package com.example.sunfinder.DataAdministration;

import java.util.Comparator;

public class SortByDistanceComparator implements Comparator<City> {
    @Override
    public int compare(City o1, City o2) {
        if(o1.distance<o2.distance)return 1;
        else return -1;
    }
}
