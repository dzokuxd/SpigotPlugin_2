package pl.spigotplugin.listeners;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Button;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class PlayerInteractListener implements Listener {
    public static Map<UUID, Long> times = new HashMap<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack s = e.getItem();
        User u = UserManager.getUser(p);
        Block clickedBlock = e.getClickedBlock();
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
        if (u == null) {
            return;
        }
        if (p.getItemInHand().isSimilar(VoucherUtil.turbo)) {
            Bukkit.broadcastMessage("&6Gracz &c" + p.getName() + " &6aktywowal Voucher na &cTURBODROP 10M!");
            ChatUtil.removeItems(p, VoucherUtil.turbo);
            long turboDropHave = 0L;
            long currentTurboDrop = u.getTurboDrop();
            if(currentTurboDrop >System.currentTimeMillis()){
                turboDropHave = currentTurboDrop-System.currentTimeMillis();
            }
            long givenTurboDrop = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10);
            u.setTurboDrop(givenTurboDrop+turboDropHave);
            return;
        }
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
            int x = RandomUtil.getRandInt(-90, 90);
            int z = RandomUtil.getRandInt(-90, 90);
            int i = 0;
            World world_nether = Bukkit.getWorld("gtp");
            for (Player players : this.getPlayersInRadius(clickedBlock.getLocation(), 3)) {
                ++i;
                Location location = new Location(world_nether,x, world_nether.getHighestBlockYAt(x,z),z);
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
        if (s == null) {
            return;
        }
        int strzalyw = ItemUtil.getItemAmount(Material.ARROW, p, (short) 0);
        if (strzalyw > Config.LIMIT_STRZAL) {
            ItemStack item = new ItemStack(Material.ARROW, (strzalyw - Config.LIMIT_STRZAL), (short) 0);
            int added = ItemUtil.remove(item, p, Config.LIMIT_STRZAL);
            u.addStrzaly(added);
            u.addarrowsShoten(1);
            p.sendMessage("&6Posiadasz przy sobie wiecej niz &c" + Config.LIMIT_STRZAL + " &6strzal! &7(&c" + added + " &6strzaly zostaja odlozone do twojego schowka&7)");
        }
        if (s.getType() == Material.ENDER_PEARL) {
            int pearl = ItemUtil.getItemAmount(Material.ENDER_PEARL, p, (short) 0);
            if (pearl > Config.LIMIT_PEARL) {
                ItemStack item = new ItemStack(Material.ENDER_PEARL, (pearl - Config.LIMIT_PEARL), (short) 0);
                int added = ItemUtil.remove(item, p, Config.LIMIT_PEARL);
                u.addPerly(added);
                u.addpearlThrown(1);
                p.sendMessage("&6Posiadasz przy sobie wiecej niz &c" + Config.LIMIT_PEARL + " &6perel! &7(&c" + added + " &6perly zostaja odlozone do twojego schowka&7)");
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
    @EventHandler
    public void PlayerItemConsume(PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();
        User u = UserManager.getUser(p);
        ItemStack is = e.getItem();
        if (!is.getType().equals(Material.GOLDEN_APPLE)) {
            return;
        }
        int i = ItemUtil.getItemAmount(Material.GOLDEN_APPLE, p, (short) 1);
        if (i > Config.LIMIT_KOX) {
            ItemStack item = new ItemStack(Material.GOLDEN_APPLE, (i - Config.LIMIT_KOX), (short) 1);
            int added = ItemUtil.remove(item, p, Config.LIMIT_KOX);
            u.addKoxy(added);
            p.sendMessage("&6Posiadasz przy sobie wiecej niz &c" + Config.LIMIT_KOX + " &6koxy! &7(&c" + added + " &6koxy zostaja odlozone do twojego schowka&7)");
        }
        int ii = ItemUtil.getItemAmount(Material.GOLDEN_APPLE, p, (short) 0);
        if (ii > Config.LIMIT_REFILE) {
            ItemStack item = new ItemStack(Material.GOLDEN_APPLE, (ii - Config.LIMIT_REFILE), (short) 0);
            int added = ItemUtil.remove(item, p, Config.LIMIT_REFILE);
            u.addRefile(added);
            p.sendMessage("&6Posiadasz przy sobie wiecej niz &c" + Config.LIMIT_REFILE + " &6refile! &7(&c" + added + " &6refile zostaja odlozone do twojego schowka&7)");
        }
        if (is.getDurability() == 1) {
            e.setCancelled(true);
            p.getInventory().removeItem(new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1));

            p.removePotionEffect(PotionEffectType.ABSORPTION);
            p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2410, 1));

            p.removePotionEffect(PotionEffectType.REGENERATION);
            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 15, 4));
            u.addkoxEaten(1);

        } else {
            p.removePotionEffect(PotionEffectType.REGENERATION);
            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 2));

            p.removePotionEffect(PotionEffectType.ABSORPTION);
            p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2410, 0));
            p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 3, 0));
            u.addrefilEaten(1);

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