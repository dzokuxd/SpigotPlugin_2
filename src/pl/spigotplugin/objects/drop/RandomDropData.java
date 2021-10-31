package pl.spigotplugin.objects.drop;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.configs.statues;
import pl.spigotplugin.configs.DropFile;
import pl.spigotplugin.managers.CombatManager;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.*;

import java.util.*;

public class RandomDropData implements DropData{
    private static final List<Drop> drops = new ArrayList<>();
    private static final Set<UUID> noCobble = new HashSet<>();
    private static final Set<UUID> noMsg = new HashSet<>();

    public RandomDropData() {
        super();
        RandomDropData.drops.clear();
        for (String s : DropFile.getConfig().getConfigurationSection("random-drops").getKeys(false)) {
            Drop d = new Drop(s);
            RandomDropData.drops.add(d);
        }
    }

    public static void changeNoCobble(UUID uuid) {
        if (RandomDropData.noCobble.contains(uuid)) {
            RandomDropData.noCobble.remove(uuid);
        } else {
            RandomDropData.noCobble.add(uuid);
        }
    }

    public static boolean isNoMsg(UUID uuid) {
        return RandomDropData.noMsg.contains(uuid);
    }

    public static void changeNoMsg(UUID uuid) {
        if (RandomDropData.noMsg.contains(uuid)) {
            RandomDropData.noMsg.remove(uuid);
        } else {
            RandomDropData.noMsg.add(uuid);
        }
    }

    public static boolean isNoCobble(UUID uuid) {
        return RandomDropData.noCobble.contains(uuid);
    }

    public static Drop getDropByName(String name) {
        for (Drop d : RandomDropData.drops) {
            if (d.getName().equalsIgnoreCase(name)) {
                return d;
            }
        }
        return null;
    }

    public static List<Drop> getDrops() {
        return RandomDropData.drops;
    }

    public void breakBlock(Block block, Player player, ItemStack item) {
        List<ItemStack> drop = new ArrayList<>();
        User u = UserManager.getUser(player);
        if (u == null) return;
        if(block.getType() == Material.STONE) {
            u.setWykStone(u.getWykStone()+1);
        }
        for (Drop d : RandomDropData.drops) {
            ItemStack itemDrop = d.getWhat().clone();
            int expDrop = d.getExp();
            if (!d.getFrom().equals(block.getType())) {
                continue;
            }
            if (!d.getTools().contains(item.getType())) {
                continue;
            }
            if (!d.getBiomes().contains(block.getBiome())) {
                continue;
            }
            int y = block.getLocation().getBlockY();
            if (y < d.getMinHeight()) {
                continue;
            }
            if (y > d.getMaxHeight()) {
                continue;
            }
            double chance = d.getChance();
            if (player.hasPermission("core.drop.vip")) {
                chance += 1.0;
            } else if (player.hasPermission("core.drop.svip")) {
                chance += 0.50;
            }
            if (statues.EVENTS_TURBO > System.currentTimeMillis() || u.getTurboDrop() > System.currentTimeMillis()) {
                chance += 2;
            }
            double bonus = d.getChance() / 100.0 * (100.0 + u.getLvl() * 1.2) - d.getChance();
            chance += bonus;
            if (!RandomUtil.getChance(chance)) {
                continue;
            }
            if (item.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS) && d.isFortune()) {
                int a = DropUtil.addFortuneEnchant((d.getMinAmount() == d.getMaxAmount()) ? d.getMinAmount() : RandomUtil.getRandInt(d.getMinAmount(), d.getMaxAmount()), item);
                itemDrop.setAmount(a);
                expDrop *= a;
            }
            if (!d.isDisabled(player.getUniqueId())) {
                drop.add(itemDrop);
                if (u.getDrops().containsKey(itemDrop.getType())) {
                    u.getDrops().put(itemDrop.getType(), u.getDrops().get(itemDrop.getType()) + 1);
                } else {
                    u.getDrops().put(itemDrop.getType(), 1);
                }
            }
            player.giveExp(expDrop);
            u.setExp(u.getExp() + expDrop);
            LevelUtil.checkLevel(u);

            if (d.getMessage().isEmpty()) {
                continue;
            }
            if (!CombatManager.isFighting(player)) {
                String msg = d.getMessage();
                msg = msg.replace("{AMOUNT}", Integer.toString(itemDrop.getAmount()));
                msg = msg.replace("{EXP}", expDrop + (d.getDisabled().contains(player.getUniqueId()) ? " &c(wylaczone)" : ""));
                if (!RandomDropData.isNoMsg(player.getUniqueId())) {
                    ChatUtil.sendActionBar(player, msg);
                }
            }

        }
        if (!RandomDropData.noCobble.contains(player.getUniqueId())) {
            drop.add(new ItemStack(item.containsEnchantment(Enchantment.SILK_TOUCH) ? Material.STONE : Material.COBBLESTONE, 1));
        }
        DropUtil.addItemsToPlayer(player, drop, block);
        DropUtil.recalculateDurability(player, item);
        block.setType(Material.AIR);
    }

    public DropType getDropType() {
        return DropType.RANDOM_DROP;
    }
}
