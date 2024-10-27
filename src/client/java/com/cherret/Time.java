package com.cherret;

public class Time {
    private static boolean isTimeSync = true;
    private static boolean isClear = false;
    private static long time = 1000;
    public static void setTime(long time) {
        Time.time = time;
    }
    public static long getTime() {
        return time;
    }
    public static void setIsTimeSync(boolean isTimeSync) {
        Time.isTimeSync = isTimeSync;
    }
    public static boolean getIsTimeSync() {
        return isTimeSync;
    }
    public static void setClear(boolean isClear) {
        Time.isClear = isClear;
    }
    public static boolean getIsClear() {
        return isClear;
    }
}
