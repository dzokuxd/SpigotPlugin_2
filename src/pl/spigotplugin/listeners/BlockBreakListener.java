package pl.spigotplugin.listeners;

import io.netty.util.internal.ConcurrentSet;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.settings.Settings;
import pl.spigotplugin.utils.*;

import java.util.Set;

public class BlockBreakListener implements Listener {
    public static Set<Player> playerSet = new ConcurrentSet<>();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        User u = UserManager.getUser(p);
        if (e.getBlock().getType() == Material.STONE || e.getBlock().getType() == Material.COBBLESTONE) {
            if (playerSet.contains(p));
            int ammount = ItemUtil.getamount(Material.COBBLESTONE, p, (short) 0);
            if (ammount > 64 * 9) {
                p.getInventory().removeItem(new ItemStack(Material.COBBLESTONE, 64 * 9));
                p.getInventory().addItem(Settings.cobblexItem);
                p.sendMessage("&aPosiadasz za duzo cobbla w eq, zamienilem go na CobbleX");
            }
        }
        if (CheckUtil.checkedPlayers.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
        if (!p.getGameMode().equals(GameMode.SURVIVAL)) {
            return;
        }
        if (Config.EVENTS_CASE > System.currentTimeMillis() && RandomUtil.getChance(0.10)) {
            ItemStack d = new ItemBuilder(Material.CHEST, 1).setTitle(ChatUtil.color("&c&lSkrzynia "+Config.IP)).build();
            Bukkit.broadcastMessage("&6Gracz &c" + p.getName() + " &6wydropil &cSkrzynie "+Config.IP);
            Bukkit.broadcastMessage("&6Do konca eventu pozostalo &c" + DataUtil.secondsToString(Config.EVENTS_CASE) + " &c/event");
            p.sendMessage("&6Trafiles na: &cSkrzynie &7(1szt) &c+20");
            /*u.setExp(u.getExp() + 20);*/
            ChatUtil.giveItems(p, d);
        }
    }
}//TODO widac zjebie
