package pl.spigotplugin.tasks;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.spigotplugin.commands.admin.LiveTpsCommand;
import pl.spigotplugin.utils.ChatUtil;

public class LiveTpsTask extends BukkitRunnable {
    @Override
    public void run() {
        int online = Bukkit.getOnlinePlayers().size();
        double tps = ChatUtil.round(MinecraftServer.getServer().recentTps[0], 2);
        for (Player p : LiveTpsCommand.using){
            ChatUtil.sendActionBar(p,"&6Online: &c" +online+" &6TPS: &c"+ tps);
        }
    }
}
