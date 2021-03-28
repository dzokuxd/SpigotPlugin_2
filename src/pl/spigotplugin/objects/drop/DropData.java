package pl.spigotplugin.objects.drop;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface DropData {
    void breakBlock(Block p0, Player p1, ItemStack p2);

    DropType getDropType();
}
