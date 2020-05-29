package com.example.sunfinder.DataAdministration;

import java.util.Comparator;

public class SortByBestWeatherComparator implements Comparator<City> {
    @Override
    public int compare(City o1, City o2) {
        int temp1 = 0;
        int temp2 = 0;

        //comparing clouds
        if(o1.weatherData.clouds.all<o2.weatherData.clouds.all)temp1++;
        else if(o1.weatherData.clouds.all>o2.weatherData.clouds.all)temp2++;

        //comparing temp
        if (o1.weatherData.main.temp_max>o2.weatherData.main.temp_max)temp1++;
        else if (o1.weatherData.main.temp_max<o2.weatherData.main.temp_max)temp2++;

        //comparing wind
        if(o1.weatherData.wind.speed<o2.weatherData.wind.speed) temp1++;
        else if(o1.weatherData.wind.speed<o2.weatherData.wind.speed) temp2++;

        return temp1-temp2;
    }
}
