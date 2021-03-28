package pl.spigotplugin.managers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.spigotplugin.configs.DropFile;
import pl.spigotplugin.objects.drop.*;

import java.util.HashMap;

public class DropManager {
    private static HashMap<Material, DropData> drops;
    private static HashMap<Material, Integer> exps;

    static {
        drops = new HashMap<Material, DropData>();
        exps = new HashMap<Material, Integer>();
    }

    public static void setup() {
        DropManager.drops.clear();
        DropManager.exps.clear();
        for (String s : DropFile.getConfig().getStringList("cancel-drops")) {
            DropManager.drops.put(Material.getMaterial(s), new CancelDropData());
        }
        RandomDropData data = new RandomDropData();
        for (Drop d : RandomDropData.getDrops()) {
            DropManager.drops.put(d.getFrom(), data);
        }
        for (String s2 : DropFile.getConfig().getConfigurationSection("exp-drops").getKeys(false)) {
            DropManager.exps.put(Material.getMaterial(s2), DropFile.getConfig().getInt("exp-drops." + s2, 1));
        }
    }

    public static DropData getDropData(Material mat) {
        DropData drop = new NormalDropData();
        if (DropManager.drops.containsKey(mat)) {
            drop = DropManager.drops.get(mat);
        }
        return drop;
    }

    public static int getExp(Material mat, Player p) {
        int exp = 0;
        if (DropManager.exps.containsKey(mat)) {
            exp = DropManager.exps.get(mat);
        }
        return exp;
    }

    public static HashMap<Material, DropData> getDrops() {
        return DropManager.drops;
    }

    public static HashMap<Material, Integer> getExps() {
        return DropManager.exps;
    }
}