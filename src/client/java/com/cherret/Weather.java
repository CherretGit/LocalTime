package com.cherret;

public class Weather {
    private static boolean isWeatherSync = true;
    private static WeatherType weatherType = WeatherType.CLEAR;
    public static boolean isWeatherSync() {
        return isWeatherSync;
    }
    public static void setWeatherSync(boolean weatherSync) {
        isWeatherSync = weatherSync;
    }
    public static void setWeatherType(WeatherType type) { weatherType = type; }
    public static WeatherType getWeatherType() { return weatherType; }
}