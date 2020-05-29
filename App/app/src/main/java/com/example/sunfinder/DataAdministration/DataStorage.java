package com.example.sunfinder.DataAdministration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DataStorage {
    ArrayList<City>cities;
    public DataStorage(ArrayList<City>cities)
    {
        this.cities = cities;
    }
    public List<City> getCitiesSortedBy(int amount, Comparator comparator)
    {
        Collections.sort(cities, comparator);
        return cities.stream().limit(amount).collect(Collectors.<City>toList());
    }
}
