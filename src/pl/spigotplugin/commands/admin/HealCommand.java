package pl.spigotplugin.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import pl.spigotplugin.api.PlayerCommand;

public class HealCommand extends PlayerCommand {
    public HealCommand() { super("heal", "heal <gracz>", "spigot.heal"); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length == 0) {
            p.setFireTicks(0);
            p.setHealth(p.getMaxHealth());
            p.setFoodLevel(20);
            for (PotionEffect effect : p.getActivePotionEffects()) {
                p.removePotionEffect(effect.getType());
            }
            p.sendMessage("&aZostales uleczony!");
            return;
        }
        if (!p.hasPermission("core.cmd.admin")) {
            p.sendMessage("&cNie masz dostepu!");
            return;
        }
        Player o = Bukkit.getPlayer(args[0]);
        if (o == null) {
            p.sendMessage("&cGracz jest offline!");
            return;
        }
        o.setFireTicks(0);
        o.setHealth(p.getMaxHealth());
        o.setFoodLevel(20);
        for (PotionEffect effect : o.getActivePotionEffects()) {
            o.removePotionEffect(effect.getType());
        }
        o.sendMessage("&fZostales uleczony przez &d" + p.getName());
        p.sendMessage("&fUleczyles &d" + o.getName());
    }
}
