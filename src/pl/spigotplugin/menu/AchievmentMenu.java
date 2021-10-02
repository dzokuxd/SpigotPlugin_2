package pl.spigotplugin.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.spigotplugin.enums.AchievmentType;
import pl.spigotplugin.enums.AchievmentTypeName;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.DataUtil;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AchievmentMenu {

    public static void open(Player player) {
        Inventory inventory = Bukkit.createInventory(null,27,"Osiagniecia:");

        for (final AchievmentTypeName value : AchievmentTypeName.values()) {
            inventory.setItem(value.getSlot(), value.getItemStack());
        }

        player.openInventory(inventory);
    }

    private static List<String> convert(String str) {
        String[] split = str.split(";:;");
        List<String> list = new LinkedList<>();
        Collections.addAll(list, split);
        return list;
    }

    public static void openSub(Player player, AchievmentTypeName type) {
        User user = UserManager.getUser(player);
        Inventory inventory = null;
        switch (type) {
            case STONE:{
                inventory = Bukkit.createInventory(null,27,(ChatUtil.color("&7&lOsiagniecia - &cStone")));


                AtomicInteger atomicInteger = new AtomicInteger(9);
                for (AchievmentType value : AchievmentType.values()) {
                    if(value.getType() == type) {

                        ItemStack guiIcon = value.getGuiIcon().clone();

                        String name = value.name();
                        int tryToGetANumberForThisShit = Integer.parseInt(name.replace("STONE_",""));

                        guiIcon.setAmount(tryToGetANumberForThisShit);

                        ItemMeta itemMeta = guiIcon.getItemMeta();
                        itemMeta.setDisplayName(ChatUtil.color(value.getDisplayName()));

                        List<String> thisIsVeryShit = new LinkedList<>();

                        for (String s : convert(value.getGuiLore())) {
                            thisIsVeryShit.add(ChatUtil.color(s.replace("%mined",String.valueOf(user.getWykStone()))));
                        }

                        itemMeta.setLore(thisIsVeryShit);
                        guiIcon.setItemMeta(itemMeta);

                        inventory.setItem(atomicInteger.getAndIncrement(), guiIcon);

                    }

                }
                break;
            }
            case OBSIDIAN:{
                inventory = Bukkit.createInventory(null,27,(ChatUtil.color("&7&lOsiagniecia - &cObsidian")));

                for (int i = 0; i < 27; i++) {
                    inventory.setItem(i,new ItemStack(Material.STAINED_GLASS_PANE,1,(short)15));
                }

                AtomicInteger atomicInteger = new AtomicInteger(9);
                for (AchievmentType value : AchievmentType.values()) {
                    if(value.getType() == type) {

                        ItemStack guiIcon = value.getGuiIcon().clone();

                        String name = value.name();
                        int tryToGetANumberForThisShit = Integer.parseInt(name.replace("OBS_",""));

                        guiIcon.setAmount(tryToGetANumberForThisShit);

                        ItemMeta itemMeta = guiIcon.getItemMeta();
                        itemMeta.setDisplayName(ChatUtil.color(value.getDisplayName()));

                        List<String> thisIsVeryShit = new LinkedList<>();

                        for (String s : convert(value.getGuiLore())) {
                            thisIsVeryShit.add(ChatUtil.color(s.replace("%obs",String.valueOf(user.getWykObsidian()))));
                        }

                        itemMeta.setLore(thisIsVeryShit);
                        guiIcon.setItemMeta(itemMeta);

                        inventory.setItem(atomicInteger.getAndIncrement(), guiIcon);

                    }

                }

                break;
            }
            case KOX:{
                inventory = Bukkit.createInventory(null,27,(ChatUtil.color("&7&lOsiagniecia - &cZjedzone koxy")));

                for (int i = 0; i < 27; i++) {
                    inventory.setItem(i,new ItemStack(Material.STAINED_GLASS_PANE,1,(short)15));
                }

                AtomicInteger atomicInteger = new AtomicInteger(9);
                for (AchievmentType value : AchievmentType.values()) {
                    if(value.getType() == type) {

                        ItemStack guiIcon = value.getGuiIcon().clone();

                        String name = value.name();
                        int tryToGetANumberForThisShit = Integer.parseInt(name.replace("KOX_",""));

                        guiIcon.setAmount(tryToGetANumberForThisShit);

                        ItemMeta itemMeta = guiIcon.getItemMeta();
                        itemMeta.setDisplayName(ChatUtil.color(value.getDisplayName()));

                        List<String> thisIsVeryShit = new LinkedList<>();

                        for (String s : convert(value.getGuiLore())) {
                            thisIsVeryShit.add(ChatUtil.color(s.replace("%kox",String.valueOf(user.getKoxEaten()))));
                        }

                        itemMeta.setLore(thisIsVeryShit);
                        guiIcon.setItemMeta(itemMeta);

                        inventory.setItem(atomicInteger.getAndIncrement(), guiIcon);

                    }

                }

                break;
            }
            case REF:{
                inventory = Bukkit.createInventory(null,27,(ChatUtil.color("&7&lOsiagniecia - &cZjedzone refile")));

                for (int i = 0; i < 27; i++) {
                    inventory.setItem(i,new ItemStack(Material.STAINED_GLASS_PANE,1,(short)15));
                }

                AtomicInteger atomicInteger = new AtomicInteger(9);
                for (AchievmentType value : AchievmentType.values()) {
                    if(value.getType() == type) {

                        ItemStack guiIcon = value.getGuiIcon().clone();

                        String name = value.name();
                        int tryToGetANumberForThisShit = Integer.parseInt(name.replace("REF_",""));

                        guiIcon.setAmount(tryToGetANumberForThisShit);

                        ItemMeta itemMeta = guiIcon.getItemMeta();
                        itemMeta.setDisplayName(ChatUtil.color(value.getDisplayName()));

                        List<String> thisIsVeryShit = new LinkedList<>();

                        for (String s : convert(value.getGuiLore())) {
                            thisIsVeryShit.add(ChatUtil.color(s.replace("%ref",String.valueOf(user.getRefilEaten()))));
                        }

                        itemMeta.setLore(thisIsVeryShit);
                        guiIcon.setItemMeta(itemMeta);

                        inventory.setItem(atomicInteger.getAndIncrement(), guiIcon);

                    }

                }

                break;
            }
            case TIME:{
                inventory = Bukkit.createInventory(null,27,(ChatUtil.color("&7&lOsiagniecia - &cCzas gry")));

                for (int i = 0; i < 27; i++) {
                    inventory.setItem(i,new ItemStack(Material.STAINED_GLASS_PANE,1,(short)15));
                }

                AtomicInteger atomicInteger = new AtomicInteger(9);
                for (AchievmentType value : AchievmentType.values()) {
                    if(value.getType() == type) {

                        ItemStack guiIcon = value.getGuiIcon().clone();

                        String name = value.name();
                        int tryToGetANumberForThisShit = Integer.parseInt(name.replace("TIME_",""));

                        guiIcon.setAmount(tryToGetANumberForThisShit);

                        ItemMeta itemMeta = guiIcon.getItemMeta();
                        itemMeta.setDisplayName(ChatUtil.color(value.getDisplayName()));

                        List<String> thisIsVeryShit = new LinkedList<>();
                        String spedzonyczas = DataUtil.secondsToStringNoMinus(user.getTime());
                        for (String s : convert(value.getGuiLore())) {
                            thisIsVeryShit.add(ChatUtil.color(s.replace("%time", spedzonyczas)));
                        }

                        itemMeta.setLore(thisIsVeryShit);
                        guiIcon.setItemMeta(itemMeta);

                        inventory.setItem(atomicInteger.getAndIncrement(), guiIcon);

                    }

                }

                break;
            }
            case KILLS:{
                inventory = Bukkit.createInventory(null,27,(ChatUtil.color("&7&lOsiagniecia - &cZabojstwa")));

                for (int i = 0; i < 27; i++) {
                    inventory.setItem(i,new ItemStack(Material.STAINED_GLASS_PANE,1,(short)15));
                }

                AtomicInteger atomicInteger = new AtomicInteger(9);
                for (AchievmentType value : AchievmentType.values()) {
                    if(value.getType() == type) {

                        ItemStack guiIcon = value.getGuiIcon().clone();

                        String name = value.name();
                        int tryToGetANumberForThisShit = Integer.parseInt(name.replace("KILLS_",""));

                        guiIcon.setAmount(tryToGetANumberForThisShit);

                        ItemMeta itemMeta = guiIcon.getItemMeta();
                        itemMeta.setDisplayName(ChatUtil.color(value.getDisplayName()));

                        List<String> thisIsVeryShit = new LinkedList<>();

                        for (String s : convert(value.getGuiLore())) {
                            thisIsVeryShit.add(ChatUtil.color(s.replace("%killed",String.valueOf(user.getKills()))));
                        }

                        itemMeta.setLore(thisIsVeryShit);
                        guiIcon.setItemMeta(itemMeta);

                        inventory.setItem(atomicInteger.getAndIncrement(), guiIcon);

                    }

                }
                break;
            }
            case ASYSTY:{
                inventory = Bukkit.createInventory(null,27,(ChatUtil.color("&7&lOsiagniecia - &cAsysty")));

                for (int i = 0; i < 27; i++) {
                    inventory.setItem(i,new ItemStack(Material.STAINED_GLASS_PANE,1,(short)15));
                }

                AtomicInteger atomicInteger = new AtomicInteger(9);
                for (AchievmentType value : AchievmentType.values()) {
                    if(value.getType() == type) {

                        ItemStack guiIcon = value.getGuiIcon().clone();

                        String name = value.name();
                        int tryToGetANumberForThisShit = Integer.parseInt(name.replace("ASYSTY_",""));

                        guiIcon.setAmount(tryToGetANumberForThisShit);

                        ItemMeta itemMeta = guiIcon.getItemMeta();
                        itemMeta.setDisplayName(ChatUtil.color(value.getDisplayName()));

                        List<String> thisIsVeryShit = new LinkedList<>();

                        for (String s : convert(value.getGuiLore())) {
                            thisIsVeryShit.add(ChatUtil.color(s.replace("%asysty",String.valueOf(user.getAsysty()))));
                        }

                        itemMeta.setLore(thisIsVeryShit);
                        guiIcon.setItemMeta(itemMeta);

                        inventory.setItem(atomicInteger.getAndIncrement(), guiIcon);

                    }

                }
                break;
            }
        }

        if(inventory == null) {
            System.out.println(ChatUtil.color("&cCos poszlo nie tak!"));
            return;
        }
        player.openInventory(inventory);
    }
}