package com.example.sunfinder.DataAdministration;

import java.io.Serializable;

public class City implements Serializable {
    String _id;
    String postCode;
    String name;
    String[] facts;
    WeatherData weatherData;
    double distance;

    public String get_id() {
        return _id;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getName() {
        return name;
    }

    public String[] getFacts() {
        return facts;
    }

    public WeatherData getWeatherData() {
        return weatherData;
    }

    public double getDistance() {
        return distance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public class WeatherData implements Serializable{
        public Coord coord;
        public Weather[] weather;
        public String base;
        public Main main;
        public int visibility;
        public Wind wind;
        public Clouds clouds;
        public long dt;
        public Sys sys;
        public int timezone;
        public long id;
        public String name;
        public int cod;
    }

    public class Wind implements Serializable{
        public double speed;
        public int deg;
    }

    public class Clouds implements Serializable{
        public int all;
    }

    public class Sys implements Serializable{
        int type;
        int id;
        String country;
        long sunrise;
        long sunset;
    }

    public class Coord implements Serializable{
        double lat;
        double lon;
    }

    public class Weather implements Serializable{
        int id;
        String main;
        String description;
        String icon;
    }

    public class Main implements Serializable{
        public double temp;
        public double feels_like;
        public double temp_min;
        public double temp_max;
        public int pressure;
        public int humidity;
    }
}
