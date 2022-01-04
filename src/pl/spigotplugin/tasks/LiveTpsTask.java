package pl.spigotplugin.tasks;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.spigotplugin.commands.admin.LiveTpsCommand;
import pl.spigotplugin.commands.admin.VanishCommand;
import pl.spigotplugin.helper.TabHelper;
import pl.spigotplugin.utils.ChatUtil;

public class LiveTpsTask extends BukkitRunnable {
    World world = Bukkit.getWorld("world");
    @Override
    public void run() {
        int online = Bukkit.getOnlinePlayers().size();
        double tps = ChatUtil.xD(MinecraftServer.getServer().recentTps[0], 2);
        for (Player p : LiveTpsCommand.using){
            ChatUtil.sendActionBar(p,"&fOnline: &d" +online+" &fTPS: &d"+ tps);
        }
        for (Player p : VanishCommand.using) {
            ChatUtil.sendActionBar(p, "&b&lAktualnie jestes niewidzialny!");//TODO jak bedziesz robil  castle event w xwh core masz wypierdalanie bossow
        }
    }
}
