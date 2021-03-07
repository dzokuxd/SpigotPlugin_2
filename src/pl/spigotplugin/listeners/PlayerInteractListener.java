package pl.spigotplugin.listeners;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Button;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.RandomUtil;
import pl.spigotplugin.utils.VoucherUtil;

import java.util.*;

public class PlayerInteractListener implements Listener {
    public static Map<UUID, Long> times = new HashMap<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (p.getItemInHand().isSimilar(VoucherUtil.vip)) {
            if (p.hasPermission("vip")) {
                p.sendMessage("&cPosiadasz ta lub lepsza range!");
                return;
            }
            Bukkit.broadcastMessage("&6Gracz &c" + p.getName() + " &6aktywowal Voucher na range &cVIP!");
            ChatUtil.removeItems(p, VoucherUtil.vip);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user" + p.getName() + " group set vip");
            return;
        }
        if (p.getItemInHand().isSimilar(VoucherUtil.svip)) {
            if (p.hasPermission("svip")) {
                p.sendMessage("&cPosiadasz ta lub lepsza range!");
                return;
            }
            Bukkit.broadcastMessage("&6Gracz &c" + p.getName() + " &6aktywowal Voucher na range &cSVIP!");
            ChatUtil.removeItems(p, VoucherUtil.svip);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user" + p.getName() + " group set svip");
            return;
        }
        Block clickedBlock = e.getClickedBlock();
        if(clickedBlock != null){
            if (clickedBlock.getType() == Material.STONE_BUTTON) {
                if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
                    return;
                Button button = (Button) clickedBlock.getState().getData();
                Block relative = clickedBlock.getRelative(button.getAttachedFace());

                if (relative.getType() != Material.JUKEBOX)
                    return;

                randomTP(p);
                return;
            }
        }
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.WOOD_BUTTON) {
            Button button = (Button) clickedBlock.getState().getData();
            Block base = clickedBlock.getRelative(button.getAttachedFace());
            if (base.getType() != Material.JUKEBOX) {
                return;
            }
            int x = RandomUtil.getRandInt(-Config.BORDER_NETHER, Config.BORDER_NETHER);
            int z = RandomUtil.getRandInt(-Config.BORDER_NETHER, Config.BORDER_NETHER);
            int i = 0;
            for (Player players : this.getPlayersInRadius(clickedBlock.getLocation(), 3)) {
                ++i;
                Location location = new Location(Bukkit.getWorld("world_nether"),x, p.getWorld().getHighestBlockYAt(x,z),z);
                if (i > 2) continue;
                p.teleport(location);
                Location ploc =p.getLocation().clone();
                ploc.setY(p.getLocation().getY() + 5.0);
               p.teleport(ploc);
                players.teleport(p.getLocation());
                for (PotionEffect activePotionEffect : players.getActivePotionEffects()) {
                    players.removePotionEffect(activePotionEffect.getType());
                }
            }
        }
        ItemStack k = e.getPlayer().getItemInHand();
        if (k.getType().equals(Material.DIAMOND_PICKAXE) && k.getDurability() == 1559) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,100, 100));
            p.sendMessage("&cTwoj kilof ma jedno uzycie, napraw go :)");
            e.setCancelled(true);
        }
        if (e.isCancelled()) {
            return;
        }
        if(e.getClickedBlock() == null)
            return;
        if (e.getClickedBlock().getType() != Material.LEVER) {
            return;
        }
        Long t = PlayerInteractListener.times.get(p.getUniqueId());
        if (t != null && System.currentTimeMillis() - t < 3000L) {
            p.sendMessage("&cOdczekaj chwile przed nastepnym uzyciem!");
            e.setCancelled(true);
            return;
        }
        PlayerInteractListener.times.put(p.getUniqueId(), System.currentTimeMillis());
    }//TODO dodac antynogi
    @EventHandler
    public void sing(SignChangeEvent e) {
        if (!e.getPlayer().hasPermission("spigotplugin.sign")) {
            return;
        }
        for (int i = 0; i <= 3; ++i) {
            e.setLine(i, ChatUtil.color(e.getLine(i)));
        }
    }
    @EventHandler
    public void onCraft(CraftItemEvent e) {
        ItemStack item = e.getRecipe().getResult();
        Player p = (Player) e.getWhoClicked();
        if (e.getInventory().getType().equals(InventoryType.WORKBENCH) && e.getSlotType().toString().equalsIgnoreCase("RESULT") && e.getCurrentItem().getType().name().equalsIgnoreCase("JUKEBOX")) {
            e.setCancelled(true);
        }
        if (item.getType() == Material.MINECART) {
            p.sendMessage( "&cCraftowanie wagonikow jest wylaczone!");
            e.setCancelled(true);
        }
    }
    private List<Player> getPlayersInRadius(Location location, int size) {
        List<Player> players = new ArrayList<>();
        for (Player p : location.getWorld().getPlayers()) {
            if (location.distance(p.getLocation()) <= size) {
                players.add(p);
            }
        }
        return players;
    }

    private void randomTP(Player player) {
        int x = RandomUtil.getRandInt(-Config.BORDER_WORLD, Config.BORDER_WORLD);
        int z = RandomUtil.getRandInt(-Config.BORDER_WORLD, Config.BORDER_WORLD);
        double y = player.getWorld().getHighestBlockYAt(x,z) + 1.5f;
        Location location = new Location(player.getWorld(), x,y,z);
        player.teleport(location);  //TODO dodac gildie
    }
}