package pl.spigotplugin.listeners;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import pl.spigotplugin.SpigotPlugin;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.managers.CombatManager;
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.configs.Settings;
import pl.spigotplugin.utils.*;

import java.util.ArrayList;
import java.util.List;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();
        if (e.getBlockPlaced().getType() == Material.BREWING_STAND) {
            p.sendMessage("&cAlchemia zostala zablokowana!");
            e.setCancelled(true);
            return;
        }
        if (CheckUtil.checkedPlayers.contains(e.getPlayer())) {
            e.setCancelled(true);
            return;
        }
        if (CuboidUtil.isOutsideSpawn(b.getLocation()) && !p.hasPermission("regionplugin.bypass")) {
            e.setBuild(false);
            e.setCancelled(true);
            p.sendMessage("&cTa interakcja jest zablokowana!");
            return;
        }
        Guild guild = GuildManager.getGuild(b.getLocation());
        if (guild != null) {
            if (p.hasPermission("regionplugin.bypass"))
                return;
            User user = UserManager.getUser(p);
            if (user == null) {
                e.setBuild(false);
                e.setCancelled(true);
                return;
            }
            if (!user.getGuild().equals(guild.getTag())) {
                e.setBuild(false);
                e.setCancelled(true);
                return;
            }
            if (guild.getLastExplodeTime() + TimeUtil.SECOND.getTime(120) > System.currentTimeMillis()) {
                p.sendMessage("&cNa terenie gildii wybuchlo tnt nie mozesz budowac!!");
                e.setCancelled(true);
                return;
            }
            if (guild.isMember(p.getName()) && guild.getRegion().isInCentrum(e.getBlock().getLocation(), 3, 2, 3)) {
                e.setCancelled(true);
                p.sendMessage("&cNie mozesz budowac w centrum gildii!");
                return;
            }
            if (b.getLocation().getBlockY() >= 60) {
                if (e.getBlockPlaced().getType() == Material.CHEST) {
                    e.setBuild(false);
                    e.setCancelled(true);
                    p.sendMessage("Skrzynie mozesz postawic ponizej 60 poziomu");
                }
            }
            if (CombatManager.isFighting(p)) {
                e.setCancelled(true);
                e.setBuild(false);
            }
        }
        if (b.getLocation().getBlockY() >= 90) {
            if (CombatManager.isFighting(p)) {
                e.setCancelled(true);
                p.sendMessage("&cJestes podczas walki! Nie mozesz stawiac powyzej 90 poziomu!");
            }
        }
        if (e.getBlockPlaced().getType() == Material.OBSIDIAN) {
            e.setBuild(false);
            e.setCancelled(true);
            p.sendMessage("&cObsydianu mozesz uzywac tylko na terenie gildii");
        }
        if (b.getType() == Material.ENDER_STONE) {
            Block u = e.getBlock().getLocation().add(0.0, 1.0, 0.0).getBlock();
            if (!u.isEmpty()) {
                p.sendMessage("&cNie moze byc zadnego bloku nad generatorem!");
                e.setCancelled(true);
                return;
            }
            if (guild != null) {
                if (!guild.isMember(e.getPlayer().getName())) {
                    if (CombatManager.isFighting(p)) {
                        p.sendMessage("&cJestes podczas walki nie mozesz postawic stoniarki!");
                        e.setCancelled(true);
                        e.getBlock().setType(Material.AIR);
                        return;
                    }
                }
            }
            u.setType(Material.STONE);
            return;
        }

        if (p.getItemInHand().isSimilar(Settings.cobblexItem)) {
            e.setCancelled(true);
            e.getBlockPlaced().setType(Material.AIR);
            List<ItemStack> dropList = Settings.normalDropList;
            if (p.hasPermission("cobblex.premiumDrop")) {
                dropList = Settings.premiumDropList;
            }
            ItemUtil.giveDrop(e.getBlockPlaced().getLocation(), dropList);
            p.getInventory().removeItem(Settings.cobblexItem);
        }
    }//TODO dodac boyfarmery

    private static final List<ItemStack> itemStacks = new ArrayList<>();

    static {
        ItemStack kilof = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta meta = kilof.getItemMeta();
        meta.addEnchant(Enchantment.DIG_SPEED, 6, true);
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 1, true);
        kilof.setItemMeta(meta);

        for (int i = 0; i < 80; i++) {
            itemStacks.add(kilof);
        }

        ItemStack dirt = new ItemStack(Material.DIRT);
        for (int i = 0; i < 10; i++) {
            itemStacks.add(dirt);
        }

        ItemStack gold = new ItemStack(Material.GOLD_INGOT);
        for (int i = 0; i < 10; i++) {
            itemStacks.add(gold);
        }
    }

    @EventHandler
    private void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlockPlaced();
        if (p.getItemInHand().isSimilar(getCaseItem())) {
            e.setCancelled(true);
            b.setType(Material.AIR);
            p.getInventory().removeItem(getCaseItem());
            ItemUtil.giveItems(p, itemStacks.get(RandomUtil.getRandInt(0, itemStacks.size() - 1)));
        }
        if (p.getItemInHand().isSimilar(getCaseItem1())) {
            e.setCancelled(true);
            b.setType(Material.AIR);
            p.getInventory().removeItem(getCaseItem1());
            p.playEffect(p.getLocation(), Effect.EXPLOSION_HUGE, 10);
            // zmienic
        }
        if (p.getGameMode().equals(GameMode.SURVIVAL) && p.getWorld().getName().equals("gtp")) {
            e.setCancelled(true);
        }
    }
    private ItemStack getCaseItem() {
        ItemStack item = new ItemStack(Material.CHEST, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatUtil.color("&c&lSkrzynia Easy6/1/1"));
        item.setItemMeta(meta);
        return item;
    }
    private ItemStack getCaseItem1() {
        ItemStack item = new ItemStack(Material.CHEST, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatUtil.color("&c&lSkrzynia "+ Config.IP));
        item.setItemMeta(meta);
        return item;
    }
}
