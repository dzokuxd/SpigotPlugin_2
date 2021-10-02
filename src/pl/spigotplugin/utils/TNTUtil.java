package pl.spigotplugin.utils;

import pl.spigotplugin.configs.guild;

import java.util.Calendar;
import java.util.TimeZone;

public class TNTUtil {
    @SuppressWarnings("deprecation")
    public static boolean isBetween() {
        TimeZone timeZone = TimeZone.getTimeZone("Europe/Warsaw");
        Calendar calendar = Calendar.getInstance(timeZone);
        int hour = calendar.getTime().getHours();
        return hour >= guild.CUBOID_TNT_OD && hour <= guild.CUBOID_TNT_DO;
    }
}
