package pl.spigotplugin.tasks;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.objects.guild.Guild;

public class CheckValidityTask extends BukkitRunnable {
    public void run() {
        for (Guild g : GuildManager.getGuilds().values()) {
            if (g.getProlong() < System.currentTimeMillis()) {
                GuildManager.deleteGuild(g);
                Bukkit.broadcastMessage("&6Gildia &c" +g.getTag()+ " &7- &c" +g.getName()+ "&6wygasla! Ich kordy to x: &c" +g.getRegion().getX()+ "&7z: &c " +g.getRegion().getZ());
            }
        }
    }
}