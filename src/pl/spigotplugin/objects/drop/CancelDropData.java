package pl.spigotplugin.objects.drop;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CancelDropData implements DropData {
    public void breakBlock(Block block, Player player, ItemStack item) {
        block.setType(Material.AIR);
    }

    public DropType getDropType() {
        return DropType.CANCEL_DROP;
    }
}