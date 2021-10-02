package pl.spigotplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.configs.guild;
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.managers.TabManager;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.protocoltab.ProtocolTabAPI;
import pl.spigotplugin.protocoltab.manager.ProtocolTab;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TabUtil {
    public static int i = 0;

    private static void updateAsGuild(Player player, User u, PermissionUser uu) {
        ProtocolTab tablist = ProtocolTabAPI.getTablist(player);
        tablist.setSlot(1, "&7&lSTATYSTYKI             ");
        tablist.setSlot(2, "");
        tablist.setSlot(3, "&6Nick: &c" + player.getName());
        tablist.setSlot(4, "&6Grupa: &c" + Arrays.toString(uu.getGroupsNames()).replace("]", "").replace("[", ""));
        tablist.setSlot(5, "&6Punkty: &c" + u.getPoints());
        tablist.setSlot(6, "&6Zabojstwa: &c" + u.getKills());
        tablist.setSlot(7, "&6Smierci: &c" + u.getDeaths());
        tablist.setSlot(8, "&6Asysty &c" + u.getAsysty());
        tablist.setSlot(9, "xD");
        //ProtocolTabAPI.getTablist(player).setSlot(9, "&6KD: &c" + u.getKd());
        tablist.setSlot(10, "&6KillStreak: &c" + u.getKs());
        tablist.setSlot(11, "&6Max KillStreak: &c" + u.getMaxks());
        tablist.setSlot(12, "");
        tablist.setSlot(13, "&7&lINFORMACJE");
        tablist.setSlot(14, "");
        tablist.setSlot(15, "&6Incognito: &c" + (u.isIncognito() ? "&aWlaczone" : "&cWylaczone"));
        tablist.setSlot(16, "&6Godzina: &c" + DataUtil.getTime(System.currentTimeMillis()));
        tablist.setSlot(17, "&6Online: &c" + Bukkit.getOnlinePlayers().size());
        tablist.setSlot(18, "&6Ping: &c" + ((CraftPlayer) player).getHandle().ping);
        tablist.setSlot(19, "");

        tablist.setSlot(20, " ");
        tablist.setSlot(21, "&7&lTOP GRACZY");
        tablist.setSlot(22, " ");
        AtomicInteger atomicInteger = new AtomicInteger(23);
        for (int j = 0; j < 16; j++) {
            ProtocolTabAPI.getTablist(player).setSlot(atomicInteger.getAndIncrement(), TabManager.getReplacementR(j + 1));
        }
        tablist.setSlot(39, "");
        tablist.setSlot(40, "");
        tablist.setSlot(41, "&7&lTOP GILDII");
        tablist.setSlot(42, "");
        AtomicInteger atomicInteger1 = new AtomicInteger(43);
        for (int j = 0; j < 16; j++) {
           ProtocolTabAPI.getTablist(player).setSlot(atomicInteger1.getAndIncrement(), TabManager.getReplacementG(j + 1));
        }
        tablist.setSlot(59, "");
        tablist.setSlot(60, "");
        tablist.setSlot(61, "&7&lITEMY NA GILDIE");
        tablist.setSlot(62, "");
        tablist.setSlot(63, "&6"+getitems(player).get(0).getType().name()+"&7x&c"+getitems(player).get(0).getAmount());
        tablist.setSlot(64, "&6"+getitems(player).get(1).getType().name()+"&7x&c"+getitems(player).get(1).getAmount());
        tablist.setSlot(65, "&6"+getitems(player).get(2).getType().name()+"&7x&c"+getitems(player).get(2).getAmount());
        tablist.setSlot(66, "&6"+getitems(player).get(3).getType().name()+"&7x&c"+getitems(player).get(3).getAmount());
        tablist.setSlot(67, "&6"+getitems(player).get(4).getType().name()+"&7x&c"+getitems(player).get(4).getAmount());
        tablist.setSlot(68, "&6"+getitems(player).get(5).getType().name()+"&7x&c"+getitems(player).get(5).getAmount());
        tablist.setSlot(69, "&6"+getitems(player).get(6).getType().name()+"&7x&c"+getitems(player).get(6).getAmount());
        tablist.setSlot(70, "&6"+getitems(player).get(7).getType().name()+"&7x&c"+getitems(player).get(7).getAmount());
        tablist.setSlot(71, "&6"+getitems(player).get(8).getType().name()+"&7x&c"+getitems(player).get(8).getAmount());
        tablist.setSlot(72, "");
        tablist.setSlot(73, "");
        tablist.setSlot(74, "");
        tablist.setSlot(75, "");
        tablist.setSlot(76, "");
        tablist.setSlot(77, "");
        tablist.setSlot(78, "");
        tablist.setSlot(79, "");
        tablist.setSlot(80, "");
        tablist.update();
    }

    private static void updateAsPlayer(Player player,User u,Guild g,PermissionUser uu) {
        ProtocolTab tablist = ProtocolTabAPI.getTablist(player);
        tablist.setSlot(1, "&7&lSTATYSTYKI             ");
        tablist.setSlot(2, "");
        tablist.setSlot(3, "&6Nick: &c" + player.getName());
        tablist.setSlot(4, "&6Grupa: &c" + Arrays.toString(uu.getGroupsNames()).replace("]", "").replace("[", ""));
        tablist.setSlot(5, "&6Punkty: &c" + u.getPoints());
        tablist.setSlot(6, "&6Zabojstwa: &c" + u.getKills());
        tablist.setSlot(7, "&6Smierci: &c" + u.getDeaths());
        tablist.setSlot(8, "&6Asysty &c" + u.getAsysty());
        tablist.setSlot(9, "XD");
        //ProtocolTabAPI.getTablist(player).setSlot(9, "&6KD: &c" + u.getKd());
        tablist.setSlot(10, "&6KillStreak: &c" + u.getKs());
        tablist.setSlot(11, "&6Max KillStreak: &c" + u.getMaxks());
        tablist.setSlot(12, "");
        tablist.setSlot(13, "&7&lINFORMACJE");
        tablist.setSlot(14, "");
        tablist.setSlot(15, "&6Incognito: &c" + (u.isIncognito() ? "&aWlaczone" : "&cWylaczone"));
        tablist.setSlot(16, "&6Godzina: &c" + DataUtil.getTime(System.currentTimeMillis()));
        tablist.setSlot(17, "&6Online: &c" + Bukkit.getOnlinePlayers().size());
        tablist.setSlot(18, "&6Ping: &c" + ((CraftPlayer) player).getHandle().ping);
        tablist.setSlot(19, "");

        if(TabUtil.i == 0) {
            tablist.setSlot(20, " ");
            tablist.setSlot(21, "&7&lTOP GRACZY");
            tablist.setSlot(22, " ");
            AtomicInteger atomicInteger = new AtomicInteger(23);
            for (int j = 0; j < 16; j++) {
                ProtocolTabAPI.getTablist(player).setSlot(atomicInteger.getAndIncrement(), TabManager.getReplacementR(j + 1));
            }
            tablist.setSlot(39, "");
        } else {
            tablist.setSlot(20, " ");
            tablist.setSlot(21, "&7&lTOP GILDII");
            tablist.setSlot(22, " ");
            AtomicInteger atomicInteger = new AtomicInteger(23);
            for (int j = 0; j < 16; j++) {
                ProtocolTabAPI.getTablist(player).setSlot(atomicInteger.getAndIncrement(), TabManager.getReplacementG(j + 1));
            }
            tablist.setSlot(39, "");
        }
        if(TabUtil.i == 0) {
            int size = g.getRegion().getSize() * 2 + 1;
            tablist.setSlot(40, "");
            tablist.setSlot(41, "&7&lGILDIA");
            tablist.setSlot(42, "");
            tablist.setSlot(43, "&6Gilldia: &c" +g.getTag());
            tablist.setSlot(44, "&6Lider: &c" +g.getLeader());
            tablist.setSlot(45, "&6Zastepca: &c" +g.getDeputy());
            tablist.setSlot(46, "&6Zabojstwa: &c" +g.getKills());
            tablist.setSlot(47, "&6Smierci: &c" +g.getDeaths());
            tablist.setSlot(48, "&6Ranking: &c"+g.getPoints());
            //ProtocolTabAPI.getTablist(player).setSlot(48, "&6KD: &c" +g.getKd());
            tablist.setSlot(49, "&6Zycia: &c" +g.getLife());
            tablist.setSlot(50, "&6HP: &c" +g.getHp());
            tablist.setSlot(51, "&6Cuboid: &c"+size+ "&7x&c" +size);
            tablist.setSlot(52, "&6Online: &c" +g.getOnlineMembers().size() + "&7/&c"+g.getMembers().size()+"&7(&c" +g.getPlayersLimit() +"&7)");
            tablist.setSlot(53, "&6Wygasa: &c" +(g.isExits() ? "&6za: &c" + DataUtil.secondsToString(g.getProlong()) : " &cWygasla"));
            tablist.setSlot(54, "");
            tablist.setSlot(55, "");
            tablist.setSlot(56, "");
            tablist.setSlot(57, "");
            tablist.setSlot(58, "");
            tablist.setSlot(59, "");
        } else {
            tablist.setSlot(40, "");
            tablist.setSlot(41, "&4&lWOJNY");
            tablist.setSlot(42, "");
            tablist.setSlot(43, "&c"+g.getTag()+" &6vs &c"+g.getwojnatags());
            //ProtocolTabAPI.getTablist(player).setSlot(43, "&6Wojna z: &c"+ (g.getWojnakurwaguildsy().isEmpty() ? "BRAK" : g.getwojnatags()));
            tablist.setSlot(44, "&6Zabojstwa: &c");
            tablist.setSlot(45, "&6Smierci: &c");
            tablist.setSlot(46, "");
            tablist.setSlot(47, "&6Pozostaly czas: &c");
            tablist.setSlot(48, "");
            tablist.setSlot(49, "");
            tablist.setSlot(50, "");
            tablist.setSlot(51, "");
            tablist.setSlot(52, "");
            tablist.setSlot(53, "");
            tablist.setSlot(54, "xD");
            //ProtocolTabAPI.getTablist(player).setSlot(54, "&6Wojna z: &c"+ (g.getWojnakurwaguildsy().isEmpty() ? "BRAK" : g.getwojnatags()));
            tablist.setSlot(55, "&6Zabojstwa: &c");
            tablist.setSlot(56, "&6Smierci: &c");
            tablist.setSlot(57, "");
            tablist.setSlot(58, "&6Pozostaly czas: &c");
            tablist.setSlot(59, "");
        }
        tablist.setSlot(60, "");
        tablist.setSlot(61, "&7&lOnline");
        tablist.setSlot(62, "");

        List<String> names = g.getOnlineMembersNames();
        AtomicInteger atomicInteger12 = new AtomicInteger(63);
        for (int i = 0; i < 16; i++) {
            tablist.setSlot(atomicInteger12.getAndIncrement(), TabManager.getmember(i + 1, names));
        }

        tablist.setSlot(79, "");
        tablist.setSlot(80, "");
        tablist.update();
    }

    public static void update(Player player) {
        ProtocolTabAPI.getTablist(player).setHeader("&7&lEasyAge.pl");
        ProtocolTabAPI.getTablist(player).setFooter("&7&lSerwer od &c&lgraczy &7&ldla &c&lgraczy!");
        User u = UserManager.getUser(player);
        PermissionUser uu = PermissionsEx.getUser(player);
        Guild g = GuildManager.getGuild(player);

        if(g == null) {
            updateAsGuild(player,u,uu);
        } else {
            updateAsPlayer(player,u,g,uu);
        }
    }

    private static List<ItemStack> getitems(Player p){
        List<ItemStack> newitems = new LinkedList<>();

        for (ItemStack item : guild.CREATE_COST) {
            int amount = item.getAmount();

            ItemStack clonedItem = item.clone();
            clonedItem.setAmount((int)(p.hasPermission("spigotplugin.premium") ? amount * .5 : amount));

            newitems.add(clonedItem);
        }

        return newitems;
    }
}