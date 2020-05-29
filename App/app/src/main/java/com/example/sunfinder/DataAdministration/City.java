package com.example.sunfinder.DataAdministration;

public class City {
    String _id;
    String postCode;
    String name;
    String[] facts;
    WeatherData weatherData;
    double distance;

    public class WeatherData{
        Coord coord;
        Weather[] weather;
        String base;
        Main main;
        int visibility;
        Wind wind;
        Clouds clouds;
        long dt;
        Sys sys;
        int timezone;
        long id;
        String name;
        int cod;
    }
    public class Wind
    {
        double speed;
        int deg;
    }
    public class Clouds
    {
        int all;
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
        double temp;
        double feels_like;
        double temp_min;
        double temp_max;
        int pressure;
        int humidity;
    }
}
