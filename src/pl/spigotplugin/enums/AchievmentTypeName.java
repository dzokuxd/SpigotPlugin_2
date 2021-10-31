package pl.spigotplugin.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.ItemBuilder;

public enum AchievmentTypeName {

    STONE(9, new ItemBuilder(Material.STONE).setTitle("&6&lWykopany stone").addLore("").addLore("&7Kliknij, aby przejsc dalej!").build()),
    OBSIDIAN(10, new ItemBuilder(Material.OBSIDIAN).setTitle("&6&lWykopany obsidian").addLore("").addLore("&7Kliknij, aby przejsc dalej!").build()),
    KILLS(11, new ItemBuilder(Material.DIAMOND_SWORD).setTitle("&6&lZabojstwa").addLore("").addLore("&7Kliknij, aby przejsc dalej!").build()),
    ASYSTY(12, new ItemBuilder(Material.GOLD_SWORD).setTitle("&6&lAsysty").addLore("").addLore("&7Kliknij, aby przejsc dalej!").build()),
    TIME(15, new ItemBuilder(Material.WATCH).setTitle("&6&lSpedzony czas").addLore("").addLore("&7Kliknij, aby przejsc dalej!").build()),
    KOX(13, new ItemBuilder(Material.GOLDEN_APPLE,1,(short)1).setTitle("&6&lZjedzone koxy").addLore("").addLore("&7Kliknij, aby przejsc dalej!").build()),
    REF(14, new ItemBuilder(Material.GOLDEN_APPLE,1,(short)0).setTitle("&6&lZjedzone refile").addLore("").addLore("&7Kliknij, aby przejsc dalej!").build());

    private final int slot;
    private final ItemStack itemStack;

    AchievmentTypeName(int slot, ItemStack itemStack) {
        this.slot = slot;
        this.itemStack = itemStack;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
