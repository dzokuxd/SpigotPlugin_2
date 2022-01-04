package pl.spigotplugin.commands.player;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.guild;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.DataUtil;
import pl.spigotplugin.utils.TimeUtil;

import java.util.List;

public class GuildInfoCommand extends PlayerCommand {
    public GuildInfoCommand() {super("gildia", RankType.GRACZ, "ginfo");}

    @Override
    public void onCommand(Player p, String[] args) {
        Guild g;
        if (args.length == 0) {
            g = GuildManager.getGuild(p);
        } else {
            g = GuildManager.getGuild(args[0]);
        }
        if (g == null && args.length == 0) {
            p.sendMessage(ChatUtil.color("&cNie posiadasz gildii!"));
            return;
        } else if (g == null) {
            p.sendMessage(ChatUtil.color("&cGildia o takim tagu nie istnieje!"));
            return;
        }
        p.sendMessage(ChatUtil.color("&7&m-----------[&r &d&l" + g.getTag() + " &7&m-&r &d&l" + g.getName() + " &7]&7&m-----------"));
        p.sendMessage(ChatUtil.color("&7\u00bb &fKordy: &dx"+g.getRegion().getX()+"&f/&dy "+g.getRegion().getZ()));
        p.sendMessage(ChatUtil.color("&7\u00bb &fZalozyciel: &d" + g.getLeader()));
        p.sendMessage(ChatUtil.color("&7\u00bb &fZastepca: &d" + (g.getDeputy().equalsIgnoreCase("null") ? "Brak" : g.getDeputy())));
        p.sendMessage(ChatUtil.color("&7\u00bb &fPunkty: &d" + g.getPoints()));
        p.sendMessage(ChatUtil.color("&7\u00bb &fZabojstwa: &d" + g.getKills()));
        p.sendMessage(ChatUtil.color("&7\u00bb &fSmierci: &d" + g.getDeaths()));
        p.sendMessage(ChatUtil.color("&7\u00bb &fHP: &d" + g.getHp() + " &fZycia: &d" + g.getLife()));
        int size = g.getRegion().getSize() * 2 + 1;
        p.sendMessage(ChatUtil.color("&7\u00bb &fTeren &d" + size + "&fx&d" + size));
        p.sendMessage(ChatUtil.color("&7\u00bb &fUtworzona: &d" + DataUtil.getDate(g.getCreateTime())));
        p.sendMessage(ChatUtil.color("&7\u00bb &fOchrona tnt: &d" + (g.isProtected() ? DataUtil.secondsToString(g.getCreateTime() + TimeUtil.HOUR.getTime(guild.CUBOID_PROTECTION_HOWHOUR)) : "&cNie")));
        p.sendMessage(ChatUtil.color("&7\u00bb &fWygasa " + (g.isExits() ? "&fza: &d" + DataUtil.secondsToString(g.getProlong()) : " &cWygasla")));
        p.sendMessage(ChatUtil.color("&7\u00bb &fCzlonkow: &d" + g.getMembers().size() + "&f/&d" + g.getPlayersLimit() + "&7, &fOnline: &d" + g.getOnlineMembers().size()));
        p.sendMessage(ChatUtil.color("&7\u00bb &fCzlonkow: " + StringUtils.join(g.getMembers(), "&7, ")));
        p.sendMessage(ChatUtil.color(g.getALlyList().replaceFirst("&f, ", "") + " &7[&d" + g.getAlly().size() + "&7/&d" + 2 + "&7]"));
        p.sendMessage(ChatUtil.color("&7\u00bb &fWojny: &4" + (g.getWars().isEmpty() ? "BRAK" : g.getWars())));
        p.sendMessage(ChatUtil.color("&7&m-----------[&r &d&l" + g.getTag() + " &7&m-&r &d&l" + g.getName() + " &7]&7&m-----------"));
    }
}
