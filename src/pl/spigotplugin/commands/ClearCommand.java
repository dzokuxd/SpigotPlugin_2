package pl.spigotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.GlobalMessage;

public class ClearCommand extends PlayerCommand {
    public ClearCommand() { super("clear", "/clear", "", "ci"); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length == 0) {
            p.getInventory().setHelmet(null);
            p.getInventory().setChestplate(null);
            p.getInventory().setLeggings(null);
            p.getInventory().setBoots(null);
            p.getInventory().clear();
            p.sendMessage(GlobalMessage.MESSAGES_CLEAR);
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
        x.sendMessage(GlobalMessage.MESSAGES_CLEAR1.replace("{PLAYER}", p.getName()));
        p.sendMessage(GlobalMessage.MESSAGES_CLEAR2.replace("{PLAYER1}",x.getName()));
    }
}