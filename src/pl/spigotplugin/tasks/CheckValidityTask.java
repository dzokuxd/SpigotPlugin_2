package pl.spigotplugin.tasks;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.spigotplugin.helper.TabHelper;
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.TabUtil;

public class CheckValidityTask extends BukkitRunnable {
    public void run() {
        for (Guild g : GuildManager.getGuilds().values()) {
            if (g.getProlong() < System.currentTimeMillis()) {
                GuildManager.deleteGuild(g);
                Bukkit.broadcastMessage(ChatUtil.color("&4Gildia " +g.getTag()+ " - " +g.getName()+ " wygasla! Ich kordy to x: " +g.getRegion().getX()+ "z: " +g.getRegion().getZ()));
            }
        }
    }
}