package com.example.sunfinder.DataAdministration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DataStorage implements Serializable {
    private City myCity;
    private List<City> cities;

    public DataStorage(City myCity, List<City> cities) {
        this.myCity = myCity;
        this.cities = cities;
    }

    public List<City> getSunnyCitiesSortedBy(int amount, String comparatorString) {
        Comparator comparator;
        switch (comparatorString) {

            case "highestTemp":
                comparator = new SortByBestWeatherComparator();
                break;
            case "leastWind":
                comparator = new SortByWindComparator();
                break;
                default:
                    comparator = new SortByDistanceComparator();
        }
        Collections.sort(cities, comparator);
        if(amount <= cities.size()) return cities.subList(0, amount);
        return cities;
    }

    public List<City> getSunnyCities() {
        return cities;
    }

    public City getCityByIndex(int index) {
        return cities.get(index);
    }

    public City getMyCity() {
        return myCity;
    }
}
