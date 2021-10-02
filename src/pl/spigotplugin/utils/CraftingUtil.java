package pl.spigotplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class CraftingUtil {
    public static String invName;
    public static String invName1;
    public static String invName2;
    public static String invName3;
    public static String invName4;
    public static String invName5;
    public static String invName6;
    public static String invName7;
    public static String invName8;
    public static ItemStack a;
    public static ItemStack b;
    public static ItemStack c;
    public static ItemStack d;
    public static ItemStack e;
    public static ItemStack f;
    public static ItemStack g;
    public static ItemStack h;

    static {
        CraftingUtil.invName = ChatUtil.color("&7&lCraftingi");
        CraftingUtil.invName1 = ChatUtil.color("&7&lCrafting 1/8");
        CraftingUtil.invName2 = ChatUtil.color("&7&lCrafting 2/8");
        CraftingUtil.invName3 = ChatUtil.color("&7&lCrafting 3/8");
        CraftingUtil.invName4 = ChatUtil.color("&7&lCrafting 4/8");
        CraftingUtil.invName5 = ChatUtil.color("&7&lCrafting 5/8");
        CraftingUtil.invName6 = ChatUtil.color("&7&lCrafting 6/8");
        CraftingUtil.invName7 = ChatUtil.color("&7&lCrafting 7/8");
        CraftingUtil.invName8 = ChatUtil.color("&7&lCrafting 8/8");
        CraftingUtil.a = new ItemBuilder(Material.ENDER_STONE).setTitle(ChatUtil.color("&c&lStoniarka")).build();
        CraftingUtil.b = new ItemBuilder(Material.ENDER_PORTAL_FRAME, 4).setTitle(ChatUtil.color("&c&lBoyFarmer")).build();
        CraftingUtil.c = new ItemBuilder(Material.ENDER_CHEST).setTitle(ChatUtil.color("&c&lEnderchest")).build();
        CraftingUtil.d = new ItemBuilder(Material.NAME_TAG).setTitle(ChatUtil.color("&c&lAntyNogi")).build();
        CraftingUtil.e = new ItemBuilder(Material.POTION, (short) 8227).setTitle(ChatUtil.color("&c&lPotka fire")).build();
        CraftingUtil.f = new ItemBuilder(Material.GOLDEN_APPLE,1,(short)0).setTitle("&cRefil").build();
        CraftingUtil.g = new ItemBuilder(Material.GOLDEN_APPLE,1,(short)1).setTitle("&cKox").build();
        CraftingUtil.h = new ItemBuilder(Material.PUMPKIN_PIE).setTitle("&cPumpkin").setGlow(true).build();
    }

    public static void openMenu(Player p) {
        Inventory inv = Bukkit.createInventory(p, 9, invName);
        inv.setItem(0, a);
        inv.setItem(1, b);
        inv.setItem(2, c);
        inv.setItem(3, d);
        inv.setItem(4, e);
        inv.setItem(6, h);
        inv.setItem(7, f);
        inv.setItem(8, g);
        p.openInventory(inv);
    }
    public static void openPumpkin(Player p){
        Inventory inv = Bukkit.createInventory(p, InventoryType.WORKBENCH, invName8);
        ItemStack iron = new ItemStack(Material.IRON_INGOT);
        ItemStack apple = new ItemStack(Material.APPLE);

        inv.setItem(1, iron);
        inv.setItem(2, iron);
        inv.setItem(3, iron);
        inv.setItem(4, iron);
        inv.setItem(5, apple);
        inv.setItem(6, iron);
        inv.setItem(7, iron);
        inv.setItem(8, iron);
        inv.setItem(9, iron);
        ItemStack autoc = new ItemBuilder(Material.PUMPKIN_PIE).setGlow(true).setTitle(ChatUtil.color("&c&lAutoCrafting")).addLore(ChatUtil.color("&7Aby utworzyc item potrzebujesz:")).addLore(ChatUtil.color("")).addLore(ChatUtil.color("&8- &c8 zelaza")).addLore(ChatUtil.color("&8- &c1 jablko")).build();
        inv.setItem(0, autoc);

        p.openInventory(inv);
    }
    public static void openRefil(Player p){
            Inventory inv = Bukkit.createInventory(p, InventoryType.WORKBENCH, invName6);
            ItemStack gold = new ItemStack(Material.GOLD_INGOT);
            ItemStack apple = new ItemStack(Material.APPLE);

        inv.setItem(1, gold);
        inv.setItem(2, gold);
        inv.setItem(3, gold);
        inv.setItem(4, gold);
        inv.setItem(5, apple);
        inv.setItem(6, gold);
        inv.setItem(7, gold);
        inv.setItem(8, gold);
        inv.setItem(9, gold);
        ItemStack autoc = new ItemBuilder(Material.GOLDEN_APPLE,1,(short)0).setTitle(ChatUtil.color("&c&lAutoCrafting")).addLore(ChatUtil.color("&7Aby utworzyc item potrzebujesz:")).addLore(ChatUtil.color("")).addLore(ChatUtil.color("&8- &c8 zlota")).addLore(ChatUtil.color("&8- &c1 jablko")).build();
        inv.setItem(0, autoc);

        p.openInventory(inv);
    }

    public static void openKox(Player p){
        Inventory inv = Bukkit.createInventory(p, InventoryType.WORKBENCH, invName7);
        ItemStack goldblock = new ItemStack(Material.GOLD_BLOCK);
        ItemStack apple = new ItemStack(Material.APPLE);

        inv.setItem(1, goldblock);
        inv.setItem(2, goldblock);
        inv.setItem(3, goldblock);
        inv.setItem(4, goldblock);
        inv.setItem(5, apple);
        inv.setItem(6, goldblock);
        inv.setItem(7, goldblock);
        inv.setItem(8, goldblock);
        inv.setItem(9, goldblock);
        ItemStack autoc = new ItemBuilder(Material.GOLDEN_APPLE,1,(short)1).setTitle(ChatUtil.color("&c&lAutoCrafting")).addLore(ChatUtil.color("&7Aby utworzyc item potrzebujesz:")).addLore(ChatUtil.color("")).addLore(ChatUtil.color("&8- &c8 blokow zlota")).addLore(ChatUtil.color("&8- &c1 jablko")).build();
        inv.setItem(0, autoc);

        p.openInventory(inv);
    }

    public static void openEndStone(Player p) {
        Inventory inv = Bukkit.createInventory(p, InventoryType.WORKBENCH, invName1);
        ItemStack obs = new ItemStack(Material.COBBLESTONE);
        ItemStack red = new ItemStack(Material.REDSTONE_BLOCK);

        inv.setItem(1, obs);
        inv.setItem(2, obs);
        inv.setItem(3, obs);
        inv.setItem(4, obs);
        inv.setItem(5, red);
        inv.setItem(6, obs);
        inv.setItem(7, obs);
        inv.setItem(8, obs);
        inv.setItem(9, obs);
        ItemStack autoc = new ItemBuilder(Material.ENDER_STONE).setTitle(ChatUtil.color("&c&lAutoCrafting")).addLore(ChatUtil.color("&7Aby utworzyc item potrzebujesz:")).addLore(ChatUtil.color("")).addLore(ChatUtil.color("&8- &c8 cobblestone")).addLore(ChatUtil.color("&8- &c1 redstone block")).build();
        inv.setItem(0, autoc);

        p.openInventory(inv);
    }

    public static void openBoy(Player p) {
        Inventory inv = Bukkit.createInventory(p, InventoryType.WORKBENCH, invName2);
        ItemStack obs = new ItemStack(Material.OBSIDIAN);
        ItemStack block = new ItemStack(Material.REDSTONE_BLOCK);
        ItemStack gold = new ItemStack(Material.GOLD_BLOCK);

        inv.setItem(1, obs);
        inv.setItem(2, obs);
        inv.setItem(3, obs);
        inv.setItem(4, gold);
        inv.setItem(5, block);
        inv.setItem(6, gold);
        inv.setItem(7, obs);
        inv.setItem(8, obs);
        inv.setItem(9, obs);
        ItemStack autoc = new ItemBuilder(Material.ENDER_PORTAL_FRAME).setTitle(ChatUtil.color("&c&lAutoCrafting")).addLore(ChatUtil.color("&7Aby utworzyc item potrzebujesz:")).addLore(ChatUtil.color("")).addLore(ChatUtil.color("&8- &c6 obsidian")).addLore(ChatUtil.color("&8- &c2 gold block")).addLore(ChatUtil.color("&8- &c1 redstone block")).build();
        inv.setItem(0, autoc);

        p.openInventory(inv);
    }

    public static void openEnderchest(Player p) {
        Inventory inv = Bukkit.createInventory(p, InventoryType.WORKBENCH, invName3);
        ItemStack obs = new ItemStack(Material.OBSIDIAN);
        ItemStack pearl = new ItemStack(Material.ENDER_PEARL);

        inv.setItem(1, obs);
        inv.setItem(2, obs);
        inv.setItem(3, obs);
        inv.setItem(4, obs);
        inv.setItem(5, pearl);
        inv.setItem(6, obs);
        inv.setItem(7, obs);
        inv.setItem(8, obs);
        inv.setItem(9, obs);
        ItemStack autoc = new ItemBuilder(Material.ENDER_CHEST).setTitle(ChatUtil.color("&c&lAutoCrafting")).addLore(ChatUtil.color("&7Aby utworzyc item potrzebujesz:")).addLore(ChatUtil.color("")).addLore(ChatUtil.color("&8- &c8 obsidian")).addLore(ChatUtil.color("&8- &c1 ender pearl")).addLore(ChatUtil.color("")).build();
        inv.setItem(0, autoc);

        p.openInventory(inv);
    }

    public static void openAntyNogi(Player p) {
        Inventory inv = Bukkit.createInventory(p, InventoryType.WORKBENCH, invName4);

        ItemStack cobb = new ItemStack(Material.GOLD_BLOCK, 1);
        ItemStack cob1 = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);

        inv.setItem(1, cobb);
        inv.setItem(2, cobb);
        inv.setItem(3, cobb);
        inv.setItem(4, cobb);
        inv.setItem(5, cob1);
        inv.setItem(6, cobb);
        inv.setItem(7, cobb);
        inv.setItem(8, cobb);
        inv.setItem(9, cobb);
        ItemStack autoc = new ItemBuilder(Material.NAME_TAG).setTitle(ChatUtil.color("&c&lAutoCrafting")).addLore(ChatUtil.color("&7Aby utworzyc item potrzebujesz:")).addLore(ChatUtil.color("")).addLore(ChatUtil.color("&8- &c8 gold block")).addLore(ChatUtil.color("&8- &c1 Glowe gracza")).addLore(ChatUtil.color("")).build();
        inv.setItem(0, autoc);

        p.openInventory(inv);
    }

    public static void openPotka(Player p) {
        Inventory inv = Bukkit.createInventory(p, InventoryType.WORKBENCH, invName5);

        ItemStack cobb = new ItemStack(Material.GLASS, 1);
        ItemStack cob1 = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);

        inv.setItem(1, cobb);
        inv.setItem(2, cobb);
        inv.setItem(3, cobb);
        inv.setItem(4, cobb);
        inv.setItem(5, cob1);
        inv.setItem(6, cobb);
        inv.setItem(7, cobb);
        inv.setItem(8, cobb);
        inv.setItem(9, cobb);
        ItemStack autoc = new ItemBuilder(Material.POTION, (short) 8227).setTitle(ChatUtil.color("&c&lAutoCrafting")).addLore(ChatUtil.color("&7Aby utworzyc item potrzebujesz:")).addLore(ChatUtil.color("")).addLore(ChatUtil.color("&8- &c8 szklo")).addLore(ChatUtil.color("&8- &c1 Glowe gracza")).addLore(ChatUtil.color("")).build();
        inv.setItem(0, autoc);

        p.openInventory(inv);
    }

    public static void registerRecipe() {
        Bukkit.addRecipe(new ShapedRecipe(new ItemBuilder(Material.ENDER_STONE, 1).setTitle(ChatUtil.color("&a&lStoniarka")).addEnchantment(Enchantment.THORNS, 10).build()).shape("aaa", "aba", "aaa").setIngredient('a', Material.COBBLESTONE).setIngredient('b', Material.REDSTONE_BLOCK));
        Bukkit.addRecipe(new ShapedRecipe(new ItemBuilder(Material.PUMPKIN_PIE, 1).build()).shape("aaa", "aba", "aaa").setIngredient('a', Material.IRON_INGOT).setIngredient('b', Material.APPLE));
        Bukkit.addRecipe(new ShapedRecipe(new ItemBuilder(Material.ENDER_PORTAL_FRAME, 4).setTitle(ChatUtil.color("&a&lBoyFarmer")).addEnchantment(Enchantment.THORNS, 10).build()).shape("aaa", "csc", "aaa").setIngredient('a', Material.OBSIDIAN).setIngredient('s', Material.REDSTONE_BLOCK).setIngredient('c', Material.GOLD_BLOCK));
        Bukkit.addRecipe(new ShapedRecipe(new ItemBuilder(Material.NAME_TAG, 1).setTitle(ChatUtil.color("&6&lAnty Nogi")).addLore(ChatUtil.color("")).addLore(ChatUtil.color("&8\u00bb &2Kliknij PPM, aby uratowac czlonka gildii!")).addEnchantment(Enchantment.DURABILITY, 2).build()).shape("aaa", "asa", "aaa").setIngredient('a', Material.GOLD_BLOCK).setIngredient('s', Material.SKULL_ITEM));
        Bukkit.addRecipe(new ShapedRecipe(new ItemStack(Material.ENDER_CHEST, 1)).shape("sss", "sps", "sss").setIngredient('s', Material.OBSIDIAN).setIngredient('p', Material.ENDER_PEARL));
        Bukkit.addRecipe(new ShapedRecipe(new ItemStack(Material.POTION, 1, (short) 8227)).shape("sss", "sps", "sss").setIngredient('s', Material.GLASS).setIngredient('p', Material.SKULL_ITEM));

    }
}
