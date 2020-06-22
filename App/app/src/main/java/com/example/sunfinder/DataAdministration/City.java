package com.example.sunfinder.DataAdministration;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

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
        //do not show empty elements
        if (facts != null) return Arrays.stream(facts).filter(fact -> !fact.equals("")).toArray(String[]::new);
        else return new String[0];
    }

    public void addFact(String fact) {
        String[] factArr = new String[facts.length + 1];
        for (int i = 0; i < facts.length; i++) {
            factArr[i] = facts[i];
        }
        factArr[facts.length] = fact;
        facts = factArr;
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

    public double getLat() {
        return weatherData.coord.lat;
    }

    public double getLon() {
        return weatherData.coord.lon;
    }

    public double getTempInDegrees() {
        return kelvinToDegrees(weatherData.main.temp);
    }

    public double getTempMaxInDegrees() {
        return kelvinToDegrees(weatherData.main.temp_max);
    }

    public double getTempMinInDegrees() {
        return kelvinToDegrees(weatherData.main.temp_min);
    }

    public double getTempFeelsLikeInDegrees() {
        return kelvinToDegrees(weatherData.main.feels_like);
    }

    private double kelvinToDegrees(double kelvin) {
        DecimalFormat df = new DecimalFormat("#.#");
        return Double.parseDouble(df.format(kelvin - 273.15).replace(",", "."));
    }

    public com.example.sunfinder.DataAdministration.Weather getWeather() {
        if (weatherData.weather[0].icon.equals("01d") && weatherData.weather[0].id == 800)
            return com.example.sunfinder.DataAdministration.Weather.SUN;
        else if (weatherData.weather[0].main.equals("Thunderstorm"))
            return com.example.sunfinder.DataAdministration.Weather.THUNDERSTORM;
        else if (weatherData.weather[0].main.equals("Rain"))
            return com.example.sunfinder.DataAdministration.Weather.RAIN;
        else return com.example.sunfinder.DataAdministration.Weather.CLOUD;
    }

    public class WeatherData implements Serializable {
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

    public class Wind implements Serializable {
        public double speed;
        public int deg;
    }

    public class Clouds implements Serializable {
        public int all;
    }

    public class Sys implements Serializable {
        int type;
        int id;
        String country;
        long sunrise;
        long sunset;
    }

    public class Coord implements Serializable {
        double lat;
        double lon;
    }

    public class Weather implements Serializable {
        int id;
        String main;
        String description;
        String icon;
    }

    public class Main implements Serializable {
        public double temp;
        public double feels_like;
        public double temp_min;
        public double temp_max;
        public int pressure;
        public int humidity;
    }
}
