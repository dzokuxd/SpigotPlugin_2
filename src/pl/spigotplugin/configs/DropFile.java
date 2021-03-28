package pl.spigotplugin.configs;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.spigotplugin.SpigotPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

public class DropFile {
    private static SpigotPlugin plugin;
    private static FileConfiguration dropConfig;
    private static File dropConfigFile;

    static {
        DropFile.plugin = SpigotPlugin.getPlugin();
        DropFile.dropConfig = null;
        DropFile.dropConfigFile = null;
    }

    public static void reloadConfig() {
        if (DropFile.dropConfigFile == null) {
            DropFile.dropConfigFile = new File(DropFile.plugin.getDataFolder(), "drops.yml");
        }
        DropFile.dropConfig = YamlConfiguration.loadConfiguration(DropFile.dropConfigFile);
        InputStream defConfigStream = DropFile.plugin.getResource("drops.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            DropFile.dropConfig.setDefaults(defConfig);
        }
    }

    public static FileConfiguration getConfig() {
        if (DropFile.dropConfig == null) {
            reloadConfig();
        }
        return DropFile.dropConfig;
    }

    public static void saveConfig() {
        if (DropFile.dropConfig == null || DropFile.dropConfigFile == null) {
            return;
        }
        try {
            getConfig().save(DropFile.dropConfigFile);
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not save config to " + DropFile.dropConfigFile, ex);
        }
    }

    public static void saveDefaultConfig() {
        if (DropFile.dropConfigFile == null) {
            DropFile.dropConfigFile = new File(DropFile.plugin.getDataFolder(), "drops.yml");
        }
        if (!DropFile.dropConfigFile.exists()) {
            DropFile.plugin.saveResource("drops.yml", false);
        }
    }
}
