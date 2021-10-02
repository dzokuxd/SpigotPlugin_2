package pl.spigotplugin.managers;

import org.bukkit.entity.Player;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.utils.ChatUtil;

import java.util.Collections;
import java.util.List;

public class TabManager {
    public static String getReplacementR(Integer i) {
        if (TopsManager.kills.size() >= i) {
            String s = "&6" + i + ". &c";
            if (i > 9) {
                s = "&6" + i + ". &c";
            }
            return s + TopsManager.kills.get(i - 1).getName();
        }
        return "";
    }

    public static String getReplacementG(Integer i) {
        if (TopsManager.guildRankings.size() >= i) {
            Guild guild = TopsManager.guildRankings.get(i - 1);
            String s = "&6" + i + ". &c";
            if (i > 9) {
                s = "&6" + i + ". &c";
            }
            return ChatUtil.color(s + "&c" + guild.getTag() + "&8~ &7" + guild.getPoints());
        }
        return "";
    }

    public static String getmember(Integer i, List<String> members) {
        if (members.size() >= i) {
            String ss = members.get(i - 1);
            String s = "&a";
            if (i > 9) {
                s = "&a";
            }
            return ChatUtil.color(s + ss);
        }
        return "";
    }

    public static String getZgony(Player player) {
        Guild g = GuildManager.getGuild(player);
        return (g == null) ? "&7Zgony: &6Brak" : ("&7Zgony: &6" + g.getDeaths());
    }

    public static String getHp(Player player) {
        Guild g = GuildManager.getGuild(player);
        return (g == null) ? "&7HP: &6Brak" : ("&7HP: &6" + g.getHp() + "&8/&61000");
    }

    /*public static String getKD(Player player) {
        Guild g = GuildManager.getGuild(player);
        return (g == null) ? "&7KD-RATIO: &6Brak" : ("&7KD-RATIO: &6" + g.getKd());
    }*/

    public static String getKill(Player player) {
        Guild g = GuildManager.getGuild(player);
        return (g == null) ? "&7Zabojstwa: &6Brak" : ("&7Zabojstwa: &6" + g.getKills());
    }

    public static String getPoints(Player player) {
        Guild g = GuildManager.getGuild(player);
        return (g == null) ? "&7Punkty: &6Brak" : ("&7Punkty: &6" + g.getPoints());
    }

    public static String getGuild(Player player) {
        Guild g = GuildManager.getGuild(player);
        return (g == null) ? "&7Gildia: &6Brak" : ("&7Gildia: &6" + g.getTag());
    }
}
