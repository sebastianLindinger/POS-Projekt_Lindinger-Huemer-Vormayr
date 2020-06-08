package com.example.sunfinder.DataAdministration;

import java.text.DecimalFormat;

public class Functions {
    public static double kelvinToDegrees(double kelvin) {
        DecimalFormat df = new DecimalFormat("#.#");
        return Double.parseDouble(df.format(kelvin - 273.15).replace(",", "."));
    }

    public static Weather getWeather(City city) {
        if(city.weatherData.weather[0].icon.equals("01d") && city.weatherData.weather[0].id == 800) return Weather.SUN;
        else if(city.weatherData.weather[0].main.equals("Rain")) return Weather.RAIN;
        else if(city.weatherData.weather[0].main.equals("Thunderstorm")) return Weather.THUNDERSTORM;
        else return Weather.CLOUD;
    }
}
