package pl.spigotplugin.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.spigotplugin.SpigotPlugin;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.utils.ChatUtil;

public class GuildRegenerationTask {
    public static void regen(Guild g) {
        final int[] dupson = {0};
        final boolean[] first = {true};
        new BukkitRunnable() {
            @SuppressWarnings("deprecation")
            @Override
            public void run() {
                String[] kors = g.getRegen().split("!");
                if(first[0]) {
                    dupson[0] = kors.length;
                    first[0] = false;
                }
                if(g.getBlocksToRegen() >= kors.length) {
                    if (g.getRegen().contains("!")) {
                        if(!g.isStartedRegen())
                            g.setStartedRegen(true);
                        String[] kory = kors[0].split(":");
                        int x = Integer.parseInt(kory[0]);
                        int y = Integer.parseInt(kory[1]);
                        int z = Integer.parseInt(kory[2]);
                        String meterial = kory[3];
                        byte data = Byte.parseByte(kory[4]);
                        int liczba = kors[0].length() + 1;
                        Location location = new Location(Bukkit.getWorld("world"), x, y, z);
                        Material material = Material.matchMaterial(meterial);
                        if (location.getBlock().isEmpty()) {
                            location.getBlock().setType(material);
                            location.getBlock().setData(data);
                        }
                        g.setRegen(g.getRegen().substring(liczba));
                        if(g.getBlocksToRegen() % 10 == 0) {
                            g.setGold(g.getGold() - 1);
                        }
                        g.setBlocksToRegen(g.getBlocksToRegen() - 1);
                        for (String player : g.getMembers()) {
                            Player p = Bukkit.getPlayer(player);
                            if (p != null) {
                                String[] spliter = g.getRegen().split("!");
                                ChatUtil.sendActionBar(p, ChatUtil.color("&7Regeneracja (&c" + spliter.length + "&7/&c"+dupson[0]+"&7)"));
                            }
                        }
                    } else {
                        g.setStartedRegen(false);
                        cancel();
                        g.setRegen("");
                        g.saveGold(g.getGold());
                    }
                } else {
                    g.setStartedRegen(false);
                    g.saveGold(g.getGold());
                    cancel();
                }
            }
        }.runTaskTimer(SpigotPlugin.getPlugin(), 1L, 1L);
    }
}
