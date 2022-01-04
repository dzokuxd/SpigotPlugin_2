package pl.spigotplugin.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.GroupUtil;

public class HealCommand extends PlayerCommand {
    public HealCommand() { super("heal",  RankType.HELPER); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length == 0) {
            p.setFireTicks(0);
            p.setHealth(p.getMaxHealth());
            p.setFoodLevel(20);
            for (PotionEffect effect : p.getActivePotionEffects()) {
                p.removePotionEffect(effect.getType());
            }
            p.sendMessage(ChatUtil.color("&aZostales uleczony!"));
            return;
        }
        if (!(GroupUtil.have(p, RankType.MOD))) {
            p.sendMessage(ChatUtil.color("&cNie masz dostepu!"));
            return;
        }
        Player o = Bukkit.getPlayer(args[0]);
        if (o == null) {
            p.sendMessage(ChatUtil.color("&cGracz jest offline!"));
            return;
        }
        o.setFireTicks(0);
        o.setHealth(p.getMaxHealth());
        o.setFoodLevel(20);
        for (PotionEffect effect : o.getActivePotionEffects()) {
            o.removePotionEffect(effect.getType());
        }
        o.sendMessage(ChatUtil.color("&fZostales uleczony przez &d" + p.getName()));
        p.sendMessage(ChatUtil.color("&fUleczyles &d" + o.getName()));
    }
}
