package pl.spigotplugin.commands.player;

import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.component.Teleporter;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.configs.GlobalMessage;
import pl.spigotplugin.configs.GuildConfig;
import pl.spigotplugin.holder.LocationHolder;
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.*;

public class GuildCommand extends PlayerCommand {
    public GuildCommand() { super("gildie", "gildie", "","g"); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length < 1) {
            GlobalMessage.usage(p, getUsage());
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
                    p.sendMessage("&cPosiadasz juz gildie!");
                    return;
                }
                String tag = args[1].toUpperCase();
                String name = args[2];
                if (tag.length() >5 || tag.length() <2 || name.length() > 32 || name.length() <4){
                    p.sendMessage("&cTag gildi musi zawierac 2-5 zankow, nawzwa 4-32 znakow");
                    return;
                }
                if (GuildManager.getGuild(tag) !=null) {
                    p.sendMessage("&cIstenieje juz gildia o takim tagu!");
                    return;
                }
                if (GuildManager.getGuild(name) !=null) {
                    p.sendMessage("&cIstnieje juz gildia o takiej nazwie");
                    return;
                }
                if (!ChatUtil.isAlphaNumeric(tag)) {
                    p.sendMessage("&cTag nie moze byc alfanumeryczny");
                    return;
                }
                if (!ChatUtil.isAlphaNumeric(name)) {
                    p.sendMessage("&cNazwa nie moze byc alfanumeryczna");
                    return;
                }
                if (!GuildManager.canCreateGuildBySpawn(p.getLocation())) {
                    p.sendMessage("&cGildie mozna zakladac 250 kratek od spawnu!");
                    return;
                }
                /*if (!GuildManager.canCreateGuildByGuild(p.getLocation())) {
                    p.sendMessage("&cW poblizu znajduje sie gildia!");
                    return;//TODO dodaj
                }*/

