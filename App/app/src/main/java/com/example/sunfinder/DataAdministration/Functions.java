package com.example.sunfinder.DataAdministration;

import java.text.DecimalFormat;

public class Functions {
    public static double kelvinToDegrees(double kelvin) {
        DecimalFormat df = new DecimalFormat("#.#");
        return Double.parseDouble(df.format(kelvin - 273.15).replace(",", "."));
    }
}
