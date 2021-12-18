package pl.spigotplugin.commands.player;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.utils.ChatUtil;

public class BlocksCommand extends PlayerCommand {
    public BlocksCommand() {super("bloki", "bloki", "");}

    @Override
    public void onCommand(Player p, String[] args) {
        p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1.0f, 1.0f);
        ChatUtil.replace(p, Material.REDSTONE, Material.REDSTONE_BLOCK);
        ChatUtil.replace(p, Material.GOLD_INGOT, Material.GOLD_BLOCK);
        ChatUtil.replace(p, Material.EMERALD, Material.EMERALD_BLOCK);
        ChatUtil.replace(p, Material.IRON_INGOT, Material.IRON_BLOCK);
        ChatUtil.replace(p, Material.DIAMOND, Material.DIAMOND_BLOCK);
        p.sendMessage("&aPomyslnie zamieniles wszystko na bloki!");
    }
}
