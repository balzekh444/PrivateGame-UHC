package fr.lidary.game.utils;

public class GameInfo {

    private static String hostName;
    private static String player;
    private static int hour;
    private static int minute;
    private static int maxSlots;
    public static boolean isMumbleActivated = false; 


    public static String getHostName() {
        return hostName;
    }

    public static void setHostName(String hostName) {
        GameInfo.hostName = hostName;
    }

    public static String getPlayer() {
        return player;
    }

    public static void setPlayer(String player) {
        GameInfo.player = player;
    }

    public static int getHour() {
        return hour;
    }

    public static boolean isMumbleActivated() {
        return isMumbleActivated;
    }

    public static void setMumbleActivated(boolean activated) {
        isMumbleActivated = activated;
    }
    
    public static void setHour(int hour) {
        GameInfo.hour = hour;
    }

    public static int getMinute() {
        return minute;
    }

    public static void setMinute(int minute) {
        GameInfo.minute = minute;
    }
    


    public static int getMaxSlots() {
        return maxSlots;
    }

    public static void setMaxSlots(int maxSlots) {
        GameInfo.maxSlots = maxSlots;
    }
    
}
