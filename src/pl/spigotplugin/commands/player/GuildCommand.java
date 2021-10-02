package pl.spigotplugin.commands.player;

import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.component.Teleporter;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.configs.guild;
import pl.spigotplugin.holder.LocationHolder;
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.menu.PanelMenu;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.tasks.GuildRegenerationTask;
import pl.spigotplugin.utils.*;

import javax.swing.text.html.HTML;
import java.util.concurrent.TimeUnit;

public class GuildCommand extends PlayerCommand {
    public GuildCommand() { super("gildie", "gildie", "","g"); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length < 1) {
            usage(p);
            return;
        }
        final User user = UserManager.getUser(p);
        switch (args[0].toLowerCase()){
            case "zaloz": {
                if (args.length !=3) {
                    p.sendMessage("&7Prawidlowe uzycie: &c/g zaloz <tag> <pelna nazwa>");
                    return;
                }
                if (!p.hasPermission("spigotplugin.manage") && !Config.MANAGE_GUILDCREATE) {
                    p.sendMessage("&cZakladanie gildii jest tymczasowo wylaczone!");
                    return;
                }
                if (!user.getGuild().isEmpty())  {
                    p.sendMessage(guild.CREATE_HAVEGUILD);
                    return;
                }
                String tag = args[1].toUpperCase();
                String name = args[2];
                if (tag.length() >5 || tag.length() <2 || name.length() > 32 || name.length() <4){
                    p.sendMessage(guild.CREATE_WRONGTAGANDNAME);
                    return;
                }
                if (GuildManager.getGuild(tag) !=null) {
                    p.sendMessage(guild.CREATE_ALLREADYEXISTSBYSHORTCUT);
                    return;
                }
                if (GuildManager.getGuild(name) !=null) {
                    p.sendMessage(guild.CREATE_ALLREADYEXISTSBYFULLNAME);
                    return;
                }
                if (!ChatUtil.isAlphaNumeric(tag)) {
                    p.sendMessage(guild.CREATE_SHORTCUTNOTALPHANUMERIC);
                    return;
                }
                if (!ChatUtil.isAlphaNumeric(name)) {
                    p.sendMessage(guild.CREATE_FULLNAMENOTALPHANUMERIC);
                    return;
                }
                if (!GuildManager.canCreateGuildBySpawn(p.getLocation())) {
                    p.sendMessage(guild.CREATE_TOCLOSESPAWN);
                    return;
                }
                if (!GuildUtil.hasItems(p))
                    return;
                GuildUtil.removeItems(p);
                Guild g = GuildManager.createGuild(
                        tag,
                        name,
                        p,
                        p.getLocation().clone());
                user.setGuild(g.getTag());
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 5.0f, 3.0f);
                Bukkit.broadcastMessage(guild.CREATE_BROADCAST.replace("{TAG}",g.getTag()).replace("{NAME}",g.getName()).replace("{PLAYER}",p.getName()));
                ChatUtil.sendTitleMessage(p, guild.CREATE_TITLE.replace("{TAG}",g.getTag()), guild.CREATE_SUBTITLE.replace("{PLAYER}",p.getName()),30,70,40);
                TagUtil.updateBoard(p);
                break;
            }
            case "zapros": {
                Guild g = GuildManager.getGuild(p);
                if (args.length != 2) {
                    p.sendMessage("&6Prawidlowe uzycie: &c/g zapros <gracz/*>");
                    return;
                }
                if (g == null) {
                    p.sendMessage(guild.INVITE_DONTHAVEAGUILD);
                    return;
                }
                if (!g.isDeputy(p.getName())){
                    p.sendMessage(guild.INVITE_NOPERMISSION);
                    return;
                }
                ItemStack costInvite = guild.INVITE_COST;
                if (args[1].equalsIgnoreCase("*")) {
                    int i = 0;
                    for (Player players : LocationUtil.getPlayersInRadius(p.getLocation(), 5)) {
                        if (players == p) continue;
                        ++i;
                        if (!p.getInventory().containsAtLeast(costInvite, costInvite.getAmount())) {
                            p.sendMessage("&cNie posiadasz " + costInvite.getType() + "x" + costInvite.getAmount());
                            return;
                        }
                        if (i == 0) {
                            p.sendMessage(guild.INVITE_GROUPMESSAGE);
                            return;
                        }
                        if (g.getInvites().contains(players.getUniqueId())) {
                            p.sendMessage(guild.INVITE_GROUPERROR);
                            continue;
                        }
                        p.getInventory().removeItem(costInvite);
                        g.getInvites().add(players.getUniqueId());
                        players.sendMessage(guild.INVITE_TARGET1.replace("{TAG}",g.getTag()).replace("{PLAYER}",p.getName()));
                        players.sendMessage(guild.INVITE_TARGET2.replace("{TAG}",g.getTag()));
                    }
                    p.sendMessage("&aZaprosiles " + i + " graczy do gildii");
                } else {
                    User o = UserManager.getUser(args[1]);
                    if (o == null) {
                        p.sendMessage(guild.PLAYER_USERNULL);
                        return;
                    }
                    Player player = o.getPlayer();
                    if (player == null) {
                        p.sendMessage(guild.PLAYER_ISOFFLINE);
                        return;
                    }
                    Guild go = GuildManager.getGuild(player);
                    if (go != null) {
                        p.sendMessage(guild.CREATE_HAVEGUILD);
                        return;
                    }
                    if (g.getInvites().contains(player.getUniqueId())) {
                        g.getInvites().remove(player.getUniqueId());
                        player.sendMessage(guild.INVITE_COFNIETE1.replace("{TAG}",g.getTag()).replace("{PLAYER}",p.getName()));
                        p.sendMessage(guild.INVITE_COFNIETE2.replace("{TARGET}",o.getName()));
                        return;
                    }
                    if(!p.getInventory().containsAtLeast(costInvite, costInvite.getAmount())){
                        p.sendMessage("&cNie posiadasz " + costInvite.getType() + "x" + costInvite.getAmount());//TODO zrob to wszedzie
                        return;
                    }
                    g.getInvites().add(player.getUniqueId());
                    p.getInventory().removeItem(costInvite);
                    p.sendMessage("&6Zaprosiles &c"+o.getName()+ "&6do gildii");
                    p.sendMessage(guild.INVITE_TARGET1.replace("{TAG}",g.getTag()).replace("{PLAYER}",p.getName()));
                    p.sendMessage(guild.INVITE_TARGET2.replace("{TAG}",g.getTag()));
                    return;
                }
                break;
            }
            case "wyrzuc": {
                if (args.length != 2) {
                    p.sendMessage("&6Prawidlowe uzycie: &c/g wyrzuc <gracz>");
                    return;
                }
                Guild g = GuildManager.getGuild(p);
                if (g == null) {
                    p.sendMessage(guild.INVITE_DONTHAVEAGUILD);
                    return;
                }
                if (!g.isLeader(p.getName())) {
                    p.sendMessage(guild.INVITE_NOPERMISSION);
                    return;
                }
                User o = UserManager.getUser(args[1]);
                if (o == null) {
                    p.sendMessage("&cNie ma takiego gracza w bazie danych!");
                    return;
                }
                if (!g.isMember(o.getName())) {
                    p.sendMessage(guild.KICK_NOTINYOURGUILD);
                    return;
                }
                if (g.isLeader(o.getName())) {
                    p.sendMessage(guild.KICK_CANTKICKLEADER);
                    return;
                }
                if (p.getName().equals(o.getName())) {
                    p.sendMessage(guild.KICK_SAMEGOSIEBIE);
                    return;
                }
                if (g.isLeader(o.getName())) {
                    g.setDeputy("Brak");
                }
                if (g.isMember(p.getName())) {
                    p.teleport(LocationHolder.SPAWN);
                    g.removeMember(o.getName());
                    TagUtil.updateBoard(p);
                    user.setGuild("");
                    g.putForSave();
                    Bukkit.broadcastMessage(guild.KICK_BROADCAST.replace("{TARGET}",o.getName()).replace("{TAG}",g.getTag()));
                    break;
                }
            }
            case "itemy": {
                GuildUtil.openInv(p,p.hasPermission("spigotplugin.premium"));
                return;
            }
            case "usun": {
                Guild g = GuildManager.getGuild(p);
                if (g == null) {
                    p.sendMessage(guild.INVITE_DONTHAVEAGUILD);
                    return;
                }
                if (!g.isLeader(p.getName())) {
                    p.sendMessage(guild.INVITE_NOPERMISSION);
                    return;
                }
                if (args.length < 2) {

                    if (g.deleteCode.isEmpty()) {
                        g.deleteCode = RandomStringUtils.randomAlphabetic(4);
                        p.sendMessage("");
                        p.sendMessage(guild.DELETE_GUILD.replace("{CODE}",g.deleteCode));
                        return;
                    }
                    p.sendMessage(guild.DELETE_USAGECODE);
                } else {
                    if (g.deleteCode.isEmpty()) {
                        g.deleteCode = RandomStringUtils.randomAlphabetic(4);
                        p.sendMessage(guild.DELETE_GUILD.replace("{CODE}",g.deleteCode));
                        return;
                    }

                    if (!g.deleteCode.equals(args[1])) {
                        p.sendMessage(guild.DELETE_INVAILDCODE);
                        return;
                    }
                    GuildManager.deleteGuild(g);
                    Bukkit.broadcastMessage(guild.DELETE_BROADCAST.replace("{TAG}",g.getTag().replace("{NAME}",g.getName()).replace("{PLAYER}",p.getName())));
                    ChatUtil.sendTitleMessage(p, guild.DELETE_TITLE.replace("{TAG}",g.getTag()), guild.DELETE_SUBTITLE.replace("{PLAYER}",p.getName()), 30, 70, 40);
                    for (Player o2 : g.getOnlineMembers()) {
                        TagUtil.updateBoard(o2);
                    }
                }
                break;
            }
            case "dolacz": {
                if (args.length != 2) {
                    p.sendMessage("&6Prawidlowe uzycie: &c/g dolacz <tag>");
                    return;
                }
                Guild gl = GuildManager.getGuild(p);
                if (gl != null) {
                    p.sendMessage(guild.INVITE_DONTHAVEAGUILD);
                    return;
                }
                Guild g = GuildManager.getGuild(args[1]);
                if (g == null) {
                    p.sendMessage(guild.JOIN_GUILDNOTFOUND);
                    return;
                }
                if (!g.getInvites().contains(p.getUniqueId())) {
                    p.sendMessage(guild.JOIN_NOTINVITED.replace("{TAG}",g.getTag()));
                    return;
                }
                if (g.getMembers().size() >= g.getPlayersLimit()) {
                    p.sendMessage("&cGildia do ktorej chcesz dolaczyc posiada maksymalna liczbe czlonkow!(" + g.getPlayersLimit() + ")");
                    return;
                }
                g.addMember(p.getName());
                user.setGuild(g.getTag());
                TagUtil.updateBoard(p);
                Bukkit.broadcastMessage(guild.JOIN_BROADCAST.replace("{PLAYER}",p.getName()).replace("{TAG}",g.getTag()));
                break;
            }
            case "dom": {
                if (args.length != 1) {
                    p.sendMessage("&6Prawidlowe uzycie: &c/g dom");
                    return;
                }
                Guild g = GuildManager.getGuild(p);
                if (g == null) {
                    p.sendMessage(guild.INVITE_DONTHAVEAGUILD);
                    return;
                }
                Guild o = GuildManager.getGuild(p.getLocation());
                if (o != null && !o.isMember(p.getName())) {
                    p.sendMessage(guild.JOIN_TELEPORT);
                    return;
                }
                Teleporter.sendRequest(p, g.getHome());
                break;

            }
            case "ustawdom": {
                if (args.length != 1) {
                    p.sendMessage("&6Prawidlowe uzycie: &c/g ustawdom");
                    return;
                }
                Guild g = GuildManager.getGuild(p);
                if (g == null) {
                    p.sendMessage(guild.INVITE_DONTHAVEAGUILD);
                    return;
                }
                if (!g.isDeputy(p.getName())) {
                    p.sendMessage(guild.INVITE_NOPERMISSION);
                    return;
                }
                Guild o = GuildManager.getGuild(p.getLocation());
                if (!g.equals(o)) {
                    p.sendMessage(guild.SETHOME_WRONGTERRAIN);
                }
                g.setHome(p.getLocation());
                g.putForSave();
                p.sendMessage(guild.SETHOME_SUCCESS);
                break;
            }
            case "lider": {
                if (args.length != 2) {
                    p.sendMessage("&6Prawidlowe uzycie: &c/g lider <gracz>");
                    return;
                }
                Guild g = GuildManager.getGuild(p);
                if (g == null) {
                    p.sendMessage(guild.INVITE_DONTHAVEAGUILD);
                    return;
                }
                if (!g.isLeader(p.getName())) {
                    p.sendMessage(guild.INVITE_DONTHAVEAGUILD);
                    return;
                }
                User u = UserManager.getUser(args[1]);
                if (u == null) {
                    p.sendMessage(guild.PLAYER_USERNULL);
                    return;
                }
                if (!g.isMember(u.getName())) {
                    p.sendMessage(guild.PLAYER_DONTHAVEAGUILD);
                    return;
                }
                if(!p.getInventory().containsAtLeast(guild.COST_LEADER, guild.COST_LEADER.getAmount())){
                    p.sendMessage("&cNie Posiadasz x"+ guild.COST_LEADER);
                    return;
                }
                p.getInventory().removeItem(guild.COST_LEADER);
                g.setLeader(u.getName());
                g.putForSave();
                Bukkit.broadcastMessage(guild.LIDER_BROADCAST.replace("{TARGET}",u.getName()).replace("{TAG}",g.getTag()));
                break;
            }
            case "zastepca": {
                if (args.length != 2) {
                    p.sendMessage("&6Prawidlowe uzycie: &c/g zastepca <gracz>");
                    return;
                }
                Guild g = GuildManager.getGuild(p);
                if (g == null) {
                    p.sendMessage(guild.INVITE_DONTHAVEAGUILD);
                    return;
                }
                if (!g.isLeader(p.getName())) {
                    p.sendMessage(guild.INVITE_NOPERMISSION);
                    return;
                }
                User u = UserManager.getUser(args[1]);
                if (u == null) {
                    p.sendMessage(guild.PLAYER_USERNULL);
                    return;
                }
                if (!g.isMember(u.getName())) {
                    p.sendMessage(guild.PLAYER_DONTHAVEAGUILD);
                    return;
                }
                if (g.isLeader(u.getName())) {
                    p.sendMessage("&cNie mozesz wyrzucic zalozyciela!");
                    return;
                }
                if (g.isLeader(u.getName())) {
                    g.setDeputy("Brak");
                    Bukkit.broadcastMessage(guild.DEPUTY_BROADCAST.replace("{TARGET}",u.getName()).replace("{TAG}",g.getTag()));
                }
                if(!p.getInventory().containsAtLeast(guild.COST_DEPUTY, guild.COST_DEPUTY.getAmount())){
                    p.sendMessage("&cNie Posiadasz x"+ guild.COST_DEPUTY);
                    return;
                }
                p.getInventory().removeItem(guild.COST_DEPUTY);
                g.setDeputy(u.getName());
                Bukkit.broadcastMessage(guild.DEPUTY_CHANGE.replace("{TARGET}",u.getName()).replace("{TAG}",g.getTag()));
                break;
            }
            case "odnow": {
                if (args.length != 1) {
                    p.sendMessage("&6Prawidlowe uzycie: &c/g odnow");
                    return;
                }
                Guild g = GuildManager.getGuild(p);
                if (g == null) {
                    p.sendMessage(guild.INVITE_DONTHAVEAGUILD);
                    return;
                }
                if (!g.isLeader(p.getName())) {
                    p.sendMessage(guild.INVITE_NOPERMISSION);
                    return;
                }
                if (g.getProlong() + TimeUtil.DAY.getTime(guild.CUBOID_PROLONG_ADD) > System.currentTimeMillis() + TimeUtil.DAY.getTime(guild.CUBOID_PROLONG_MAX)) {
                    p.sendMessage(guild.RENEW_MAX);
                    return;
                }
                if(!p.getInventory().containsAtLeast(guild.COST_PROLONG, guild.COST_PROLONG.getAmount())){
                    p.sendMessage("&cNie Posiadasz x"+ guild.COST_PROLONG);
                    return;
                }
                p.getInventory().removeItem(guild.COST_PROLONG);
                g.setProlong(g.getProlong() + TimeUtil.DAY.getTime(guild.CUBOID_PROLONG_ADD));
                //p.sendMessage(guild.RENEW_SEND.replace("{RENEWADD}",guild.CUBOID_PROLONG_ADD));
                p.sendMessage("" + guild.CUBOID_PROLONG_ADD + " &6dni!");
                break;
            }
            case "opusc": {
                if (args.length != 1) {
                    p.sendMessage("&6Prawidlowe uzycie: &c/g opusc");
                    return;
                }
                Guild g = GuildManager.getGuild(p);
                if (g == null) {
                    p.sendMessage(guild.INVITE_DONTHAVEAGUILD);
                    return;
                }
                if (g.isLeader(p.getName())) {
                    p.sendMessage(guild.INVITE_NOPERMISSION);
                    return;
                }
                if (g.isLeader(p.getName())) {
                    g.setDeputy("Brak");
                }
                g.removeMember(p.getName());
                Guild o = GuildManager.getGuild(p.getLocation());
                if (o != null && !o.isMember(p.getName())) {
                    p.teleport(LocationHolder.SPAWN);
                }
                user.setGuild("");
                TagUtil.updateBoard(p);
                Bukkit.broadcastMessage(guild.LEAVE_BROADCAST.replace("{PLAYER}",p.getName()).replace("{TAG}",g.getTag()));
                break;
            }
            case "pvp": {
                Guild g = GuildManager.getGuild(p);
                if (g == null) {
                    p.sendMessage(guild.INVITE_DONTHAVEAGUILD);
                    return;
                }
                if (!g.isLeader(p.getName())) {
                    p.sendMessage(guild.INVITE_NOPERMISSION);
                    return;
                }
                if (args.length == 1) {
                    g.setPvp(!g.isPvp());
                    g.message(guild.FRIENDLYFIRE_GUILD.replace("{PLAYER}",p.getName()).replace("{GUILDSTATE}",g.isPvp() ? " &cwlaczyl" : " &awylaczyl"));
                } else {
                    if (args[1].equalsIgnoreCase("sojusz")) {
                        g.setPvpAlly(!g.isPvpAlly());
                        for (Player o2 : g.getOnlineMembers()) {
                            o2.sendMessage(guild.FRIENDLYFIRE_ALLY.replace("{PLAYER}",p.getName()).replace("{ALLYSTATE}", g.isPvpAlly() ? " &awlaczyl" : " &cwylaczyl"));
                        }
                    } else {
                        p.sendMessage("&6Prawidlowe uzycie: &c/g pvp lub /g pvp sojusz");
                    }
                }
                break;
            }
            case "panel": {
                Guild g = GuildManager.getGuild(p);
                if (g == null) {
                    p.sendMessage(guild.INVITE_DONTHAVEAGUILD);
                    return;
                }
                PanelMenu.show(p,g);
                break;
            }
            case "regeneruj":
            case "regen": {
                Guild g = GuildManager.getGuild(p);
                if (g == null) {
                    p.sendMessage(guild.INVITE_DONTHAVEAGUILD);
                    return;
                }
                if (!g.getRegen().contains("!")) {
                    p.sendMessage(guild.REGEN_BLOCKS);
                    return;
                }
                if (TNTUtil.isBetween()) {
                    p.sendMessage(guild.REGEN_TNTBETWEEN);
                    return;
                }
                if (g.getRegen().isEmpty()) {
                    p.sendMessage(guild.REGEN_ISSTARTED);
                    return;
                }
                g.setBlocksToRegen(g.getGold() * 10);
                GuildRegenerationTask.regen(g);
                break;
            }
            case "zapisz": {
                p.sendMessage("xD");
                p.sendMessage("xD");
                break;
            }
            case "wojna": {
                Guild g = GuildManager.getGuild(p);
                if (args.length != 2) {
                    p.sendMessage("&6Prawidlowe uzycie: &c/g wojna <tag>");
                    return;
                }
                if (g == null) {
                    p.sendMessage(guild.INVITE_DONTHAVEAGUILD);
                    return;
                }
                Guild gg = GuildManager.getGuild(args[1]);
                if (gg == null) {
                    p.sendMessage(guild.JOIN_GUILDNOTFOUND);
                    return;
                }
                if (g == gg) {
                    p.sendMessage(guild.WAR_MYGUILD);
                    return;
                }
                if (g.getAlly().contains(g.getTag())) {
                    p.sendMessage(guild.WAR_ALLY);
                    return;
                }
                /*if (TNTUtil.isBetween()){
                    p.sendMessage(guild.WAR_BETWEEN);
                    return;
                }*/
                boolean czydzokumamalego = true;
                for (String s : gg.getGuildWar()) {
                    String[] splitter = s.split("@");
                    if (splitter[0].contains(g.getTag())) {
                        czydzokumamalego = false;
                    }
                }
                if (!czydzokumamalego) {
                    p.sendMessage(guild.WAR_ISSET.replace("{TARGETGUILD}",gg.getTag()));
                    return;
                }
                gg.getGuildWar().add(g.getTag() + "@" + System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24));
                g.getGuildWar().add(gg.getTag() + "@" + System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24));
                Bukkit.broadcastMessage(guild.WAR_BROADCAST.replace("{TAG}",g.getTag()).replace("{TARGETGUILD}", gg.getTag()));
                ChatUtil.sendTitleMessage(p, guild.WAR_TITLE, guild.WAR_SUBTITLE.replace("{TARGETGUILD}",gg.getTag()), 30,70, 40);
                g.putForSave();
                gg.putForSave();
                break;
            }
            case "sojusz": {
                Guild g = GuildManager.getGuild(p);
                if (args.length < 3) {
                    p.sendMessage("&6Prawidlowe uzycie: &c/g sojusz <zerwij/zawrzyj> <tag>");
                    return;
                }
                if (g == null) {
                    p.sendMessage(guild.INVITE_DONTHAVEAGUILD);
                    return;
                }
                //if (!g.isLeader()) {
                    //p.sendMessage(guild.INVITE_NOPERMISSION);
                    //return;
                //}
                Guild o = GuildManager.getGuild(args[2]);
                if (o == null) {
                    p.sendMessage(guild.JOIN_GUILDNOTFOUND);
                    return;
                }
                if (g.equals(o)) {
                    p.sendMessage(guild.ALYY_MYGUILD);
                    return;
                }
                switch (args[1].toLowerCase()) {
                    case "zerwij": {
                        if (!g.getAlly().contains(o.getTag())) {
                            p.sendMessage(guild.ALYY_NOTALLY.replace("{TARGETGUILD}",o.getTag()));
                            return;
                        }
                        g.removeAlly(o.getTag());
                        o.removeAlly(g.getTag());
                        TagUtil.updateBoard(p);
                        Bukkit.broadcastMessage(guild.ALYY_BREAKBROADCAST.replace("{TAG}",g.getTag()).replace("{TARGETGUILD}", o.getTag()));
                        break;
                    }
                    case "zawrzyj": {
                        if (g.getAlly().contains(o.getTag())) {
                            p.sendMessage(guild.ALYY_ALLREADYALLY.replace("{TARGETGUILD}",o.getTag()));
                            return;
                        }
                        if (g.getAllyinvites().contains(o)) {
                            g.getAllyinvites().remove(o);
                            p.sendMessage(guild.ALYY_CHUJ.replace("{TARGETGUILD}",o.getTag()));
                            Player op = Bukkit.getPlayer(o.getLeader());
                            if (op == null) {
                                return;
                            }
                            op.sendMessage(guild.ALYY_CHUJ1.replace("{TAG}",g.getTag()));
                            return;
                        }
                        if (g.getAlly().size() >= 2) {
                            //p.sendMessage(guild.ALYY_MAX.replace("{MAXALLY}",2));
                            p.sendMessage("&&cGildia posiada maksymalna liczbe sojuszy! (" + 2 + ")");
                            return;
                        }
                        if (o.getAlly().size() >= 2) {
                            p.sendMessage("&cGildia " + o.getTag() + "posiada maksymalna liczbe sojuszy! (" + 2 + ")");//TODO
                            return;
                        }
                        Player op = Bukkit.getPlayer(o.getLeader());
                        if (op == null) {
                            p.sendMessage(guild.ALYY_LEADER.replace("{LEADER}",o.getTag()));
                            return;
                        }
                        if (o.getAllyinvites().contains(g)) {
                            if (!p.getInventory().containsAtLeast(guild.COST_ALLY, guild.COST_ALLY.getAmount())) {
                                p.sendMessage("&cNie posiadasz iemow!");//TODO dodac <
                                return;
                            }
                            p.getInventory().removeItem(guild.COST_ALLY);
                            g.addAlly(o.getTag());
                            o.addAlly(g.getTag());
                            g.getAllyinvites().remove(o);
                            o.getAllyinvites().remove(g);
                            for (Player o2 : g.getOnlineMembers()) {
                                TagUtil.updateBoard(o2);
                            }
                            Bukkit.broadcastMessage(guild.ALYY_SUCCESS.replace("{TAG}",g.getTag()).replace("{TARGETGUILD}",o.getTag()));
                            return;
                        }
                        g.getAllyinvites().add(o);
                        TagUtil.updateBoard(p);
                        p.sendMessage(guild.ALYY_INVITED.replace("{TARGETGUILD}",o.getTag()));
                        op.sendMessage(guild.ALYY_MESSAGETOMEMBERS1.replace("{TAG}",g.getTag()));
                        op.sendMessage(guild.ALYY_MESSAGETOMEMBERS2.replace("{TAG}",g.getTag()));
                        break;
                    }
                }
            }
            break;
        }
    }
    private void usage(CommandSender p) {
        p.sendMessage(guild.GUILDHELP_MESSAGE);
        p.sendMessage("&7&m-------------&r&7[  &c&lKomendy gildii  &7]&7&m-------------");
        p.sendMessage("&c/g zaloz <tag> <pelna_nazwa> &7- &6zalozenie gildii");
        p.sendMessage("&c/g dolacz <tag/nazwa> &7- &6dolaczasz do gildii");
        p.sendMessage("&c/g opusc &7- &6opuszczasz gildie");
        p.sendMessage("&c/g dom &7- &6teleportacja do gildii");
        p.sendMessage("&c/g odnow &7- &6oplaca gildie na \" + Config.CUBOID_PROLONG_ADD + \" dni");
        p.sendMessage("&c/g ustawdom &7- &6ustawia baze gildii");
        p.sendMessage("&c/g wyrzuc <nick> &7- &6wyrzuca gracza z gildii");
        p.sendMessage("&c/g zapros <nick/all> &7- &6zaprasza gracza do gildii");
        p.sendMessage("&c/g lider <nick> &7- &6przekazuje wlasciciela gildii");
        p.sendMessage("&c/g zastepca <nick> &7- &6zmienia zastepce gildii");
        p.sendMessage("&c/g wojna &7- &6wywolywanie wojen gildyjnych");
        p.sendMessage("&c/g pvp &7- &6wlacza/wylacza pvp w gildii");
        p.sendMessage("&c/g pvp sojusz &7- &6wlacza/wylacza pvp w sojuszu");
        p.sendMessage("&c/g zapisz &7- &6zapisuje gildie na event");
        p.sendMessage("&c/g lista &7- &6wyswitla wszystkie gildie na serwerze");
        p.sendMessage("&c/gildia <gildia> &7- &6informacje o gildii");
        p.sendMessage("");
        p.sendMessage("&c! &8- &6Wiadomosc do gildii");
        p.sendMessage("&c!! &8- &6Wiadomosc do sojuszy");
        p.sendMessage("&c@ &8- &6Wiadomosc o pomoc do gildii");
        p.sendMessage("&7&m-------------&r&7[  &c&lKomendy gildii  &7]&7&m-------------");
    }
}
