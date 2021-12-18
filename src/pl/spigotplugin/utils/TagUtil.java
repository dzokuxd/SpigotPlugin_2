package pl.spigotplugin.utils;

import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_8_R3.Scoreboard;
import net.minecraft.server.v1_8_R3.ScoreboardTeam;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.objects.user.User;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class TagUtil {
    private static final Scoreboard scoreboard = new Scoreboard();

    public static void createBoard(Player p) {
        ScoreboardTeam team = null;
        if (scoreboard.getPlayerTeam(Bukkit.getPlayer(p.getName()).getName()) == null) {
            team = scoreboard.createTeam(p.getName());
        }
        assert team != null;
        scoreboard.addPlayerToTeam(p.getName(), team.getName());

        team.setPrefix("");
        team.setDisplayName("");
        team.setSuffix("");
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam(team, 0);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);

        for (Player pp : Bukkit.getOnlinePlayers()) {
            if (pp == p) return;
            ((CraftPlayer) pp).getHandle().playerConnection.sendPacket(packet);

            ScoreboardTeam scoreboardTeam = scoreboard.getTeam(pp.getName());

            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutScoreboardTeam(scoreboardTeam, 0));
        }
    }

    public static void updateBoard(Player p) {
        if (p != null) {
            ScoreboardTeam team = scoreboard.getPlayerTeam(p.getName());
            team.setDisplayName("");
            Bukkit.getOnlinePlayers().forEach(player -> {
                String validPrefix = getValidPrefix(p, player);
                if (validPrefix.length() > 16) {
                    validPrefix = validPrefix.substring(0, 15);
                }
                team.setPrefix(validPrefix);
                String suffix = "";
                PermissionUser uu = PermissionsEx.getUser(p);
                if (uu.inGroup("Prezes")) {
                    suffix = ChatUtil.color(" &ePrezes");
                }
                if (uu.inGroup("H@")) {
                    suffix = ChatUtil.color(" &4H@");
                }
                if (uu.inGroup("admin")) {
                    suffix = " &cA";
                }
                if (uu.inGroup("mod")) {
                    suffix = " &2Mod";
                }
                if (uu.inGroup("helper")) {
                    suffix = " &3H";
                }
                if (uu.inGroup("easy")) {
                    suffix = " &5EASY";
                }
                if (uu.inGroup("svip")) {
                    suffix = " &dSVIP";
                }
                if (uu.inGroup("vip")) {
                    suffix = " &6VIP";
                }
                if (uu.inGroup("gracz")) {
                    suffix = "&f";
                }

                team.setSuffix(ChatUtil.color(suffix));

                PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam(team, 2);
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
            });
        }
    }


    private static String getValidPrefix(Player get, Player send) {
        String color = "&c";
        User u = UserManager.getUser(get);
        if (u == null) return "";
        Guild g = GuildManager.getGuild(get);
        Guild o = GuildManager.getGuild(send);
        boolean chuj = false;
        boolean shouldAddQuestionMark = false;
        if (g != null && o != null) {
            if (g.equals(o)) {
                color = "&a";
                chuj = true;
            } else if (g.getAlly().contains(o.getTag())) {
                color = "&9";
                chuj = true;
            }
        }
        String tag = "";
        if (g != null) {
            tag = "&8[" + color + g.getTag() + "&8] " + color;
            shouldAddQuestionMark = true;
        }
        String tag1234;
        if (!chuj && !send.hasPermission("spigotplugin.bypass")) {
            tag1234 = "&k";
        } else {
            tag1234 = tag;
        }

        if (shouldAddQuestionMark && !chuj) {
            if (!send.hasPermission("spigotplugin.bypass")) {
               tag1234 = "&8[&c?&8] &c&k ";
            }
        }

        ChatColor nameColor = ChatColor.WHITE;
        if (u.focused.equals(send.getName())) {
            nameColor = ChatColor.LIGHT_PURPLE;
        }

        get.sendMessage(nameColor.name());
        get.sendMessage(u.focused);

        return (u.isIncognito() ? ChatUtil.color(tag1234) + nameColor : ChatUtil.color(tag) + nameColor);
    }

    public static void removeBoard(Player p) {
        if (scoreboard.getPlayerTeam(Bukkit.getPlayer(p.getName()).getName()) == null) {
            return;
        }
        ScoreboardTeam team = TagUtil.scoreboard.getPlayerTeam(p.getName());
        scoreboard.removePlayerFromTeam(p.getName(), team);

        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam(team, 1);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);

        Bukkit.getOnlinePlayers().stream().filter(pp -> pp != p).forEach(pp -> {
            ((CraftPlayer) pp).getHandle().playerConnection.sendPacket(packet);

            ScoreboardTeam scoreboardTeam = TagUtil.scoreboard.getTeam(pp.getName());
            PacketPlayOutScoreboardTeam packetHide = new PacketPlayOutScoreboardTeam(scoreboardTeam, 1);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetHide);
        });
        scoreboard.removeTeam(team);
    }
}