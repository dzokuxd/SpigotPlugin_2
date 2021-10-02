package pl.spigotplugin.managers;


import org.bukkit.enchantments.Enchantment;

import java.util.HashMap;

public class EnchantManager {
    private static HashMap<String, Enchantment> enchants;

    static {
        (enchants = new HashMap<>()).put("alldamage", Enchantment.DAMAGE_ALL);
        EnchantManager.enchants.put("alldmg", Enchantment.DAMAGE_ALL);
        EnchantManager.enchants.put("sharpness", Enchantment.DAMAGE_ALL);
        EnchantManager.enchants.put("sharp", Enchantment.DAMAGE_ALL);
        EnchantManager.enchants.put("dal", Enchantment.DAMAGE_ALL);
        EnchantManager.enchants.put("ardmg", Enchantment.DAMAGE_ARTHROPODS);
        EnchantManager.enchants.put("baneofarthropods", Enchantment.DAMAGE_ARTHROPODS);
        EnchantManager.enchants.put("baneofarthropod", Enchantment.DAMAGE_ARTHROPODS);
        EnchantManager.enchants.put("arthropod", Enchantment.DAMAGE_ARTHROPODS);
        EnchantManager.enchants.put("dar", Enchantment.DAMAGE_ARTHROPODS);
        EnchantManager.enchants.put("undeaddamage", Enchantment.DAMAGE_UNDEAD);
        EnchantManager.enchants.put("smite", Enchantment.DAMAGE_UNDEAD);
        EnchantManager.enchants.put("du", Enchantment.DAMAGE_UNDEAD);
        EnchantManager.enchants.put("digspeed", Enchantment.DIG_SPEED);
        EnchantManager.enchants.put("efficiency", Enchantment.DIG_SPEED);
        EnchantManager.enchants.put("minespeed", Enchantment.DIG_SPEED);
        EnchantManager.enchants.put("cutspeed", Enchantment.DIG_SPEED);
        EnchantManager.enchants.put("ds", Enchantment.DIG_SPEED);
        EnchantManager.enchants.put("eff", Enchantment.DIG_SPEED);
        EnchantManager.enchants.put("durability", Enchantment.DURABILITY);
        EnchantManager.enchants.put("unb", Enchantment.DURABILITY);
        EnchantManager.enchants.put("dura", Enchantment.DURABILITY);
        EnchantManager.enchants.put("unbreaking", Enchantment.DURABILITY);
        EnchantManager.enchants.put("d", Enchantment.DURABILITY);
        EnchantManager.enchants.put("thorns", Enchantment.THORNS);
        EnchantManager.enchants.put("highcrit", Enchantment.THORNS);
        EnchantManager.enchants.put("thorn", Enchantment.THORNS);
        EnchantManager.enchants.put("highercrit", Enchantment.THORNS);
        EnchantManager.enchants.put("t", Enchantment.THORNS);
        EnchantManager.enchants.put("fireaspect", Enchantment.FIRE_ASPECT);
        EnchantManager.enchants.put("fire", Enchantment.FIRE_ASPECT);
        EnchantManager.enchants.put("meleefire", Enchantment.FIRE_ASPECT);
        EnchantManager.enchants.put("meleeflame", Enchantment.FIRE_ASPECT);
        EnchantManager.enchants.put("fa", Enchantment.FIRE_ASPECT);
        EnchantManager.enchants.put("knockback", Enchantment.KNOCKBACK);
        EnchantManager.enchants.put("kback", Enchantment.KNOCKBACK);
        EnchantManager.enchants.put("kb", Enchantment.KNOCKBACK);
        EnchantManager.enchants.put("k", Enchantment.KNOCKBACK);
        EnchantManager.enchants.put("blockslootbonus", Enchantment.LOOT_BONUS_BLOCKS);
        EnchantManager.enchants.put("fortune", Enchantment.LOOT_BONUS_BLOCKS);
        EnchantManager.enchants.put("fort", Enchantment.LOOT_BONUS_BLOCKS);
        EnchantManager.enchants.put("lbb", Enchantment.LOOT_BONUS_BLOCKS);
        EnchantManager.enchants.put("mobslootbonus", Enchantment.LOOT_BONUS_MOBS);
        EnchantManager.enchants.put("mobloot", Enchantment.LOOT_BONUS_MOBS);
        EnchantManager.enchants.put("looting", Enchantment.LOOT_BONUS_MOBS);
        EnchantManager.enchants.put("lbm", Enchantment.LOOT_BONUS_MOBS);
        EnchantManager.enchants.put("oxygen", Enchantment.OXYGEN);
        EnchantManager.enchants.put("respiration", Enchantment.OXYGEN);
        EnchantManager.enchants.put("breathing", Enchantment.OXYGEN);
        EnchantManager.enchants.put("breath", Enchantment.OXYGEN);
        EnchantManager.enchants.put("o", Enchantment.OXYGEN);
        EnchantManager.enchants.put("protection", Enchantment.PROTECTION_ENVIRONMENTAL);
        EnchantManager.enchants.put("prot", Enchantment.PROTECTION_ENVIRONMENTAL);
        EnchantManager.enchants.put("protect", Enchantment.PROTECTION_ENVIRONMENTAL);
        EnchantManager.enchants.put("p", Enchantment.PROTECTION_ENVIRONMENTAL);
        EnchantManager.enchants.put("explosionsprotection", Enchantment.PROTECTION_EXPLOSIONS);
        EnchantManager.enchants.put("explosionprotection", Enchantment.PROTECTION_EXPLOSIONS);
        EnchantManager.enchants.put("expprot", Enchantment.PROTECTION_EXPLOSIONS);
        EnchantManager.enchants.put("blastprotection", Enchantment.PROTECTION_EXPLOSIONS);
        EnchantManager.enchants.put("blastprotect", Enchantment.PROTECTION_EXPLOSIONS);
        EnchantManager.enchants.put("pe", Enchantment.PROTECTION_EXPLOSIONS);
        EnchantManager.enchants.put("fallprotection", Enchantment.PROTECTION_FALL);
        EnchantManager.enchants.put("fallprot", Enchantment.PROTECTION_FALL);
        EnchantManager.enchants.put("featherfall", Enchantment.PROTECTION_FALL);
        EnchantManager.enchants.put("featherfalling", Enchantment.PROTECTION_FALL);
        EnchantManager.enchants.put("pfa", Enchantment.PROTECTION_FALL);
        EnchantManager.enchants.put("fireprotection", Enchantment.PROTECTION_FIRE);
        EnchantManager.enchants.put("flameprotection", Enchantment.PROTECTION_FIRE);
        EnchantManager.enchants.put("fireprotect", Enchantment.PROTECTION_FIRE);
        EnchantManager.enchants.put("flameprotect", Enchantment.PROTECTION_FIRE);
        EnchantManager.enchants.put("fireprot", Enchantment.PROTECTION_FIRE);
        EnchantManager.enchants.put("flameprot", Enchantment.PROTECTION_FIRE);
        EnchantManager.enchants.put("pf", Enchantment.PROTECTION_FIRE);
        EnchantManager.enchants.put("projectileprotection", Enchantment.PROTECTION_PROJECTILE);
        EnchantManager.enchants.put("projprot", Enchantment.PROTECTION_PROJECTILE);
        EnchantManager.enchants.put("pp", Enchantment.PROTECTION_PROJECTILE);
        EnchantManager.enchants.put("silktouch", Enchantment.SILK_TOUCH);
        EnchantManager.enchants.put("softtouch", Enchantment.SILK_TOUCH);
        EnchantManager.enchants.put("st", Enchantment.SILK_TOUCH);
        EnchantManager.enchants.put("waterworker", Enchantment.WATER_WORKER);
        EnchantManager.enchants.put("aquaaffinity", Enchantment.WATER_WORKER);
        EnchantManager.enchants.put("watermine", Enchantment.WATER_WORKER);
        EnchantManager.enchants.put("ww", Enchantment.WATER_WORKER);
        EnchantManager.enchants.put("firearrow", Enchantment.ARROW_FIRE);
        EnchantManager.enchants.put("flame", Enchantment.ARROW_FIRE);
        EnchantManager.enchants.put("flamearrow", Enchantment.ARROW_FIRE);
        EnchantManager.enchants.put("af", Enchantment.ARROW_FIRE);
        EnchantManager.enchants.put("arrowdamage", Enchantment.ARROW_DAMAGE);
        EnchantManager.enchants.put("power", Enchantment.ARROW_DAMAGE);
        EnchantManager.enchants.put("arrowpower", Enchantment.ARROW_DAMAGE);
        EnchantManager.enchants.put("ad", Enchantment.ARROW_DAMAGE);
        EnchantManager.enchants.put("arrowknockback", Enchantment.ARROW_KNOCKBACK);
        EnchantManager.enchants.put("arrowkb", Enchantment.ARROW_KNOCKBACK);
        EnchantManager.enchants.put("punch", Enchantment.ARROW_KNOCKBACK);
        EnchantManager.enchants.put("arrowpunch", Enchantment.ARROW_KNOCKBACK);
        EnchantManager.enchants.put("ak", Enchantment.ARROW_KNOCKBACK);
        EnchantManager.enchants.put("infinitearrows", Enchantment.ARROW_INFINITE);
        EnchantManager.enchants.put("infarrows", Enchantment.ARROW_INFINITE);
        EnchantManager.enchants.put("infinity", Enchantment.ARROW_INFINITE);
        EnchantManager.enchants.put("infinite", Enchantment.ARROW_INFINITE);
        EnchantManager.enchants.put("unlimited", Enchantment.ARROW_INFINITE);
        EnchantManager.enchants.put("unlimitedarrows", Enchantment.ARROW_INFINITE);
        EnchantManager.enchants.put("ai", Enchantment.ARROW_INFINITE);
    }

    public static Enchantment get(String name) {
        return EnchantManager.enchants.get(name.toLowerCase());
    }

    public static HashMap<String, Enchantment> getEnchants() {
        return EnchantManager.enchants;
    }
}
