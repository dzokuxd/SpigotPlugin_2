package pl.spigotplugin.managers;

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.enums.ArmorType;

import java.util.HashMap;

public class DataManager
{
    private static final HashMap<String, ArmorType> disco = new HashMap<>();
    private static final HashMap<String, Color> lastColor = new HashMap<>();
    private static final HashMap<String, ItemStack[]> shiftArmor = new HashMap<>();

    public static HashMap<String, ArmorType> getDisco() {
        return DataManager.disco;
    }

    public static HashMap<String, Color> getLastColor() {
        return DataManager.lastColor;
    }

    public static HashMap<String, ItemStack[]> getShiftArmor() {
        return DataManager.shiftArmor;
    }
}
