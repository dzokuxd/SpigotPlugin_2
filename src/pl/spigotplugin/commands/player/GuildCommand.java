package pl.spigotplugin.commands.player;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.SpigotPlugin;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.component.Teleporter;
import pl.spigotplugin.configs.statues;
import pl.spigotplugin.configs.guild;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.holder.LocationHolder;
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.menu.PanelMenu;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.objects.guild.GuildWar;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.tasks.GuildRegenerationTask;
import pl.spigotplugin.utils.*;

import javax.xml.soap.Text;
import java.util.Optional;

public class GuildCommand extends PlayerCommand {
    public GuildCommand() { super("gildie", RankType.GRACZ, "","g"); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length < 1) {
            p.sendMessage(ChatUtil.color(guild.GUILDHELP_MESSAGE));
            return;
        }
        User user = UserManager.getUser(p);
        switch (args[0].toLowerCase()){
            case "zaloz": {
                if (args.length !=3) {
                    p.sendMessage(ChatUtil.color("&fPrawidlowe uzycie: &d/g zaloz <tag> <pelna nazwa>"));
                    return;
                }
                if (!GroupUtil.have(p, RankType.ADMIN) && !statues.MANAGE_GUILDCREATE) {
                    p.sendMessage(ChatUtil.color("&cZakladanie gildii jest tymczasowo wylaczone!"));
                    return;
                }
                if (p.getWorld() != Bukkit.getWorld("world")) {
                    p.sendMessage(ChatUtil.color("&cGildie mozesz zalozyc tylko w normalnym swiecie!"));
                    return;
                }
                if (!user.getGuild().isEmpty())  {
                    p.sendMessage(ChatUtil.color(guild.CREATE_HAVEGUILD));
                    return;
                }
                String tag = args[1].toUpperCase();
                String name = args[2];
                if (tag.length() >5 || tag.length() <2 || name.length() > 32 || name.length() <4){
                    p.sendMessage(ChatUtil.color(guild.CREATE_WRONGTAGANDNAME));
                    return;
                }
                if (GuildManager.getGuild(tag) !=null) {
                    p.sendMessage(ChatUtil.color(guild.CREATE_ALLREADYEXISTSBYSHORTCUT));
                    return;
                }
                if (GuildManager.getGuild(name) !=null) {
                    p.sendMessage(ChatUtil.color(guild.CREATE_ALLREADYEXISTSBYFULLNAME));
                    return;
                }
                if (!ChatUtil.isAlphaNumeric(tag)) {
                    p.sendMessage(ChatUtil.color(guild.CREATE_SHORTCUTNOTALPHANUMERIC));
                    return;
                }
                if (!ChatUtil.isAlphaNumeric(name)) {
                    p.sendMessage(ChatUtil.color(guild.CREATE_FULLNAMENOTALPHANUMERIC));
                    return;
                }
                if (!isValidLocation(p)) {
                    return;
                }
                if (!GuildUtil.hasItems(p))
                    return;
                GuildUtil.removeItems(p);
                Guild g = GuildManager.createGuild(tag, name, p, p.getLocation().clone());
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
                    p.sendMessage(ChatUtil.color("&fPrawidlowe uzycie: &d/g zapros <gracz/*>"));
                    return;
                }
                if (g == null) {
                    p.sendMessage(ChatUtil.color(guild.PLAYER_YOUDONTHAVEAGUILD));
                    return;
                }
                if (!g.isDeputy(p.getName())){
                    p.sendMessage(ChatUtil.color(guild.PLAYER_NOPERMISSION));
                    return;
                }
                ItemStack costInvite = guild.INVITE_COST;
                if (args[1].equalsIgnoreCase("*")) {
                    int i = 0;
                    for (Player players : LocationUtil.getPlayersInRadius(p.getLocation(), 5)) {
                        if (players == p) continue;
                        ++i;
                        if (!p.getInventory().containsAtLeast(costInvite, costInvite.getAmount())) {
                            p.sendMessage(ChatUtil.color("&cNie posiadasz " + costInvite.getType() + "x" + costInvite.getAmount()));
                            return;
                        }
                        if (i == 0) {
                            p.sendMessage(ChatUtil.color(guild.INVITE_GROUPMESSAGE));
                            return;
                        }
                        if (g.getInvites().contains(players.getUniqueId())) {
                            p.sendMessage(ChatUtil.color(guild.INVITE_GROUPERROR));
                            continue;
                        }
                        p.getInventory().removeItem(costInvite);
                        g.getInvites().add(players.getUniqueId());
                        players.sendMessage(ChatUtil.color(guild.INVITE_TARGET1.replace("{TAG}",g.getTag()).replace("{PLAYER}",p.getName())));
                        players.sendMessage(ChatUtil.color(guild.INVITE_TARGET2.replace("{TAG}",g.getTag())));
                        TextComponent message = new TextComponent("&dKliknij, aby dolaczyc do gildii!");
                        TextComponent hoverText = new TextComponent("&dKliknij, aby dolaczyc do gildii!");
                        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/g dolacz "+g.getTag()));
                        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[] {hoverText}));
                        players.sendMessage(message);
                    }
                    p.sendMessage(ChatUtil.color("&aZaprosiles " + i + " graczy do gildii"));
                } else {
                    User o = UserManager.getUser(args[1]);
                    if (o == null) {
                        p.sendMessage(ChatUtil.color(guild.PLAYER_USERNULL));
                        return;
                    }
                    Player player = o.getPlayer();
                    if (player == null) {
                        p.sendMessage(ChatUtil.color(guild.PLAYER_ISOFFLINE));
                        return;
                    }
                    Guild go = GuildManager.getGuild(player);
                    if (go != null) {
                        p.sendMessage(ChatUtil.color(guild.CREATE_HAVEGUILD));
                        return;
                    }
                    if (g.getInvites().contains(player.getUniqueId())) {
                        g.getInvites().remove(player.getUniqueId());
                        player.sendMessage(ChatUtil.color(guild.INVITE_COFNIETE1.replace("{TAG}",g.getTag()).replace("{PLAYER}",p.getName())));
                        p.sendMessage(ChatUtil.color(guild.INVITE_COFNIETE2.replace("{TARGET}",o.getName())));
                        return;
                    }
                    if(!p.getInventory().containsAtLeast(costInvite, costInvite.getAmount())){
                        p.sendMessage(ChatUtil.color("&cNie posiadasz " + costInvite.getType() + "x" + costInvite.getAmount()));
                        return;
                    }
                    g.getInvites().add(player.getUniqueId());
                    p.getInventory().removeItem(costInvite);
                    p.sendMessage(ChatUtil.color("&fZaprosiles &d"+o.getName()+ "&fdo gildii"));
                    o.getPlayer().sendMessage(ChatUtil.color(guild.INVITE_TARGET1.replace("{TAG}",g.getTag()).replace("{PLAYER}",p.getName())));
                    o.getPlayer().sendMessage(ChatUtil.color(guild.INVITE_TARGET2.replace("{TAG}",g.getTag())));
                    TextComponent message = new TextComponent("&dKliknij, aby dolaczyc do gildii!");
                    TextComponent hoverText = new TextComponent("&dKliknij, aby dolaczyc do gildii!");
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/g dolacz "+g.getTag()));
                    message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[] {hoverText}));
                    o.getPlayer().sendMessage(message);
                    return;
                }
                break;
            }
            case "wyrzuc": {
                if (args.length != 2) {
                    p.sendMessage(ChatUtil.color("&fPrawidlowe uzycie: &d/g wyrzuc <gracz>"));
                    return;
                }
                Guild g = GuildManager.getGuild(p);
                if (g == null) {
                    p.sendMessage(ChatUtil.color(guild.PLAYER_YOUDONTHAVEAGUILD));
                    return;
                }
                if (!g.isLeader(p.getName())) {
                    p.sendMessage(ChatUtil.color(guild.PLAYER_NOPERMISSION));
                    return;
                }
                User o = UserManager.getUser(args[1]);
                if (o == null) {
                    p.sendMessage(ChatUtil.color(ChatUtil.color("&cNie ma takiego gracza w bazie danych!")));
                    return;
                }
                if (!g.isMember(o.getName())) {
                    p.sendMessage(ChatUtil.color(guild.KICK_NOTINYOURGUILD));
                    return;
                }
                if (g.isLeader(o.getName())) {
                    p.sendMessage(ChatUtil.color(guild.KICK_CANTKICKLEADER));
                    return;
                }
                if (p.getName().equals(o.getName())) {
                    p.sendMessage(ChatUtil.color(guild.KICK_SAMEGOSIEBIE));
                    return;
                }
                if (g.isLeader(o.getName())) {
                    g.setDeputy("Brak");
                }
                if (p.getWorld().getName().equals("end")) {
                    p.teleport(LocationHolder.SPAWN);
                    p.sendMessage(ChatUtil.color("&aZostalas przeteleportowany na spawn poniewaz byles w endzie i zostales wyrzucony z gildii"));
                    return;
                }
                Guild x = GuildManager.getGuild(p.getLocation());
                if (x != null && !x.isMember(p.getName())) {
                    p.teleport(LocationHolder.SPAWN);
                    return;
                }
                if (g.isMember(p.getName())) {
                    g.removeMember(o.getName());
                    TagUtil.updateBoard(p);
                    user.setGuild("");
                    g.putForSave();
                    Bukkit.broadcastMessage(guild.KICK_BROADCAST.replace("{TARGET}",o.getName()).replace("{TAG}",g.getTag()));
                    break;
                }
            }
            case "itemy": {
                GuildUtil.openInv(p,!GroupUtil.have(p, RankType.VIP));
                return;
            }
            case "usun": {
                Guild g = GuildManager.getGuild(p);
                if (g == null) {
                    p.sendMessage(ChatUtil.color(guild.PLAYER_YOUDONTHAVEAGUILD));
                    return;
                }
                if (!g.isLeader(p.getName())) {
                    p.sendMessage(ChatUtil.color(guild.PLAYER_NOPERMISSION));
                    return;
                }
                if (args.length < 2) {

                    if (g.deleteCode.isEmpty()) {
                        g.deleteCode = RandomStringUtils.randomAlphabetic(4);
                        p.sendMessage(ChatUtil.color(guild.DELETE_GUILD.replace("{CODE}",g.deleteCode)));
                        return;
                    }
                    p.sendMessage(ChatUtil.color(guild.DELETE_USAGECODE));
                } else {
                    if (g.deleteCode.isEmpty()) {
                        g.deleteCode = RandomStringUtils.randomAlphabetic(4);
                        p.sendMessage(ChatUtil.color(guild.DELETE_GUILD.replace("{CODE}",g.deleteCode)));
                        return;
                    }

                    if (!g.deleteCode.equals(args[1])) {
                        p.sendMessage(ChatUtil.color(guild.DELETE_INVAILDCODE));
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
                    p.sendMessage(ChatUtil.color("&fPrawidlowe uzycie: &d/g dolacz <tag>"));
                    return;
                }
                Guild gl = GuildManager.getGuild(p);
                if (gl != null) {
                    p.sendMessage(ChatUtil.color(guild.PLAYER_YOUDONTHAVEAGUILD));
                    return;
                }
                Guild g = GuildManager.getGuild(args[1]);
                if (g == null) {
                    p.sendMessage(ChatUtil.color(guild.JOIN_GUILDNOTFOUND));
                    return;
                }
                if (!g.getInvites().contains(p.getUniqueId())) {
                    p.sendMessage(ChatUtil.color(guild.JOIN_NOTINVITED.replace("{TAG}",g.getTag())));
                    return;
                }
                if (g.getMembers().size() >= g.getPlayersLimit()) {
                    p.sendMessage(ChatUtil.color("&cGildia do ktorej chcesz dolaczyc posiada maksymalna liczbe czlonkow!(" + g.getPlayersLimit() + ")"));
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
                    p.sendMessage(ChatUtil.color("&fPrawidlowe uzycie: &d/g dom"));
                    return;
                }
                Guild g = GuildManager.getGuild(p);
                if (g == null) {
                    p.sendMessage(ChatUtil.color(guild.PLAYER_YOUDONTHAVEAGUILD));
                    return;
                }
                Guild o = GuildManager.getGuild(p.getLocation());
                if (o != null && !o.isMember(p.getName())) {
                    p.sendMessage(ChatUtil.color(guild.JOIN_TELEPORT));
                    return;
                }
                Teleporter.sendRequest(p, g.getHome());
                break;

            }
            case "ustawdom": {
                if (args.length != 1) {
                    p.sendMessage(ChatUtil.color("&fPrawidlowe uzycie: &d/g ustawdom"));
                    return;
                }
                Guild g = GuildManager.getGuild(p);
                if (g == null) {
                    p.sendMessage(ChatUtil.color(guild.PLAYER_YOUDONTHAVEAGUILD));
                    return;
                }
                if (!g.isDeputy(p.getName())) {
                    p.sendMessage(ChatUtil.color(guild.PLAYER_NOPERMISSION));
                    return;
                }
                Guild o = GuildManager.getGuild(p.getLocation());
                if (!g.equals(o)) {
                    p.sendMessage(ChatUtil.color(guild.SETHOME_WRONGTERRAIN));
                }
                g.setHome(p.getLocation());
                g.putForSave();
                p.sendMessage(ChatUtil.color(guild.SETHOME_SUCCESS));
                break;
            }
            case "lider": {
                if (args.length != 2) {
                    p.sendMessage(ChatUtil.color("&fPrawidlowe uzycie: &d/g lider <gracz>"));
                    return;
                }
                Guild g = GuildManager.getGuild(p);
                if (g == null) {
                    p.sendMessage(ChatUtil.color(guild.PLAYER_YOUDONTHAVEAGUILD));
                    return;
                }
                if (!g.isLeader(p.getName())) {
                    p.sendMessage(ChatUtil.color(guild.PLAYER_NOPERMISSION));
                    return;
                }
                User u = UserManager.getUser(args[1]);
                if (u == null) {
                    p.sendMessage(ChatUtil.color(guild.PLAYER_USERNULL));
                    return;
                }
                if (!g.isMember(u.getName())) {
                    p.sendMessage(ChatUtil.color(guild.PLAYER_DONTHAVEAGUILD));
                    return;
                }
                ItemStack costLeader = guild.LIDER_COST;
                if(!p.getInventory().containsAtLeast(costLeader, costLeader.getAmount())){
                    p.sendMessage(ChatUtil.color("&cNie posiadasz " + costLeader.getType() + "x" + costLeader.getAmount()));
                    return;
                }
                p.getInventory().removeItem(guild.LIDER_COST);
                g.setLeader(u.getName());
                g.putForSave();
                Bukkit.broadcastMessage(guild.LIDER_BROADCAST.replace("{TARGET}",u.getName()).replace("{TAG}",g.getTag()));
                break;
            }
            case "zastepca": {
                if (args.length != 2) {
                    p.sendMessage(ChatUtil.color("&fPrawidlowe uzycie: &d/g zastepca <gracz>"));
                    return;
                }
                Guild g = GuildManager.getGuild(p);
                if (g == null) {
                    p.sendMessage(ChatUtil.color(guild.PLAYER_YOUDONTHAVEAGUILD));
                    return;
                }
                if (!g.isLeader(p.getName())) {
                    p.sendMessage(ChatUtil.color(guild.PLAYER_NOPERMISSION));
                    return;
                }
                User u = UserManager.getUser(args[1]);
                if (u == null) {
                    p.sendMessage(ChatUtil.color(guild.PLAYER_USERNULL));
                    return;
                }
                if (!g.isMember(u.getName())) {
                    p.sendMessage(ChatUtil.color(guild.PLAYER_DONTHAVEAGUILD));
                    return;
                }
                if (g.isLeader(u.getName())) {
                    p.sendMessage(ChatUtil.color("&cNie mozesz wyrzucic zalozyciela!"));
                    return;
                }
                if (g.isDeputy(u.getName())) {
                    g.setDeputy("Brak");
                    Bukkit.broadcastMessage(guild.DEPUTY_BROADCAST.replace("{TARGET}",u.getName()).replace("{TAG}",g.getTag()));
                    return;
                }
                ItemStack costDeputy = guild.DEPUTY_COST;
                if(!p.getInventory().containsAtLeast(costDeputy, costDeputy.getAmount())){
                    p.sendMessage(ChatUtil.color("&cNie posiadasz " + costDeputy.getType() + "x" + costDeputy.getAmount()));
                    return;
                }
                p.getInventory().removeItem(guild.DEPUTY_COST);
                g.setDeputy(u.getName());
                Bukkit.broadcastMessage(guild.DEPUTY_CHANGE.replace("{TARGET}",u.getName()).replace("{TAG}",g.getTag()));
                break;
            }
            case "odnow": {
                if (args.length != 1) {
                    p.sendMessage(ChatUtil.color("&fPrawidlowe uzycie: &d/g odnow"));
                    return;
                }
                Guild g = GuildManager.getGuild(p);
                if (g == null) {
                    p.sendMessage(ChatUtil.color(guild.PLAYER_YOUDONTHAVEAGUILD));
                    return;
                }
                if (!g.isLeader(p.getName())) {
                    p.sendMessage(ChatUtil.color(guild.PLAYER_NOPERMISSION));
                    return;
                }
                if (g.getProlong() + TimeUtil.DAY.getTime(guild.CUBOID_PROLONG_ADD) > System.currentTimeMillis() + TimeUtil.DAY.getTime(guild.CUBOID_PROLONG_MAX)) {
                    p.sendMessage(ChatUtil.color(guild.RENEW_MAX));
                    return;
                }
                ItemStack costRenew = guild.RENEW_COST;
                if(!p.getInventory().containsAtLeast(costRenew, costRenew.getAmount())){
                    p.sendMessage(ChatUtil.color("&cNie posiadasz " + costRenew.getType() + "x" + costRenew.getAmount()));
                    return;
                }
                p.getInventory().removeItem(guild.RENEW_COST);
                g.setProlong(g.getProlong() + TimeUtil.DAY.getTime(guild.CUBOID_PROLONG_ADD));
                p.sendMessage(ChatUtil.color("&fPrzedluzylesz waznosc gildii o &d" + guild.CUBOID_PROLONG_ADD + " &fdni!"));
                break;
            }
            case "opusc": {
                if (args.length != 1) {
                    p.sendMessage(ChatUtil.color("&fPrawidlowe uzycie: &d/g opusc"));
                    return;
                }
                Guild g = GuildManager.getGuild(p);
                if (g == null) {
                    p.sendMessage(ChatUtil.color(guild.PLAYER_YOUDONTHAVEAGUILD));
                    return;
                }
                if (g.isLeader(p.getName())) {
                    p.sendMessage(ChatUtil.color(guild.PLAYER_NOPERMISSION));
                    return;
                }
                if (g.isDeputy(p.getName())) {
                    g.setDeputy("Brak");
                }
                if (p.getWorld().getName().equals("end")) {
                    p.teleport(LocationHolder.SPAWN);
                    p.sendMessage(ChatUtil.color("&aZostalas przeteleportowany na spawn poniewaz byles w endzie i zostales wyrzucony z gildii"));
                    return;
                }
                g.removeMember(p.getName());
                Guild o = GuildManager.getGuild(p.getLocation());
                if (o != null && !o.isMember(p.getName())) {
                    p.teleport(LocationHolder.SPAWN);
                    return;
                }
                user.setGuild("");
                g.putForSave();
                TagUtil.updateBoard(p);
                Bukkit.broadcastMessage(guild.LEAVE_BROADCAST.replace("{PLAYER}",p.getName()).replace("{TAG}",g.getTag()));
                break;
            }
            case "pvp": {
                Guild g = GuildManager.getGuild(p);
                if (g == null) {
                    p.sendMessage(ChatUtil.color(guild.PLAYER_YOUDONTHAVEAGUILD));
                    return;
                }
                if (!g.isLeader(p.getName())) {
                    p.sendMessage(ChatUtil.color(guild.PLAYER_NOPERMISSION));
                    return;
                }
                if (args.length == 1) {
                    g.setPvp(!g.isPvp());
                    g.message(ChatUtil.color(guild.FRIENDLYFIRE_GUILD.replace("{PLAYER}",p.getName()).replace("{GUILDSTATE}",g.isPvp() ? " &cwlaczyl" : " &awylaczyl")));
                } else {
                    if (args[1].equalsIgnoreCase("sojusz")) {
                        g.setPvpAlly(!g.isPvpAlly());
                        for (Player o2 : g.getOnlineMembers()) {
                            o2.sendMessage(ChatUtil.color(guild.FRIENDLYFIRE_ALLY.replace("{PLAYER}",p.getName()).replace("{ALLYSTATE}", g.isPvpAlly() ? " &awlaczyl" : " &cwylaczyl")));
                        }
                    } else {
                        p.sendMessage(ChatUtil.color("&fPrawidlowe uzycie: &d/g pvp lub /g pvp sojusz"));
                    }
                }
                break;
            }
            case "panel": {
                Guild g = GuildManager.getGuild(p);
                if (g == null) {
                    p.sendMessage(ChatUtil.color(guild.PLAYER_YOUDONTHAVEAGUILD));
                    return;
                }
                PanelMenu.show(p,g);
                break;
            }
            case "regeneruj":
            case "regen": {
                Guild g = GuildManager.getGuild(p);
                if (g == null) {
                    p.sendMessage(ChatUtil.color(guild.PLAYER_NOPERMISSION));
                    return;
                }
                if (!g.getRegen().contains("!")) {
                    p.sendMessage(ChatUtil.color(guild.REGEN_BLOCKS));
                    return;
                }
                if (TNTUtil.isBetween()) {
                    p.sendMessage(ChatUtil.color(guild.REGEN_TNTBETWEEN));
                    return;
                }
                if (g.getRegen().isEmpty()) {
                    p.sendMessage(ChatUtil.color(guild.REGEN_ISSTARTED));
                    return;
                }
                g.setBlocksToRegen(g.getGold() * 10);
                GuildRegenerationTask.regen(g);
                break;
            }
            case "zapisz": {
                Guild g = GuildManager.getGuild(p);
                if (args.length != 1) {
                    p.sendMessage(ChatUtil.color("&fPrawidlowe uzycie: &d/g zapisz"));
                    return;
                }
                if (g == null) {
                    p.sendMessage(ChatUtil.color("&cNie posiadasz gidlii!"));
                    return;
                }
                if (!g.isLeader(p.getName())) {
                    p.sendMessage(ChatUtil.color("&cNie jestes zalozycielem gildii!"));
                    return;
                }
                if (g.isZapisana()) {
                    p.sendMessage(ChatUtil.color("&cTwoja gildia jest juz zapisana!"));
                    return;
                }
                SpigotPlugin.getMySQL().update("INSERT INTO `{P}savedGuilds` (`tag`) VALUES ('" + g.getTag() + "')");
                g.setZapisana(true);
                p.sendMessage(ChatUtil.color("&aTwoja gildia zostala zapisana"));
                return;
            }
            case "wojna": {
                Guild g = GuildManager.getGuild(p);
                if (args.length != 3) {
                    p.sendMessage(ChatUtil.color("&fPrawidlowe uzycie: &d/g wojna wypowiedz/ustawwypadowa/wypadowa <tag>"));
                    return;
                }
                if (g == null) {
                    p.sendMessage(ChatUtil.color(guild.PLAYER_YOUDONTHAVEAGUILD));
                    return;
                }
                if (!g.isLeader(p.getName())) {
                    p.sendMessage(ChatUtil.color("&cNie jestes liderem gildii!"));
                    return;
                }
                switch (args[1].toLowerCase()) {
                    case "wypowiedz":{
                        Guild o = GuildManager.getGuild(args[2]);
                        if (o == null) {
                            p.sendMessage(ChatUtil.color(guild.JOIN_GUILDNOTFOUND));
                            return;
                        }
                        if (g.getAlly().contains(o.getTag())) {
                            p.sendMessage(ChatUtil.color(guild.WAR_ALLY));
                            return;
                        }
                        /*if (TNTUtil.isBetween()){
                            p.sendMessage(ChatUtil.color(guild.WAR_BETWEEN));
                            return;
                        }*/
                        if (g.equals(o)) {
                            p.sendMessage(ChatUtil.color(guild.WAR_MYGUILD));
                            return;
                        }
                        if (g.hasWar(o.getTag())) {
                            p.sendMessage(ChatUtil.color(guild.WAR_ISSET.replace("{TARGETGUILD}",o.getTag())));
                            return;
                        }
                        g.getWars().add(new GuildWar(o.getTag(), null));
                        o.getWars().add(new GuildWar(g.getTag(), null));
                        Bukkit.broadcastMessage(ChatUtil.color(guild.WAR_BROADCAST.replace("{TAG}",g.getTag()).replace("{TARGETGUILD}", o.getTag())));
                        ChatUtil.sendTitleMessage(p, guild.WAR_TITLE, guild.WAR_SUBTITLE.replace("{TARGETGUILD}",o.getTag()), 30,70, 40);
                        o.putForSave();
                        g.putForSave();
                        break;
                    }
                    case "ustawypadowa": {
                        if (!g.isDeputy(p.getName())) {
                            p.sendMessage(ChatUtil.color(guild.PLAYER_NOPERMISSION));
                            return;
                        }
                        Guild o = GuildManager.getGuild(p.getLocation());
                        if (!g.equals(o)) {
                            p.sendMessage(ChatUtil.color(guild.SETHOME_WRONGTERRAIN));
                            return;
                        }
                        Optional<GuildWar> optional = o.get(g.getTag());

                        if (!optional.isPresent()) {
                            p.sendMessage(ChatUtil.color(guild.SETHOME_WRONGTERRAIN));
                            return;
                        }

                        g.setHome(p.getLocation());
                        g.putForSave();
                        p.sendMessage(ChatUtil.color(guild.SETHOME_SUCCESS));
                        break;
                    }
                }
                break;
            }
            case "sojusz": {
                Guild g = GuildManager.getGuild(p);
                if (args.length < 3) {
                    p.sendMessage(ChatUtil.color("&fPrawidlowe uzycie: &d/g sojusz <zerwij/zawrzyj> <tag>"));
                    return;
                }
                if (g == null) {
                    p.sendMessage(ChatUtil.color(guild.PLAYER_YOUDONTHAVEAGUILD));
                    return;
                }
                if (!g.isLeader(p.getName())) {
                    p.sendMessage(ChatUtil.color(guild.PLAYER_NOPERMISSION));
                    return;
                }
                Guild o = GuildManager.getGuild(args[2]);
                if (o == null) {
                    p.sendMessage(ChatUtil.color(guild.JOIN_GUILDNOTFOUND));
                    return;
                }
                if (g.equals(o)) {
                    p.sendMessage(ChatUtil.color(guild.ALLY_MYGUILD));
                    return;
                }
                switch (args[1].toLowerCase()) {
                    case "zerwij": {
                        if (!g.getAlly().contains(o.getTag())) {
                            p.sendMessage(ChatUtil.color(guild.ALLY_NOTALLY.replace("{TARGETGUILD}",o.getTag())));
                            return;
                        }
                        g.removeAlly(o.getTag());
                        o.removeAlly(g.getTag());
                        for (Player o2 : g.getOnlineMembers()) {
                            TagUtil.updateBoard(o2);
                        }
                        TagUtil.updateBoard(p);
                        Bukkit.broadcastMessage(ChatUtil.color(guild.ALLY_BREAKBROADCAST.replace("{TAG}",g.getTag()).replace("{TARGETGUILD}", o.getTag())));
                        break;
                    }
                    case "zawrzyj": {
                        if (g.getAlly().contains(o.getTag())) {
                            p.sendMessage(ChatUtil.color(guild.ALLY_ALLREADYALLY.replace("{TARGETGUILD}",o.getTag())));
                            return;
                        }
                        if (g.getAllyinvites().contains(o)) {
                            g.getAllyinvites().remove(o);
                            p.sendMessage(ChatUtil.color(guild.ALLY_CHUJ.replace("{TARGETGUILD}",o.getTag())));
                            Player op = Bukkit.getPlayer(o.getLeader());
                            if (op == null) {
                                return;
                            }
                            op.sendMessage(ChatUtil.color(guild.ALLY_CHUJ1.replace("{TAG}",g.getTag())));
                            return;
                        }
                        if (g.getAlly().size() >= 2) {
                            p.sendMessage(ChatUtil.color("&&fGildia posiada maksymalna liczbe sojuszy! (" + 2 + ")"));
                            return;
                        }
                        if (o.getAlly().size() >= 2) {
                            p.sendMessage(ChatUtil.color("&cGildia " + o.getTag() + "posiada maksymalna liczbe sojuszy! (" + 2 + ")"));
                            return;
                        }
                        Player op = Bukkit.getPlayer(o.getLeader());
                        if (op == null) {
                            p.sendMessage(ChatUtil.color(guild.ALLY_LEADER.replace("{LEADER}",o.getTag())));
                            return;
                        }
                        if (o.getAllyinvites().contains(g)) {
                            ItemStack costAlly = guild.ALLY_COST;
                            if(!p.getInventory().containsAtLeast(costAlly, costAlly.getAmount())){
                                p.sendMessage(ChatUtil.color("&cNie posiadasz " + costAlly.getType() + "x" + costAlly.getAmount()));
                                return;
                            }
                            p.getInventory().removeItem(guild.ALLY_COST);
                            g.addAlly(o.getTag());
                            o.addAlly(g.getTag());
                            g.getAllyinvites().remove(o);
                            o.getAllyinvites().remove(g);
                            for (Player o2 : g.getOnlineMembers()) {
                                TagUtil.updateBoard(o2);
                            }
                            Bukkit.broadcastMessage(guild.ALLY_SUCCESS.replace("{TAG}",g.getTag()).replace("{TARGETGUILD}",o.getTag()));
                            return;
                        }
                        g.getAllyinvites().add(o);
                        p.sendMessage(ChatUtil.color(guild.ALLY_INVITED.replace("{TARGETGUILD}",o.getTag())));
                        op.sendMessage(ChatUtil.color(guild.ALLY_MESSAGETOMEMBERS1.replace("{TAG}",g.getTag())));
                        op.sendMessage(ChatUtil.color(guild.ALLY_MESSAGETOMEMBERS2.replace("{TAG}",g.getTag())));
                        break;
                    }
                }
            }
            break;
        }
    }
    private static boolean isValidLocation(final Player player) {
        final Location location = player.getLocation();
        if (!checkSpawn(location)) {
            player.sendMessage(ChatUtil.color("&cJestes zbyt blisko spawnu!"));
            return false;
        }
        if (LocationUtil.getDistanceFromBorder(location) <= 50) {
            player.sendMessage(ChatUtil.color("&cJestes zbyt blisko borderu!"));
            return false;
        }
        if (checkGuild(location)) {
            player.sendMessage(ChatUtil.color("&cJestes zbyt blisko gildii!"));
            return false;
        }
        return true;
    }

    private static boolean checkGuild(final Location location) {
        for (final Guild value : GuildManager.getGuilds().values()) {
            if (value.getRegion().isInCuboidByLoc(location, 100)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkSpawn(final Location location) {
        final int x = 0;
        final int z = 0;
        return Math.abs(location.getBlockX() - x) >= 350 || Math.abs(location.getBlockZ() - z) >= 350;
    }
}
