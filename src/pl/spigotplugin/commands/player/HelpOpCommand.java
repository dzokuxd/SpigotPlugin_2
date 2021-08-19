package pl.spigotplugin.commands.player;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.GlobalMessage;
import pl.spigotplugin.utils.ChatUtil;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class HelpOpCommand extends PlayerCommand {
    public HelpOpCommand() { super("helpop", "helpop <wiadomosc>", ""); }

    private static Map<UUID, Long> times = new ConcurrentHashMap<>();


    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length < 1) {
            GlobalMessage.usage(p, getUsage());
            return;
        }
        Long t = HelpOpCommand.times.get(p.getUniqueId());
        if (t != null && System.currentTimeMillis() - t < 30000L) {
            p.sendMessage("&4Blad: &cNa Helpop mozesz pisac co 30 sekund!");
            return;
        }
        String message = ChatColor.stripColor(ChatUtil.color(StringUtils.join(args, " ")));
        for (Player po : Bukkit.getOnlinePlayers()) {
            if (po.hasPermission("spigotplugin.helpopsee")) {
                ChatUtil.sendHoverMessageCommand(po, "&4[HelpOP] &7" + p.getName() + " &8-> &7" + message, "&8(&fKliknij, aby sie przeteleportowac!&8)", "/tp " + p.getName());
            }
        }
        times.put(p.getUniqueId(), System.currentTimeMillis());
        p.sendMessage("&aWiadomosc wyslana!");
    }
}

