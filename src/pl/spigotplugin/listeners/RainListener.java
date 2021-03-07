package pl.spigotplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class RainListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void weatherChange(WeatherChangeEvent event) {
        World world = Bukkit.getWorld("world");
        world.setWeatherDuration(0);
    }
}
