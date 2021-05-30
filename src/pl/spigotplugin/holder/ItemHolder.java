package pl.spigotplugin.holder;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.ItemBuilder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ItemHolder {
    private static final Map<String, ItemStack> items = new ConcurrentHashMap<>();

    public static void init() {
        put("gui.black", new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setTitle(ChatUtil.color("&7\u2022")).build());
    }

    private static void put(String key, ItemStack item) {
        items.put(key.toLowerCase(), item);
    }

    public static ItemStack get(String key) {
        return items.getOrDefault(key.toLowerCase(), new ItemStack(Material.DIRT));
    }
}
