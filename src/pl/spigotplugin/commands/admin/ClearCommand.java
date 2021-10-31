package pl.spigotplugin.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;

public class ClearCommand extends PlayerCommand {
    public ClearCommand() { super("clear", "clear", "spigot.clear", "ci"); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length == 0) {
            p.getInventory().setHelmet(null);
            p.getInventory().setChestplate(null);
            p.getInventory().setLeggings(null);
            p.getInventory().setBoots(null);
            p.getInventory().clear();
            p.sendMessage(core.CLEAR_YOU);
            return;
        }
        Player x = Bukkit.getPlayer(args[0]);
        if (x == null) {
            p.sendMessage("&cGracz jest offline");
            return;
        }
        x.getInventory().setHelmet(null);
        x.getInventory().setChestplate(null);
        x.getInventory().setChestplate(null);
        x.getInventory().setBoots(null);
        x.getInventory().clear();
        x.sendMessage(core.CLEAR_OTHER.replace("{PLAYER}", p.getName()));
        p.sendMessage(core.CLEAR_PLAYER.replace("{PLAYER1}",x.getName()));
    }
}