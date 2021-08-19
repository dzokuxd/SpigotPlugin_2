package pl.spigotplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
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
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.settings.Settings;
import pl.spigotplugin.utils.*;

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

        final Guild guild = GuildManager.getGuild(b.getLocation());

        if (guild != null) {
            final User user = UserManager.getUser(p);

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
            return;
        }

        if (p.hasPermission("regionplugin.bypass")) {
            return;
        }

        if (CuboidUtil.isOutsideSpawn(b.getLocation())) {
            e.setBuild(false);
            e.setCancelled(true);
            p.sendMessage("&cTa interakcja jest zablokowana!");
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
            if (Math.random() * 100.0 < 2.0) {
                ItemStack kilof = new ItemStack(Material.DIAMOND_PICKAXE);
                ItemMeta meta = kilof.getItemMeta();
                meta.addEnchant(Enchantment.DIG_SPEED, 6, true);
                meta.addEnchant(Enchantment.DURABILITY, 1, true);
                meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 1, true);
                kilof.setItemMeta(meta);
                ItemUtil.giveItems(p,kilof);
                Bukkit.broadcastMessage("&6Gracz &c" + p.getName() + " &6otworzyl skrzynie &cEasy6/1/1 &6i wylosowal &c&lKilof 6/1/1");
            } else if (Math.random() * 100.0 < 49.0) {
                ItemStack dirt = new ItemStack(Material.DIRT);
                ItemUtil.giveItems(p,dirt);
            } else if (Math.random() * 100.0 < 49.0) {
                ItemStack gold = new ItemStack(Material.GOLD_INGOT);
                ItemUtil.giveItems(p,gold);
            }
        }
        if (p.getItemInHand().isSimilar(getCaseItem1())) {
            e.setCancelled(true);
            b.setType(Material.AIR);
            p.getInventory().removeItem(getCaseItem1());
            // zmienic
        }
        if (p.getGameMode().equals(GameMode.SURVIVAL) && p.getWorld().getName().equals("gtp")) {
            e.setCancelled(true);
            if (e.getBlockPlaced().getType() == Material.COBBLESTONE) {
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        e.getBlockPlaced().setType(Material.AIR);
                    }
                }.runTaskLater(SpigotPlugin.getPlugin(), 20 * 15);
            }
            if (e.getBlock().getY() < 50) {
                p.setGameMode(GameMode.ADVENTURE);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (!p.isOnline()) {
                            cancel();
                            return;
                        }
                        p.setGameMode(GameMode.SURVIVAL);
                    }
                }.runTaskLater(SpigotPlugin.getPlugin(), 20 * 5);
            }
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
