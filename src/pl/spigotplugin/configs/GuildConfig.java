package pl.spigotplugin.configs;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class GuildConfig {

    public static List<ItemStack> COST_CREATE = Arrays.asList(
            new ItemStack(Material.DIAMOND,64),
            new ItemStack(Material.GOLDEN_APPLE,64),
            new ItemStack(Material.BOOKSHELF,64),
            new ItemStack(Material.GLASS,64),
            new ItemStack(Material.TNT,64),
            new ItemStack(Material.LEAVES,64),
            new ItemStack(Material.ENDER_PEARL,16),
            new ItemStack(Material.ANVIL,64),
            new ItemStack(Material.HAY_BLOCK,64)
    );
    public static ItemStack COST_INVITE = new ItemStack(Material.DIAMOND,8);
    public static ItemStack COST_DEPUTY = new ItemStack(Material.DIAMOND,8);
    public static ItemStack COST_LEADER = new ItemStack(Material.DIAMOND,8);
}
