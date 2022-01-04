package pl.spigotplugin.holder;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.ItemBuilder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ItemHolder {
    private static final Map<String, ItemStack> items = new ConcurrentHashMap<>();

    public static void init() {
        put("gui.black", new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setTitle(ChatUtil.color("&7\u2022")).build());
        put("gui.back", new ItemBuilder(Material.FENCE_GATE, 1, (short) 14).setTitle("&4Wroc do poprzedniej strony!").build());
        put("gui.drop.main.stone", new ItemBuilder(Material.STONE).setTitle("&7Drop z &c&lStone").addLore("").addLore(" &7\u00bb &6Kliknij, aby przejsc dalej!").build());
        put("gui.drop.main.cx", new ItemBuilder(Material.MOSSY_COBBLESTONE).setTitle("&7Drop z &c&lCobbleX").addLore("").addLore(" &7\u00bb &6Kliknij, aby przejsc dalej!").build());
        put("gui.drop.main.611", new ItemBuilder(Material.ENDER_CHEST).setTitle("&7Drop z &c&lEasy6/1/1").addLore("").addLore(" &7\u00bb &6Kliknij, aby przejsc dalej!").build());
        put("gui.drop.main.easycase", new ItemBuilder(Material.CHEST).setTitle("&7Drop z &c&lEasyCase").addLore("").addLore(" &7\u00bb &6Kliknij, aby przejsc dalej!").build());
        put("gui.drop.easy611.611", new ItemBuilder(Material.DIAMOND_PICKAXE).setTitle("&4&lKilof 6/1/1").addLore("&7\u00bb &6Szansa: &c2.0").addEnchantment(Enchantment.DIG_SPEED,5).addEnchantment(Enchantment.DURABILITY,1).addEnchantment(Enchantment.LOOT_BONUS_BLOCKS,1).build());
        put("gui.drop.easy611.gold", new ItemBuilder(Material.GOLD_INGOT).setTitle("&e&lNagroda pocieszenia").addLore("&7\u00bb &6Szansa: &c49.0").build());
        put("gui.drop.easy611.dirt", new ItemBuilder(Material.DIRT).setTitle("&e&lNagroda pocieszenia").addLore("&7\u00bb &6Szansa: &c49.0").build());
        put("gui.drop.easycase.legendarne", new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setTitle("&7&lLegendarne").build());
        put("gui.drop.easycase.srednie", new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 4).setTitle("&7&lSrednie").build());
        put("gui.drop.easycase.slabe", new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setTitle("&7&lSlabe").build());
        put("gui.drop.easycase.najgorsze", new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 8).setTitle("&7&lNajgorsze").build());
        put("gui.drop.easycase.beacon", new ItemBuilder(Material.BEACON ,1).setTitle("&e&lBeacon").addLore("&7\u00bb &6Szansa: &c1.0").setGlow(true).build());
        put("gui.drop.easycase.easy611", new ItemBuilder(Material.ENDER_CHEST ,1).setTitle("&e&lEasy6/1/1").addLore("&7\u00bb &6Szansa: &c1.0").build());
        put("gui.drop.easycase.goldenhead", new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setTitle("&e&lGoldenHead").addLore("&7\u00bb &6Szansa: &c1.0").build());
        put("gui.drop.easycase.knock", new ItemBuilder(Material.DIAMOND_SWORD ,1).setTitle("&e&lMiecz knock 2").addEnchantment(Enchantment.KNOCKBACK,2).addLore("&7\u00bb &6Szansa: &c1.0").build());
        put("gui.drop.easycase.perly", new ItemBuilder(Material.ENDER_PEARL ,8).setTitle("&e&lPerly").addLore("&7\u00bb &6Szansa: &c1.0").setGlow(true).build());
        put("gui.drop.easycase.tnt", new ItemBuilder(Material.TNT ,16).setTitle("&e&lTNT").addLore("&6Szansa: &c5.0").build());
        put("gui.drop.easycase.biblioteczki", new ItemBuilder(Material.BOOKSHELF ,16).setTitle("&e&lBookshelf").addLore("&6Szansa: &c5.0").build());
        put("gui.drop.easycase.gold64", new ItemBuilder(Material.GOLD_INGOT ,64).setTitle("&e&lZloto").addLore("&6Szansa: &c5.0").build());
        put("gui.drop.easycase.ref", new ItemBuilder(Material.GOLDEN_APPLE ,16,(short) 0).setTitle("&e&lRefile").addLore("&6Szansa: &c5.0").build());
        put("gui.drop.easycase.kox", new ItemBuilder(Material.GOLDEN_APPLE ,16,(short) 1).setTitle("&e&lKox").addLore("&6Szansa: &c5.0").build());
        put("gui.drop.easycase.helm", new ItemBuilder(Material.DIAMOND_HELMET ,1).setTitle("&e&lDiamentowy Helm").addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,3).addEnchantment(Enchantment.DURABILITY,2).addLore("&6Szansa: &c10.0").build());
        put("gui.drop.easycase.klata", new ItemBuilder(Material.DIAMOND_CHESTPLATE ,1).setTitle("&e&lDiamentowa Klata").addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,3).addEnchantment(Enchantment.DURABILITY,2).addLore("&6Szansa: &c10.0").build());
        put("gui.drop.easycase.spodnie", new ItemBuilder(Material.DIAMOND_LEGGINGS ,1).setTitle("&e&lDiamentowe Spodnie").addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,3).addEnchantment(Enchantment.DURABILITY,2).addLore("&6Szansa: &c10.0").build());
        put("gui.drop.easycase.buty", new ItemBuilder(Material.DIAMOND_BOOTS ,1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,3).addEnchantment(Enchantment.DURABILITY,2).addLore("&6Szansa: &c10.0").build());
        put("gui.drop.easycase.kilof5", new ItemBuilder(Material.DIAMOND_PICKAXE ,1).setTitle("&e&lDiamentowy Kilof").addEnchantment(Enchantment.DIG_SPEED,5).addEnchantment(Enchantment.DURABILITY,3).addEnchantment(Enchantment.LOOT_BONUS_BLOCKS,3).addLore("&6Szansa: &c10.0").build());
        put("gui.drop.easycase.miecz", new ItemBuilder(Material.DIAMOND_SWORD).setTitle("&e&lMiecz Sharp").addEnchantment(Enchantment.DAMAGE_ALL,4).addLore("&6Szansa: &c20.0").build());
        put("gui.drop.easycase.kilof3", new ItemBuilder(Material.DIAMOND_PICKAXE ,1).setTitle("&e&lDiamentowy Kilof").addEnchantment(Enchantment.DIG_SPEED,3).addEnchantment(Enchantment.DURABILITY,2).addEnchantment(Enchantment.LOOT_BONUS_BLOCKS,2).addLore("&6Szansa: &c20.0").build());
        put("gui.drop.easycase.gold16", new ItemBuilder(Material.GOLD_INGOT ,16).setTitle("&e&lZloto").addLore("&6Szansa: &c20.0").build());
        put("gui.drop.easycase.anvil", new ItemBuilder(Material.ANVIL ,8).setTitle("&e&lKowadla").addLore("&6Szansa: &c20.0").build());
        put("gui.drop.easycase.dirt", new ItemBuilder(Material.DIRT ,64).setTitle("&e&lZiemia").addLore("&6Szansa: &c20.0").setGlow(true).build());
        put("gui.gameplay.tnt", new ItemBuilder(Material.TNT).setTitle("&cTNT &7dziala w godzinach &c12 &7- &c22").addLore("&7Od poziomu &c60 &7w dol").build());
        put("gui.gameplay.luk", new ItemBuilder(Material.BOW).setTitle("&7LUK &c4/1/1").addEnchantment(Enchantment.ARROW_DAMAGE, 4).addEnchantment(Enchantment.ARROW_FIRE, 1).addEnchantment(Enchantment.DURABILITY, 1).build());
        put("gui.gameplay.miecz", new ItemBuilder(Material.DIAMOND_SWORD).setTitle("&7MIECZ &c4/3/1").addEnchantment(Enchantment.DAMAGE_ALL, 4).addEnchantment(Enchantment.FIRE_ASPECT, 1).addEnchantment(Enchantment.DURABILITY, 3).build());
        put("gui.gameplay.knock", new ItemBuilder(Material.DIAMOND_SWORD).setTitle("&7&lKNOCK &c2").addEnchantment(Enchantment.KNOCKBACK,2).build());
        put("gui.gameplay.helm", new ItemBuilder(Material.DIAMOND_HELMET).setTitle("&7HELM &c3/2").addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 2).build());
        put("gui.gameplay.klata", new ItemBuilder(Material.DIAMOND_CHESTPLATE).setTitle("&7KLATA &c3/2").addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 2).build());
        put("gui.gameplay.spodnie", new ItemBuilder(Material.DIAMOND_LEGGINGS).setTitle("&7SPODNIE &c3/2").addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 2).build());
        put("gui.gameplay.buty", new ItemBuilder(Material.DIAMOND_BOOTS).setTitle("&7BUTY &c3/2").addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 2).addEnchantment(Enchantment.PROTECTION_FALL, 3).build());
        put("gui.check.yes", new ItemBuilder(Material.STAINED_CLAY, 1, (short) 13).setTitle("&2&lPrzyznaje sie").addLore("&7Kliknij, aby dostac bana").addLore("&cJesli sie przyznasz dostaniesz bana na 1 dzien").build());
        put("gui.check.no", new ItemBuilder(Material.STAINED_CLAY, 1, (short) 14).setTitle("&2&lNie przyznaje sie").addLore("&7Kliknij, aby pozostac na serwerze").addLore("&7Jesli sie nie przyznasz sprawdzanie bedzie trwalo dalej").build());

    }

    private static void put(String key, ItemStack item) {
        items.put(key.toLowerCase(), item);
    }

    public static ItemStack get(String key) {
        return items.getOrDefault(key.toLowerCase(), new ItemStack(Material.DIRT));
    }
}
