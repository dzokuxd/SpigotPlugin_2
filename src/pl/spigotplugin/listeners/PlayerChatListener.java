package pl.spigotplugin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerKickEvent;
import pl.spigotplugin.configs.statues;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.managers.ChatManager;
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.managers.MuteManager;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.objects.user.Mute;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.DataUtil;
import pl.spigotplugin.utils.GroupUtil;
import pl.spigotplugin.utils.TimeUtil;

import java.util.regex.Pattern;

public class PlayerChatListener implements Listener {
    public static Pattern URL_PATTERN = Pattern.compile("((?:(?:https?)://)?[\\a-_\\.]{2,})\\.([a-zA-Z]{2,3}(?:/\\S+)?)");
    public static Pattern BANNED_WORDS = Pattern.compile(".*(.ench|.pl|.tasrv|.crsv|.eu|.com|.aternos|aternos sie pali|Kopacz By|Buzkaa <3|Skrypt|Skrypt do kopania|By gizaar|gizaar+).*");
    public static Pattern IPPATTERN = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");


    @EventHandler
    public void handle(PlayerKickEvent event) {
        if (event.getReason().contains("disconnect.spam")) {
            event.setCancelled(true);
        }
    }
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
            if (!GroupUtil.have(p, RankType.HELPER)) {
                p.sendMessage(ChatUtil.color("&6Zostales wyciszony przez &c" + mute.getAdmin() + "&c, wygasa: &6" + ((mute.getTime() == 0L) ? "nigdy" : ("&cza &7" + DataUtil.secondsToString(mute.getTime()))) + "&c. Powod: &7" + mute.getReason()));
                e.setCancelled(true);
                return;
            }
        }
        if ((!GroupUtil.have(p, RankType.HELPER) && PlayerChatListener.URL_PATTERN.matcher(e.getMessage()).find()) || (!GroupUtil.have(p, RankType.MOD) && PlayerChatListener.IPPATTERN.matcher(e.getMessage()).find()) || (!GroupUtil.have(p, RankType.MOD) && PlayerChatListener.BANNED_WORDS.matcher(e.getMessage().toLowerCase()).find())) {
            p.sendMessage(ChatUtil.color("&cTwoja wiadomosc zawiera niedozwolone tresci!"));
            e.setCancelled(true);
            return;
        }
        if (!GroupUtil.have(p, RankType.HELPER) && statues.LVL > u.getLvl()) {
            p.sendMessage(ChatUtil.color("&7Czat jest dostepy od &c" + statues.LVL + " &7poziomu!"));
            e.setCancelled(true);
            return;
        }
        if (!GroupUtil.have(p, RankType.VIP) && ChatManager.vipChat) {
            p.sendMessage(ChatUtil.color("&cChat jest dostepny tylko dla rang premium"));
            e.setCancelled(true);
            return;
        }
        if (!GroupUtil.have(p, RankType.HELPER) && !ChatManager.enable && !ChatManager.vipChat) {
            p.sendMessage(ChatUtil.color("&cChat jest aktualnie wylaczony!"));
            e.setCancelled(true);
            return;
        }
        if (!GroupUtil.have(p, RankType.HELPER) && !u.isChat()) {
            p.sendMessage(ChatUtil.color("&7Na czacie bedziesz mogl pisac dopiero za &c" + DataUtil.secondsToString(u.getLastChat())));
            e.setCancelled(true);
            return;
        }
        if (message.startsWith("!")) {
            e.setCancelled(true);
            Guild g = GuildManager.getGuild(p);
            if (g == null) {
                p.sendMessage(ChatUtil.color("&cNie posiadasz gildii!"));
                return;
            }
            User user = UserManager.getUser(p);
            if (e.getMessage().startsWith("!")) {
                if (e.getMessage().length() <= 1) {
                    p.sendMessage(ChatUtil.color("wiadomosc").replace("/", "!"));
                    e.setCancelled(true);
                    return;
                }
                e.setCancelled(true);
                g.message(ChatUtil.color("&a" + p.getName() + "&8&l: &a" + e.getMessage().replaceFirst("!", "")));
                return;
            } else if (e.getMessage().startsWith("!!")) {
                if (e.getMessage().length() <= 1) {
                    p.sendMessage(ChatUtil.color("wiadomosc").replace("/", "!!"));
                    e.setCancelled(true);
                    return;
                }
                e.setCancelled(true);
                if (g.getAlly().isEmpty()) {
                    p.sendMessage(ChatUtil.color("Nie macie zadnych sojuszy!"));
                    return;
                }
                g.message("&9" + user.getGuild() + " " + p.getName() + "&8&l: &9" + e.getMessage().replaceFirst("!!", ""));
                for (String gg : g.getAlly()) {
                    Guild o = GuildManager.getGuild(gg);
                    if (o != null) {
                        o.message(ChatUtil.color("&9" + user.getGuild() + " " + p.getName() + "&8&l: &9" + e.getMessage().replaceFirst("!!", "")));
                    }
                }
            }
        }
        if (message.startsWith("@")) {
            e.setCancelled(true);
            Guild g = GuildManager.getGuild(p);
            if (g == null) {
                p.sendMessage(ChatUtil.color("&cNie posiadasz gildii!"));
                return;
            }
            if (e.getMessage().startsWith("!")) {
                if (e.getMessage().length() <= 1) {
                    e.setCancelled(true);
                    return;
                }
                e.setCancelled(true);
                g.message(ChatUtil.color("&2[DO GILDII] " + p.getName() + " &7Potrzebuje pomocy!"));
                g.message(ChatUtil.color("&2[DO GILDII] " + p.getName() + " &7Moje kordy to X: " + (int) p.getLocation().getX() + " Z: " + (int) p.getLocation().getZ() + " Y: " + (int) p.getLocation().getY()));
                return;
            } else if (e.getMessage().startsWith("@@")) {
                if (e.getMessage().length() <= 1) {
                    e.setCancelled(true);
                    return;
                }
                e.setCancelled(true);
                if (g.getAlly().isEmpty()) {
                    p.sendMessage(ChatUtil.color("Nie macie zadnych sojuszy!"));
                    return;
                }
                for (String gg : g.getAlly()) {
                    Guild o = GuildManager.getGuild(gg);
                    if (o != null) {
                        o.message(ChatUtil.color("&e[SOJUSZ] " + p.getName() + " &7Potrzebuje pomocy!"));
                        o.message(ChatUtil.color("&e[SOJUSZ] " + p.getName() + " &7Moje kordy to X: " + (int) p.getLocation().getX() + " Z: " + (int) p.getLocation().getZ() + " Y: " + (int) p.getLocation().getY()));
                    }
                }
            }
        }
        String globalFormat = core.CHAT_FORMAT_GLOBAL;
        if (!GroupUtil.have(p, RankType.HELPER)) {
            globalFormat = core.CHAT_FORMAT_ADMIN;
        }
        String guildFormat = core.CHAT_FORMAT_GUILD;
        u.setLastChat(System.currentTimeMillis() + TimeUtil.SECOND.getTime(statues.CHAT_SLOWMODE));
        globalFormat = globalFormat.replace("{GUILD}", (u.getGuild().isEmpty()) ? "" : guildFormat.replace("{TAG}", GuildManager.getGuild(u.getGuild()).getTag()));
        //globalFormat = globalFormat.replace("{PREFIX}", GroupUtil.have(user, String.valueOf()));
        globalFormat = globalFormat.replace("{PLAYER}", "%1$s");
        //globalFormat = globalFormat.replace("{SUFFIX}", PermissionsEx.getUser(p).getSuffix());
        globalFormat = globalFormat.replace("{MESSAGE}", "%2$s");
        globalFormat = globalFormat.replace("{LVL}", Integer.toString(u.getLvl()));
        if (!GroupUtil.have(p, RankType.HELPER)) {
            e.setMessage(ChatUtil.color(e.getMessage()));
        }
        e.setFormat(ChatUtil.color(globalFormat));
    }
}