                if (!GuildUtil.hasItems(p))
                    return;
                GuildUtil.removeItems(p);
                Guild g = GuildManager.createGuild(
                        tag,
                        name,
                        p,
                        p.getLocation().clone());
                user.setGuild(g.getTag());
                Bukkit.broadcastMessage("&6Gildia &7[&c"+ g.getTag() + "&7] &6- &7[&c" + g.getName() +"&7] &6zostala zalozona przez &c"+p.getName());
                ChatUtil.sendTitleMessage(p, "&aZalozyles gildie " +g.getTag(), "&aGratulacje "+p.getName(), 30, 70, 40);
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
                    p.sendMessage("&cNie posiadasz gildii!");
                    return;
                }
                if (!g.isDeputy(p.getName())){
                    p.sendMessage("&cNie jestes zastepca gildii!");
                    return;
                }
                if (args[1].equalsIgnoreCase("*")) {
                    int i = 0;
                    for (Player players : LocationUtil.getPlayersInRadius(p.getLocation(), 5)) {
                        if (players == p) continue;
                        ++i;
                        if (!p.getInventory().containsAtLeast(GuildConfig.COST_INVITE, GuildConfig.COST_INVITE.getAmount())) {
                            p.sendMessage("&cNie posiadasz x" + GuildConfig.COST_INVITE);
                            return;
                        }
                        if (i == 0) {
                            p.sendMessage("&cBrak ludzi dookola ciebie w promieniu 5 kratek");
                            return;
                        }
                        if (g.getInvites().contains(players.getUniqueId())) {
                            p.sendMessage("&cJedna osoba posiada juz zaproszenie");
                            continue;
                        }
                        p.getInventory().removeItem(GuildConfig.COST_INVITE);
                        g.getInvites().add(players.getUniqueId());
                        players.sendMessage("&aZostales zaproszony do gildii " + g.getTag() + " przez " + p.getName() + "!");
                        players.sendMessage("&aWpisz /g dolacz " + g.getTag() + ", aby dolaczyc do gildii.");
                    }
                    p.sendMessage("&aZaprosiles " + i + " graczy do gildii");
                } else {
                    User o = UserManager.getUser(args[1]);
                    if (o == null) {
                        p.sendMessage("&cNie ma takiego gracza w bazie danych!");
                        return;
                    }
                    Player player = o.getPlayer();
                    if (player == null) {
                        p.sendMessage("&cGracz jest offline!");
                        return;
                    }
                    Guild go = GuildManager.getGuild(player);
                    if (go != null) {
                        p.sendMessage("&cGracz posiada juz gildie!");
                        return;
                    }
                    if(!p.getInventory().containsAtLeast(GuildConfig.COST_INVITE,GuildConfig.COST_INVITE.getAmount())){
                        p.sendMessage("&cNie posiadasz x"+GuildConfig.COST_INVITE);
                        return;
                    }
                    if (g.getInvites().contains(player.getUniqueId())) {
                        g.getInvites().remove(player.getUniqueId());
                        player.sendMessage("&6Zaproszenie do gildii &c" + g.getTag() + " &6zostalo cofniete przez &c" + p.getName() + "&7!");
                        p.sendMessage("&6Cofnales zaproszenie do gildii dla gracza &c" + o.getName() + "&7!");
                        return;
                    }
                    g.getInvites().add(player.getUniqueId());
                    p.getInventory().removeItem(GuildConfig.COST_INVITE);
                    player.sendMessage("&6Zostales zaproszony do gildii &c" + g.getTag() + " &6przez &c" + p.getName() + "&7!");
                    p.sendMessage("&6Wpisz &c/g dolacz " + g.getTag() + "&6, aby dolaczyc do gildii.");
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
                    p.sendMessage("&cNie posiadasz gildii!");
                    return;
                }
                if (!g.isLeader(p.getName())) {
                    p.sendMessage("&cNie jestes liderem gildii!");
                    return;
                }
                User o = UserManager.getUser(args[1]);
                if (o == null) {
                    p.sendMessage("&cNie ma takiego gracza w bazie danych!");
                    return;
                }
                if (!g.isMember(o.getName())) {
                    p.sendMessage("&cGracz nie jest w twojej gildii!");
                    return;
                }
                if (g.isLeader(o.getName())) {
                    p.sendMessage("&cNie mozesz wyrzuci zalozyciela");
                    return;
                }
                if (p.getName().equals(o.getName())) {
                    p.sendMessage("&cNie mozesz wyrzucic samego siebie!");
                    return;
                }
                if (g.isLeader(o.getName())) {
                    g.setDeputy("Brak");
                }
                Guild w = GuildManager.getGuild(p.getLocation());
                if (w != null && w.isMember(p.getName())) {
                    p.teleport(LocationHolder.SPAWN);
                }
                g.removeMember(o.getName());
                user.setGuild("");
                // update taga
                Bukkit.broadcastMessage("&6Gracz &c" + o.getName() + " &6zostal wyrzucony z gildii &7[&c" + g.getTag() + "&7]");
                TagUtil.updateBoard(p);
                break;
            }
            case "itemy": {
                GuildUtil.openInv(p,p.hasPermission("spigotplugin.premium"));
                return;
            }
            case "usun": {
                Guild g = GuildManager.getGuild(p);
                if (g == null) {
                    p.sendMessage("&cNie posiadasz gildii!");
                    return;
                }
                if (!g.isLeader(p.getName())) {
                    p.sendMessage("&cNie jestes zalozycielem gildii!");
                    return;
                }
                if (args.length < 2) {

                    if (g.deleteCode.isEmpty()) {
                        g.deleteCode = RandomStringUtils.randomAlphabetic(4);
                        p.sendMessage("&6Potwierdz usuniecie gildii: &c/g usun <" + g.deleteCode+">");
                        return;
                    }
                    p.sendMessage("&6Prawidlowe uzycie: &c/g usun <kod>");
                } else {
                    if (g.deleteCode.isEmpty()) {
                        g.deleteCode = RandomStringUtils.randomAlphabetic(4);
                        p.sendMessage("&6Potwierdz usuniecie gildii: &c/g usun <" + g.deleteCode+">");
                        return;
                    }

                    if (!g.deleteCode.equals(args[1])) {
                        p.sendMessage("&cPodales zly kod");
                        return;
                    }
                    String tag = g.getTag();
                    String name = g.getName();
                    GuildManager.deleteGuild(g);
                    Bukkit.broadcastMessage("&6Gildia &7[&c"+ tag + "&7] &6- &7[&c" + name +"&7] &6zostala usunieta przez &c"+p.getName());
                    ChatUtil.sendTitleMessage(p, "&cUsunales gildia: " +tag, "&c" + "Gratulacje "+p.getName(), 30, 70, 40);
                    TagUtil.updateBoard(p);
                }
                break;
            }
            case "dolacz": {
                if (args.length != 2) {
                    p.sendMessage("&6Prawidlowe uzycie: &c/g dolacz <tag>");
                    return;
                }
                Guild guild = GuildManager.getGuild(p);
                if (guild != null) {
                    p.sendMessage("&cPosiadasz juz gildie!");
                    return;
                }
                Guild g = GuildManager.getGuild(args[1]);
                if (g == null) {
                    p.sendMessage("&cGildia o takim tagu nie istnieje!");
                    return;
                }
                if (!g.getInvites().contains(p.getUniqueId())) {
                    p.sendMessage("&6Nie posiadasz zaproszenia do gildii &c" + g.getTag());
                    return;
                }
                if (g.getMembers().size() >= g.getPlayersLimit()) {
                    p.sendMessage("&4Blad: &cGildia do ktorej chcesz dolaczyc posiada maksymalna liczbe czlonkow!(" + g.getPlayersLimit() + ")");
                    return;
                }
                g.addMember(p.getName());
                user.setGuild(g.getTag());
                //TagUtil.updateBoard(p);
                Bukkit.broadcastMessage("&6Gracz &c" + p.getName() + " &6dolaczyl do gildii &7[&c" + g.getTag() + "&7]");
                TagUtil.updateBoard(p);
                break;
            }
            case "dom": {
                if (args.length != 1) {
                    p.sendMessage("&6Prawidlowe uzycie: &c/g dom");
                    return;
                }
                Guild g = GuildManager.getGuild(p);
                if (g == null) {
                    p.sendMessage("&cNie posiadasz gildii!");
                    return;
                }
                Guild o = GuildManager.getGuild(p.getLocation());
                if (o != null && !o.isMember(p.getName())) {
                    p.sendMessage("&cNie mozesz teleportowac sie na terenie wrogiej gildii!");
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
                    p.sendMessage("&cNie posiadasz gildii!");
                    return;
                }
                if (!g.isDeputy(p.getName())) {
                    p.sendMessage("&cNie jestes liderem gildii!");
                    return;
                }
                Guild o = GuildManager.getGuild(p.getLocation());
                if (!g.equals(o)) {
                    p.sendMessage("&cBaze gildii mozesz ustawic tylko na terenie gildii!");
                }
                g.setHome(p.getLocation());
                g.putForSave();
                p.sendMessage("&aUstawiles baze gildii!");
                break;
            }
            case "lider": {
                if (args.length != 2) {
                    p.sendMessage("&6Prawidlowe uzycie: &c/g lider <gracz>");
                    return;
                }
                Guild g = GuildManager.getGuild(p);
                if (g == null) {
                    p.sendMessage("&cNie posiadasz gildii!");
                    return;
                }
                if (!g.isLeader(p.getName())) {
                    p.sendMessage("&cNie jestes zalozycielem gildii!");
                    return;
                }
                User u = UserManager.getUser(args[1]);
                if (u == null) {
                    p.sendMessage("&cNie ma takiego gracza w bazie danych!");
                    return;
                }
                if (!g.isMember(u.getName())) {
                    p.sendMessage("&cGracz nie jest w twojej gildii!");
                    return;
                }
                if(!p.getInventory().containsAtLeast(GuildConfig.COST_LEADER,GuildConfig.COST_LEADER.getAmount())){
                    p.sendMessage("&4Blad: &cNie Posiadasz x"+GuildConfig.COST_LEADER);
                    return;
                }
                p.getInventory().removeItem(GuildConfig.COST_LEADER);
                g.setLeader(u.getName());
                Bukkit.broadcastMessage("&6Gracz &c" + u.getName() + " &6zostal nowym liderem gildii &7[&c" + g.getTag() + "&7]");
                break;
            }
            case "zastepca": {
                if (args.length != 2) {
                    p.sendMessage("&6Prawidlowe uzycie: &c/g zastepca <gracz>");
                    return;
                }
                Guild g = GuildManager.getGuild(p);
                if (g == null) {
                    p.sendMessage("&cNie posiadasz gildii!");
                    return;
                }
                if (!g.isLeader(p.getName())) {
                    p.sendMessage("&cNie jestes liderem gildii");
                    return;
                }
                User u = UserManager.getUser(args[1]);
                if (u == null) {
                    p.sendMessage("&cNie ma takiego gracza w bazie danych!");
                    return;
                }
                if (!g.isMember(u.getName())) {
                    p.sendMessage("&cGracz nie jest w twojej gildii!");
                    return;
                }
                if (g.isLeader(u.getName())) {
                    p.sendMessage("&cNie mozesz wyrzucic zalozyciela!");
                    return;
                }
                if (g.isLeader(u.getName())) {
                    g.setDeputy("Brak");
                    p.sendMessage("");
                    Bukkit.broadcastMessage("&6Gracz &c" + u.getName() + " &6nie jest juz zastepca gildii &7[&c" + g.getTag() + "&7]");
                }
                if(!p.getInventory().containsAtLeast(GuildConfig.COST_DEPUTY,GuildConfig.COST_DEPUTY.getAmount())){
                    p.sendMessage("&4Blad: &cNie Posiadasz x"+GuildConfig.COST_DEPUTY);
                    return;
                }
                p.getInventory().removeItem(GuildConfig.COST_DEPUTY);
                g.setDeputy(u.getName());
                Bukkit.broadcastMessage("&6Gracz &c" + u.getName() + " &6zostal nowym zastepca gildii &7[&c" + g.getTag() + "&7]");
                break;
            }
            case "odnow": {
                if (args.length != 1) {
                    p.sendMessage("&6Prawidlowe uzycie: &c/g odnow");
                    return;
                }
                Guild g = GuildManager.getGuild(p);
                if (g == null) {
                    p.sendMessage("&4Blad: &cNie posiadasz gildii!");
                    return;
                }
                if (!g.isLeader(p.getName())) {
                    p.sendMessage("&4Blad: &cNie jestes zastepca gildii!");
                    return;
                }
                if (g.getProlong() + TimeUtil.DAY.getTime(GuildConfig.CUBOID_PROLONG_ADD) > System.currentTimeMillis() + TimeUtil.DAY.getTime(GuildConfig.CUBOID_PROLONG_MAX)) {
                    p.sendMessage("&4Blad: &cGildia jest przedluzona na maksymalny okres");
                    return;
                }
                if(!p.getInventory().containsAtLeast(GuildConfig.COST_PROLONG,GuildConfig.COST_PROLONG.getAmount())){
                    p.sendMessage("&4Blad: &cNie Posiadasz x"+GuildConfig.COST_PROLONG);
                    return;
                }
                p.getInventory().removeItem(GuildConfig.COST_PROLONG);
                g.setProlong(g.getProlong() + TimeUtil.DAY.getTime(GuildConfig.CUBOID_PROLONG_ADD));
                p.sendMessage("&6Przedluzylesz waznosc gildii o &c" + GuildConfig.CUBOID_PROLONG_ADD + " &6dni!");
                break;
            }
            case "opusc": {
                if (args.length != 1) {
                    p.sendMessage("&6Prawidlowe uzycie: &c/g opusc");
                    return;
                }
                Guild g = GuildManager.getGuild(p);
                if (g == null) {
                    p.sendMessage("&cNie posiadasz gildii!");
                    return;
                }
                if (g.isLeader(p.getName())) {
                    p.sendMessage("&cJestes zalozycielem gildii!");
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
                Bukkit.broadcastMessage("&6Gracz &c" + p.getName() + " &6opuscil gildie &7[&c" + g.getTag() + "&7]");
                
                break;
            }
            case "pvp": {
                Guild g = GuildManager.getGuild(p);
                if (g == null) {
                    p.sendMessage("&4Blad: &cNie posiadasz gildii!");
                }
                if (!g.isLeader(p.getName())) {
                    p.sendMessage("&4Blad: &cNie jestes zastepca gildii!");
                }
                if (args.length == 1) {
                    g.setPvp(!g.isPvp());
                    for (Player o : g.getOnlineMembers()) {
                        o.sendMessage("&6Gracz &c" + p.getName() + (g.isPvp() ? " &cwlaczyl" : " &awylaczyl") + " &6ogien sojuszniczy w twojej gildii!");
                    }
                } else {
                    if (args[1].equalsIgnoreCase("sojusz")) {
                        g.setPvpAlly(!g.isPvpAlly());
                        for (Player o2 : g.getOnlineMembers()) {
                            o2.sendMessage("&6Gracz &c" + p.getName() + (g.isPvpAlly() ? " &awlaczyl" : " &cwylaczyl") + " &6ogien sojuszniczy w sojuszu!");
                        }
                    } else {
                        p.sendMessage("&6Prawidlowe uzycie: &c/g pvp lub /g pvp sojusz");
                    }
                }
                break;
            }
            case "panel": {

            }
            case "regeneruj": {

            }
            case "zapisz": {

            }
            case "lista": {
                if (GuildManager.getGuilds().size() == 0) {
                    p.sendMessage("&cNa serwerze nie ma zadnej gildii!");
                    return;
                }
                p.sendMessage("&8&m--------------&r  &6Lista Gildii &8(&c" + GuildManager.getGuilds().size() + "&8) &8&m--------------");
                for (Guild g : GuildManager.getGuilds().values()) {
                    //p.sendMessage("&7[&c" + g.getTag() + "&7] &c" + g.getName() + " &7- &c" + g.getPoints());
                    return;//TODO xD
                }
                break;
            }
            case "wojny": {

            }
            case "sojusz": {

            }
        }
    }
}
