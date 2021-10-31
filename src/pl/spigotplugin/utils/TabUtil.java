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
import pl.spigotplugin.objects.guild.GuildWar;
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
        tablist.setSlot(3, "&fNick: &d" + player.getName());
        tablist.setSlot(4, "&fGrupa: &d" + Arrays.toString(uu.getGroupsNames()).replace("]", "").replace("[", ""));
        tablist.setSlot(5, "&fPunkty: &d" + u.getPoints());
        tablist.setSlot(6, "&fZabojstwa: &d" + u.getKills());
        tablist.setSlot(7, "&fSmierci: &d" + u.getDeaths());
        tablist.setSlot(8, "&fAsysty &d" + u.getAsysty());
        tablist.setSlot(9, "&fKD: &d" + u.getKDR());
        tablist.setSlot(10, "&fKillStreak: &d" + u.getKs());
        tablist.setSlot(11, "&fMax KillStreak: &d" + u.getMaxks());
        tablist.setSlot(12, "");
        tablist.setSlot(13, "&7&lINFORMACJE");
        tablist.setSlot(14, "");
        tablist.setSlot(15, "&fIncognito: &d" + (u.isIncognito() ? "&aWlaczone" : "&cWylaczone"));
        tablist.setSlot(16, "&fGodzina: &d" + DataUtil.getTime(System.currentTimeMillis()));
        tablist.setSlot(17, "&fOnline: &d" + Bukkit.getOnlinePlayers().size());
        tablist.setSlot(18, "&fPing: &d" + ((CraftPlayer) player).getHandle().ping);
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
        tablist.setSlot(63, "&f"+getitems(player).get(0).getType().name()+"&7x&d"+getitems(player).get(0).getAmount());
        tablist.setSlot(64, "&f"+getitems(player).get(1).getType().name()+"&7x&d"+getitems(player).get(1).getAmount());
        tablist.setSlot(65, "&f"+getitems(player).get(2).getType().name()+"&7x&d"+getitems(player).get(2).getAmount());
        tablist.setSlot(66, "&f"+getitems(player).get(3).getType().name()+"&7x&d"+getitems(player).get(3).getAmount());
        tablist.setSlot(67, "&f"+getitems(player).get(4).getType().name()+"&7x&d"+getitems(player).get(4).getAmount());
        tablist.setSlot(68, "&f"+getitems(player).get(5).getType().name()+"&7x&d"+getitems(player).get(5).getAmount());
        tablist.setSlot(69, "&f"+getitems(player).get(6).getType().name()+"&7x&d"+getitems(player).get(6).getAmount());
        tablist.setSlot(70, "&f"+getitems(player).get(7).getType().name()+"&7x&d"+getitems(player).get(7).getAmount());
        tablist.setSlot(71, "&f"+getitems(player).get(8).getType().name()+"&7x&d"+getitems(player).get(8).getAmount());
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
        tablist.setSlot(3, "&fNick: &d" + player.getName());
        tablist.setSlot(4, "&fGrupa: &d" + Arrays.toString(uu.getGroupsNames()).replace("]", "").replace("[", ""));
        tablist.setSlot(5, "&fPunkty: &d" + u.getPoints());
        tablist.setSlot(6, "&fZabojstwa: &d" + u.getKills());
        tablist.setSlot(7, "&fSmierci: &d" + u.getDeaths());
        tablist.setSlot(8, "&fAsysty &d" + u.getAsysty());
        tablist.setSlot(9, "&fKD: &d" + u.getKDR());
        tablist.setSlot(10, "&fKillStreak: &d" + u.getKs());
        tablist.setSlot(11, "&fMax KillStreak: &d" + u.getMaxks());
        tablist.setSlot(12, "");
        tablist.setSlot(13, "&7&lINFORMACJE");
        tablist.setSlot(14, "");
        tablist.setSlot(15, "&fIncognito: &d" + (u.isIncognito() ? "&aWlaczone" : "&cWylaczone"));
        tablist.setSlot(16, "&fPing: &d"+ ((CraftPlayer) player).getHandle().ping);
        tablist.setSlot(17, "");
        tablist.setSlot(18, "");
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
            tablist.setSlot(43, "&fGilldia: &d" +g.getTag());
            tablist.setSlot(44, "&fLider: &d" +g.getLeader());
            tablist.setSlot(45, "&fZastepca: &d" +g.getDeputy());
            tablist.setSlot(46, "&fZabojstwa: &d" +g.getKills());
            tablist.setSlot(47, "&fSmierci: &d" +g.getDeaths());
            tablist.setSlot(48, "&fRanking: &d"+g.getPoints());
            tablist.setSlot(49, "&fZycia: &d" +g.getLife());
            tablist.setSlot(50, "&fHP: &d" +g.getHp());
            tablist.setSlot(51, "&fCuboid: &d"+size+ "&7x&d" +size);
            tablist.setSlot(52, "&fOnline: &d" +g.getOnlineMembers().size() + "&7/&d"+g.getMembers().size()+"&7(&d" +g.getPlayersLimit() +"&7)");
            tablist.setSlot(53, "&fWygasa: &d" +(g.isExits() ? "&fza: &d" + DataUtil.secondsToString(g.getProlong()) : " &dWygasla"));
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
            tablist.setSlot(43, "&d"+g.getTag()+" &fvs &d"+g.hasWar(g.getTag()));
            tablist.setSlot(44, "&fZabojstwa: &d");
            tablist.setSlot(45, "&fSmierci: &d");
            tablist.setSlot(46, "");
            tablist.setSlot(47, "&fPozostaly czas: &d");
            tablist.setSlot(48, "");
            tablist.setSlot(49, "");
            tablist.setSlot(50, "");
            tablist.setSlot(51, "");
            tablist.setSlot(52, "");
            tablist.setSlot(53, "");
            tablist.setSlot(54, "");
            tablist.setSlot(55, "&fZabojstwa: &d");
            tablist.setSlot(56, "&fSmierci: &d");
            tablist.setSlot(57, "");
            tablist.setSlot(58, "&fPozostaly czas: &d");
            tablist.setSlot(59, "");
            tablist.setSlot(60, "");
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
        ProtocolTabAPI.getTablist(player).setHeader("&7&l----------( EasyAge.pl )----------");
        ProtocolTabAPI.getTablist(player).setFooter("&7&lGodzina:" + DataUtil.getTime(System.currentTimeMillis()));
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