package pl.spigotplugin.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.DataUtil;
import pl.spigotplugin.utils.ItemBuilder;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.Arrays;

public class ProfilMenu {
    public static void show(Player p) {
        Inventory inventory = Bukkit.createInventory(null,9, ChatUtil.color("&7&lProfil: "));
        ItemBuilder air = new ItemBuilder(Material.getMaterial(160), 1, (short) 15).setTitle(ChatUtil.color("&8\u2022"));
        for (int j = 0; j < 9; j++) {
            inventory.setItem(j, air.build());
        }
        User u = UserManager.getUser(p);
        PermissionUser uu = PermissionsEx.getUser(u.getName());
        String tag = "Brak";
        Guild g = GuildManager.getGuild(u.getName());
        if (g != null) {
            tag = g.getTag();
        }
        ItemBuilder stast = new ItemBuilder(Material.DIAMOND_SWORD).setTitle(ChatUtil.color("&7&lOgolne: "))
                .addLore(ChatUtil.color("&6Ranking: &c")+u.getPoints())
                .addLore(ChatUtil.color("&6Zabojstwa: &c")+u.getKills())
                .addLore(ChatUtil.color("&6Smierci: &c")+u.getDeaths())
                .addLore(ChatUtil.color("&6Asysty: &c")+u.getAsysty())
                .addLore(ChatUtil.color("&6KD: &c")+u.getKDR())
                .addLore(ChatUtil.color("&6Coinsy: &c")+u.getCoins())
                .addLore(ChatUtil.color("&6Gildia: &c")+tag)
                .addLore(ChatUtil.color("&6Ranga: &c")+ Arrays.toString(uu.getGroupNames()))
                .addLore(ChatUtil.color("&aKliknij, aby zresetowac!"));
        ItemBuilder schowek = new ItemBuilder(Material.PAPER).setTitle(ChatUtil.color("&7&lSchowek:"))
                .addLore(ChatUtil.color("&6Koxy: &c")+u.getkoxy())
                .addLore(ChatUtil.color("&6Refile: &c")+u.getRefile())
                .addLore(ChatUtil.color("&6Perly: &c")+u.getPerly())
                .addLore(ChatUtil.color("&6Strzaly: &c")+u.getStrzaly())
                .addLore(ChatUtil.color("&aKliknij, aby zresetowac!"));
        ItemBuilder turbodrop = new ItemBuilder(Material.DIAMOND_PICKAXE).setTitle(ChatUtil.color("&7&lTurbodrop"))
                .addLore("")
                .addLore("&6Gracz: &c"+u.getName())
                .addLore("&6Turbo: &c" + (u.getTurboDrop() > System.currentTimeMillis() ? "&a" + DataUtil.secondsToString(u.getTurboDrop()) : "&cBrak"))
                .addLore("")
                .addLore(ChatUtil.color("&aKliknij, aby zresetowac!"));
        ItemBuilder gildia;
        if(g!=null){
            int size = g.getRegion().getSize() * 2 + 1;
            gildia = new ItemBuilder(Material.BOOK_AND_QUILL).setTitle(ChatUtil.color("&7&lGildia:"))
                    .addLore(ChatUtil.color("&6Tag: &c")+g.getTag())
                    .addLore(ChatUtil.color("&6Nazwa: &c")+g.getName())
                    .addLore(ChatUtil.color("&6Zalozyciel: &c")+g.getLeader())
                    .addLore(ChatUtil.color("&6Zastepca: &c")+g.getLeader())
                    .addLore(ChatUtil.color("&6Ranking: &c")+g.getPoints())
                    .addLore(ChatUtil.color("&6Zabojstwa: &c")+g.getKills())
                    .addLore(ChatUtil.color("&6Smierci: &c")+g.getDeaths())
                    .addLore(ChatUtil.color("&6Teren: &c")+size+ "\uFFFD7x&c" +size)
                    .addLore(ChatUtil.color("&6Zycia: &c")+g.getLife())
                    .addLore(ChatUtil.color("&6HP: &c")+g.getHp());
        }else{
            gildia = new ItemBuilder(Material.BOOK_AND_QUILL).setTitle(ChatUtil.color("&7&lGildia:"))
                    .addLore(ChatUtil.color("&6Tag: &cbrak"))
                    .addLore(ChatUtil.color("&6Nazwa: &cbrak"))
                    .addLore(ChatUtil.color("&6Zalozyciel: &cbrak"))
                    .addLore(ChatUtil.color("&6Zastepca: &cbrak"))
                    .addLore(ChatUtil.color("&6Ranking: &cbrak"))
                    .addLore(ChatUtil.color("&6Zabojstwa: &cbrak"))
                    .addLore(ChatUtil.color("&6Smierci: &cbrak"))
                    .addLore(ChatUtil.color("&6KD: &cbrak"))
                    .addLore(ChatUtil.color("&6Pozycja: &cbrak"))
                    .addLore(ChatUtil.color("&6Teren: &cbrak"))
                    .addLore(ChatUtil.color("&6Zycia: &cbrak"))
                    .addLore(ChatUtil.color("&6HP: &cbrak"));
        }
        inventory.setItem(0, stast.build());
        inventory.setItem(1, turbodrop.build());
        inventory.setItem(2, schowek.build());
        inventory.setItem(3, gildia.build());
        p.openInventory(inventory);
    }
}
