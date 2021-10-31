package pl.spigotplugin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.spigotplugin.configs.statues;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.managers.ChatManager;
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.managers.MuteManager;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.objects.user.Mute;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.DataUtil;
import pl.spigotplugin.utils.TimeUtil;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.regex.Pattern;

public class PlayerChatListener implements Listener {
    public static Pattern URL_PATTERN = Pattern.compile("((?:(?:https?)://)?[\\a-_\\.]{2,})\\.([a-zA-Z]{2,3}(?:/\\S+)?)");
    public static Pattern BANNED_WORDS = Pattern.compile(".*(.ench|.pl|.tasrv|.crsv|.eu|.com|.aternos|aternos sie pali|Kopacz By|Buzkaa <3|Skrypt|Skrypt do kopania|By gizaar|gizaar+).*");
    public static Pattern IPPATTERN = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Player p = e.getPlayer();
        User u = UserManager.getUser(p);
        String message = e.getMessage();
        if (u == null) {
            e.setCancelled(true);
            return;
        }
        Mute mute = MuteManager.getMute(p);
        if (mute != null) {
            if (mute.getTime() != 0L && mute.getTime() <= System.currentTimeMillis()) {
                MuteManager.unmute(mute);
            }
            if (!p.hasPermission("spigot.bypass")) {
                p.sendMessage("&6Zostales wyciszony przez &c" + mute.getAdmin() + "&c, wygasa: &6" + ((mute.getTime() == 0L) ? "nigdy" : ("&cza &7" + DataUtil.secondsToString(mute.getTime()))) + "&c. Powod: &7" + mute.getReason());
                e.setCancelled(true);
                return;
            }
        }
        if ((!p.hasPermission("spigot.bypass") && PlayerChatListener.URL_PATTERN.matcher(e.getMessage()).find()) || (!p.hasPermission("core.chat.bypass") && PlayerChatListener.IPPATTERN.matcher(e.getMessage()).find()) || (!p.hasPermission("core.chat.bypass") && PlayerChatListener.BANNED_WORDS.matcher(e.getMessage().toLowerCase()).find())) {
            p.sendMessage("&cTwoja wiadomosc zawiera niedozwolone tresci!");
            e.setCancelled(true);
            return;
        }
        if (!p.hasPermission("spigot.bypass") && statues.LVL > u.getLvl()) {
            p.sendMessage("&7Czat jest dostepy od &c" + statues.LVL + " &7poziomu!");
            e.setCancelled(true);
            return;
        }
        if (!p.hasPermission("spigot.chatvip") && ChatManager.vipChat) {
            p.sendMessage("&cChat jest dostepny tylko dla rang premium");
            e.setCancelled(true);
            return;
        }
        if (!p.hasPermission("spigot.bypass") && !ChatManager.enable && !ChatManager.vipChat) {
            p.sendMessage("&cChat jest aktualnie wylaczony!");
            e.setCancelled(true);
            return;
        }
        if (!p.hasPermission("spigot.bypass") && !u.isChat()) {
            p.sendMessage("&7Na czacie bedziesz mogl pisac dopiero za &c" + DataUtil.secondsToString(u.getLastChat()));
            e.setCancelled(true);
            return;
        }
        if (message.startsWith("!!")) {
            e.setCancelled(true);
            Guild g = GuildManager.getGuild(p);
            if (g == null) {
                p.sendMessage("&cNie posiadasz gildii!");
                return;
            }
            String msg = message.replaceFirst("!!", "").replace("&", "");
            g.message("&8[&9DO SOJUSZY&8] &8[&9" + g.getTag() + "&8] &6" + p.getName() + "&8: &7" + msg);
            for (String s : g.getAlly()) {
                Guild o = GuildManager.getGuild(s);
                if (o != null) {
                    o.message("&8[&9DO SOJUSZY&8] &8[&9" + g.getTag() + "&8] &6" + p.getName() + "&8: &7" + msg);
                }
            }
        } else if (message.startsWith("!")) {
            e.setCancelled(true);
            Guild g = GuildManager.getGuild(p);
            if (g == null) {
                p.sendMessage("&cNie posiadasz gildii!");
                return;
            }
            String msg = message.replaceFirst("!", "").replace("&", "");
            g.message("&8[&2DO GILDII&8] &a" + p.getName() + "&8: &7" + msg);
        } else if (message.startsWith("@")) {
            e.setCancelled(true);
            Guild g = GuildManager.getGuild(p);
            if (g == null) {
                p.sendMessage("&cNie posiadasz gildii!");
                return;
            }
            g.message("&8[&2DO GILDII&8] &a" + p.getName() + "&8: &7Potrzebuje pomocy!");
            g.message("&8[&2DO GILDII&8] &a" + p.getName() + "&8: &7Moje kordy to X: " + (int) p.getLocation().getX() + " Z: " + (int) p.getLocation().getZ() + " Y: " + (int) p.getLocation().getY());
        }
        String globalFormat = core.CHAT_FORMAT_GLOBAL;
        if (p.hasPermission("spigotplugin.admin")) {
            globalFormat = core.CHAT_FORMAT_ADMIN;
        }
        String guildFormat = core.CHAT_FORMAT_GUILD;
        u.setLastChat(System.currentTimeMillis() + TimeUtil.SECOND.getTime(statues.CHAT_SLOWMODE));
        globalFormat = globalFormat.replace("{GUILD}", (u.getGuild().isEmpty()) ? "" : guildFormat.replace("{TAG}", GuildManager.getGuild(u.getGuild()).getTag()));
        globalFormat = globalFormat.replace("{PREFIX}", PermissionsEx.getUser(p).getPrefix());
        globalFormat = globalFormat.replace("{PLAYER}", "%1$s");
        globalFormat = globalFormat.replace("{SUFFIX}", PermissionsEx.getUser(p).getSuffix());
        globalFormat = globalFormat.replace("{MESSAGE}", "%2$s");
        globalFormat = globalFormat.replace("{LVL}", Integer.toString(u.getLvl()));
        if (p.hasPermission("spigotplugin.color")) {
            e.setMessage(ChatUtil.color(e.getMessage()));
        }
        e.setFormat(ChatUtil.color(globalFormat));
    }
}
