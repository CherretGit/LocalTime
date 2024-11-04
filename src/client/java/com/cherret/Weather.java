package com.cherret;

public class Weather {
    private static boolean isWeatherSync = true;
    private static boolean isClear = true;
    private static boolean isRain = false;
    public static boolean isWeatherSync() {
        return isWeatherSync;
    }
    public static void setWeatherSync(boolean weatherSync) {
        isWeatherSync = weatherSync;
    }
    public static boolean isClear() {
        return isClear;
    }
    public static void setClear(boolean clear) {
        isClear = clear;
    }
    public static boolean isRain() {
        return isRain;
    }
    public static void setRain(boolean rain) {
        isRain = rain;
    }
}