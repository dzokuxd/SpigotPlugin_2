package pl.spigotplugin.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.spigotplugin.SpigotPlugin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class AntyMacroListener extends PacketAdapter implements Listener {

    private static final PacketType USE_ENTITY;
    private Map<Player, PlayerCheatData> playerData;

    public AntyMacroListener(SpigotPlugin plugin) {
        super(params(plugin, USE_ENTITY).optionAsync());
        this.playerData = new ConcurrentHashMap<>();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        this.playerData.remove(event.getPlayer());
    }

    public void onPacketSending(PacketEvent event) {
    }

    public void onPacketReceiving(PacketEvent event) {
        PacketType type = event.getPacketType();
        Player p = event.getPlayer();
        PlayerCheatData cheatData = this.playerData.get(p);
        if (cheatData == null) {
            cheatData = new PlayerCheatData();
            this.playerData.put(p, cheatData);
            return;
        }
        if (type == USE_ENTITY && event.getPacket().getEntityUseActions().read(0) != EnumWrappers.EntityUseAction.ATTACK) {
            return;
        }
        long now = System.currentTimeMillis();
        long delta = now - cheatData.lastPacketTime;
        cheatData.lastPacketTime = now;
        if (now < cheatData.canceledUntil) {
            event.setCancelled(true);
            return;
        }
        if (delta < 66L) {
            cheatData.invalidPackets++;
            if (cheatData.invalidPackets > 30) {
                event.setCancelled(true);
                cheatData.canceledUntil = now + TimeUnit.SECONDS.toMillis(2L);
                p.sendMessage("&8» &6Posiadasz za szybkie macro &7(&c13&7)");
                p.sendMessage("&8» &6Zablokowano bicie na &c2 sekundy");
                p.sendMessage("&8» &6Optymalne: 10-12 CPS!");
            }
        }
        else if (cheatData.invalidPackets > 0) {
            cheatData.invalidPackets--;
        }
    }

    static {
        USE_ENTITY = PacketType.Play.Client.USE_ENTITY;
    }

    private static class PlayerCheatData
    {
        private long lastPacketTime;
        private int invalidPackets;
        private long canceledUntil;
    }
}
