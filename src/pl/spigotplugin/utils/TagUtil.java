package pl.spigotplugin.utils;

import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_8_R3.Scoreboard;
import net.minecraft.server.v1_8_R3.ScoreboardTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import pl.spigotplugin.SpigotPlugin;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.objects.user.User;

public class TagUtil {

    private static final Scoreboard scoreboard = new Scoreboard();

    public static void createBoard(Player p) {
        try {
            ScoreboardTeam team = null;
            if (scoreboard.getPlayerTeam(p.getName()) == null) {
                team = scoreboard.createTeam(p.getName());
            }
            scoreboard.addPlayerToTeam(p.getName(), team.getName());
            team.setPrefix("");
            team.setDisplayName("");
            team.setSuffix("");
            PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam(team, 0);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
            for (Player pp : Bukkit.getOnlinePlayers()) {
                if (pp == p) continue;
                ((CraftPlayer) pp).getHandle().playerConnection.sendPacket(packet);
                ScoreboardTeam t = scoreboard.getTeam(pp.getName());
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutScoreboardTeam(t, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void updateBoard(Player p) {
        Bukkit.getScheduler().runTaskAsynchronously(SpigotPlugin.getPlugin(), () -> {
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        updateOthersFor(p, online);
                        updateOthersFor(online, p);
                    }
                }
        );
    }

    private static void updateOthersFor(Player send, Player p) {
        ScoreboardTeam team = scoreboard.getPlayerTeam(p.getName());
        User get = UserManager.getUser(p.getName());
        User s = UserManager.getUser(send.getName());
        team.setPrefix(getValidPrefix(get, s));
        team.setSuffix(getValidSuffix(get));
        ((CraftPlayer) send).getHandle().playerConnection.sendPacket(new PacketPlayOutScoreboardTeam(team, 2));
    }


    private static String getValidPrefix(User get, User send) {
        ChatColor color = ChatColor.RED;
        Guild g = GuildManager.getGuild(get.getPlayer());
        Guild gs = GuildManager.getGuild(send.getPlayer());
        if (g != null && gs != null) {
            if (g == gs) {
                color = ChatColor.GREEN;
            } else if (g.getAlly().contains(gs.getTag())) {
                color = ChatColor.YELLOW;
            }
        }
        String tag = "";
        if (g != null) {
            tag = color + "[" + g.getTag() + "] ";
        }
        /*if (get.isIncognito() && send.getRankType()==RankType.HELPER)) {
            return ChatUtil.fixColor(tag);
        }
        if (g != null && gs != null) {
            if (g==gs) {
                return ChatUtil.fixColor(tag);
            }
        }
        if (get.isIncognito()) {
            tag = tag + ChatColor.MAGIC;
        }*/
        return tag;
    }

    private static String getValidSuffix(User u) {
        String suffix = "";
        if (u.getRankType()==RankType.PREZES) {
            suffix = "&4 W";
        } else if (u.getRankType()==RankType.HA) {
            suffix = "&4 HA";
        } else if (u.getRankType()==RankType.ADMIN) {
            suffix = "&4 A";
        } else if (u.getRankType()==RankType.MOD) {
            suffix = "&2 M";
        } else if (u.getRankType()==RankType.HELPER) {
            suffix = "&b H";
        } else if (u.getRankType()==RankType.SVIP) {
            suffix = "&e SVIP";
        } else if (u.getRankType()==RankType.EASY) {
            suffix = "&4 Y&fT";
        } else if (u.getRankType()==RankType.VIP) {
            suffix = "&6 VIP";
        }
        /*if (VanishManager.isVanish(u.getPlayer())) {
            suffix += " &8[&bV&8]";
        }*/
        if (suffix.length() > 16) {
            suffix = suffix.substring(0,16);
        }
        return ChatUtil.color(suffix);
    }

    public static void removeBoard(Player p) {
        ScoreboardTeam team = scoreboard.getPlayerTeam(p.getName());
        scoreboard.removePlayerFromTeam(p.getName(), team);
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam(team, 1);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        for (Player pp : Bukkit.getOnlinePlayers()) {
            if (pp == p) continue;
            ((CraftPlayer) pp).getHandle().playerConnection.sendPacket(packet);
            ScoreboardTeam t = scoreboard.getTeam(pp.getName());
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutScoreboardTeam(t, 1));
        }
        scoreboard.removeTeam(team);
    }
}