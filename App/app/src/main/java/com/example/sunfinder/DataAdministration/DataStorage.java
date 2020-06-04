package com.example.sunfinder.DataAdministration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DataStorage {
    private List<City> cities;

    public DataStorage(List<City> cities) {
        this.cities = cities;
    }

    public List<City> getCitiesSortedBy(int amount, Comparator comparator) {
        Collections.sort(cities, comparator);
        return cities.stream().limit(amount).collect(Collectors.<City>toList());
    }

    public List<City> getCities() {
        return cities;
    }

    public City getCityByIndex(int index) {
        return cities.get(index);
    }
}
