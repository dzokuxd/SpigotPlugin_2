package pl.spigotplugin.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.configs.guild;
import pl.spigotplugin.holder.ItemHolder;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.DataUtil;
import pl.spigotplugin.utils.ItemBuilder;
import pl.spigotplugin.utils.TNTUtil;

public class PanelMenu {
    public static void show(Player p, Guild g){
        Inventory inv = Bukkit.createInventory(p, 45, ChatUtil.color("&7&lPanel"));
        ItemStack itemStack = ItemHolder.get("gui.black");
        for (int j = 0; j < 45; j++) {
            inv.setItem(j, itemStack);
        }
        int size = g.getRegion().getSize() * 2 + 1;
        ItemStack odnow = new ItemBuilder(Material.WATCH, 1).setTitle("&7&lOdnow waznosc gildii!")
                .addLore("&6Przedluza waznosc gildii o &c24 &6Godziny")
                .addLore("&6Wygasa: "+(g.isExits() ? "&6za: &c" + DataUtil.secondsToString(g.getProlong()) : " &cWygasla"))
                .addLore("&6Potzrebujesz: &c"+ guild.RENEW_COST.getType()+"x"+ guild.RENEW_COST.getAmount())
                .addLore("")
                .addLore("&7Kliknij, aby przedluzc!").build();
        ItemStack powieksz = new ItemBuilder(
                Material.GRASS, 1)
                .setTitle("&7&lPowieksz teren gildii")
                .addLore("&6Powieksza teren gildii o &c"+ guild.CUBOID_SIZE_ADD+" &6kratki")
                .addLore("&6Aktualny rozmiar: &c"+ size +"&7x&c" +size)
                .addLore("&6Maxymalny rozmiar: &c"+(guild.CUBOID_SIZE_MAX))
                .addLore("&6Potrzebujesz: &c"+ (guild.COST_POWIEKSZ.getType())+"x"+ guild.COST_POWIEKSZ.getAmount())
                .addLore("")
                .addLore("&7Kliknij, aby powiekszyc!").build();
        ItemStack regen = new ItemBuilder(
                Material.STONE, 1)
                .setTitle("&7&lRegeneracja terenu gildii")
                .addLore("&6Regeneruje teren gildii po wybuchu &cTNT")
                .addLore("&6Dziala tylko gdy &cTNT &6jest &cWylaczone!")
                .addLore("&6Status &cTNT: "+ (TNTUtil.isBetween() ? "&aWlaczone" : "&cWylaczone"))
                .addLore((!g.isStartedRegen() ? "&6Bloki do regeneracji: &c"+ g.getRegen().split("!").length : "&6Bloki do regeneracji: &aW trakcie!"))
                .addLore((!g.isStartedRegen() ? "&6Koszt regeneracji: &c"+(g.getRegen().contains("!") ? (g.getRegen().split("!").length / 10) + 1 : "0") : "&6Koszt regeneracji: &aW trakcie!"))
                .addLore("")
                .addLore("&7Kliknij, aby zregenerowac!").build();
        ItemStack bloki = new ItemBuilder(
                Material.GOLD_BLOCK, 1)
                .setTitle("&7&lWplac bloki do sejfu")
                .addLore("&6Dodaje bloki zlota do sejfu")
                .addLore("&6Aktualny Stan: &c"+g.getGold())
                .addLore("")
                .addLore("&7Kliknij, aby wplacic!")
                .addLore("&4Uwaga: &cZabiera wszystkie bloki zlota z eq").build();
        ItemStack wither = new ItemBuilder(
                Material.SKULL_ITEM,1,(short) 1)
                .setTitle("&7&lWither")
                .addLore("&6Zabij go aby otrzymac Netherowa Gwiazde")
                .addLore("&6Potrzebujesz: &cx32GOLDEN_HEAD&7, &cx32KOX")
                .addLore("")
                .addLore("&7Kliknij, aby zrespic withera!").build();
        ItemStack hp = new ItemBuilder(
                Material.APPLE, 1)
                .setTitle("&7&lKup hp dla gildii")
                .addLore("&6Odnawia hp gildyjne")
                .addLore("&6Status: &c"+g.getHp())
                .addLore("&6Potrzebujesz: &c"+ (guild.COST_HP.getType())+"x" + guild.COST_HP.getAmount())
                .addLore("")
                .addLore("&7Kliknij, aby kupic hp").build();
        ItemStack limit = new ItemBuilder(
                Material.HOPPER, 1)
                .setTitle("&7&lLimit czlonkow gildii")
                .addLore("&6Zwieksza limit czlonkow w gildii")
                .addLore("&6Aktualny limit:&c "+g.getPlayersLimit())
                .addLore("&6Maxymalny limit: &c30")
                .addLore("&6Potrzebujesz: &c"+ (guild.COST_LIMIT.getType())+"x" + guild.COST_LIMIT.getAmount())
                .addLore("")
                .addLore("&7Kliknij, aby ulepszyc!").build();
        inv.setItem(10, odnow);
        inv.setItem(11, powieksz);
        inv.setItem(12, regen);
        inv.setItem(31, bloki);
        inv.setItem(13, wither);
        inv.setItem(14, hp);
        inv.setItem(15, limit);
        p.openInventory(inv);
    }
}
