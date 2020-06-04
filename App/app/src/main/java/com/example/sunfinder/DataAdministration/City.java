package com.example.sunfinder.DataAdministration;

public class City {
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

    public class WeatherData{
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
    public class Wind
    {
        public double speed;
        public int deg;
    }
    public class Clouds
    {
        public int all;
    }
    public class Sys
    {
        int type;
        int id;
        String country;
        long sunrise;
        long sunset;
    }
    public class Coord
    {
        double lat;
        double lon;
    }
    public class Weather
    {
        int id;
        String main;
        String description;
        String icon;
    }
    public class Main{
        public double temp;
        public double feels_like;
        public double temp_min;
        public double temp_max;
        public int pressure;
        public int humidity;
    }
}
