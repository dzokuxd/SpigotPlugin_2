package pl.spigotplugin.protocoltab;

import org.bukkit.entity.Player;
import pl.spigotplugin.SpigotPlugin;
import pl.spigotplugin.protocoltab.manager.ProtocolTab;

import java.util.UUID;

public class ProtocolTabAPI {
    public static ProtocolTab getTablist(Player player) {
        return SpigotPlugin.getPlugin().getManager().getTablist(player);
    }

    public static ProtocolTab getTablist(UUID uuid) {
        return SpigotPlugin.getPlugin().getManager().getTablist(uuid);
    }
}
