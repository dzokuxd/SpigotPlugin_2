package pl.spigotplugin.tasks;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.spigotplugin.commands.LiveTpsCommand;
import pl.spigotplugin.utils.ChatUtil;

public class LiveTpsTask extends BukkitRunnable {
    @Override
    public void run() {
        int online = Bukkit.getOnlinePlayers().size();
        int max = Bukkit.getMaxPlayers();
        long free = Runtime.getRuntime().freeMemory() / 1024L / 1024L;
        double tps = ChatUtil.round(MinecraftServer.getServer().recentTps[0], 2);
        for (Player p : LiveTpsCommand.using){
            ChatUtil.sendActionBar(p,"&6Online: &c" +online+"&6/&c" + max + " &6FREE RAM: &c"+ free + "MB &6TPS: &c"+ tps);
        }
    }
}
