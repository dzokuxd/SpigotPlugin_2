package pl.spigotplugin.commands.player;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.utils.ChatUtil;

import java.util.HashMap;
import java.util.UUID;

public class ReplyCommand extends PlayerCommand {
    public ReplyCommand() { super("reply", RankType.GRACZ, "r"); }

    private static HashMap<UUID, Long> times = new HashMap<>();

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length != 1) {
            core.usage(p, getUsage());
            return;
        }
        UUID last = MsgCommand.getLastMsg().get(p.getUniqueId());
        if (last == null) {
            p.sendMessage(ChatUtil.color("&cNie masz komu odpisac!"));
            return;
        }
        Player o = Bukkit.getPlayer(last);
        if (o == null) {
            p.sendMessage(ChatUtil.color("&cGracz nie jest online!"));
            return;
        }
        Long t = ReplyCommand.times.get(p.getUniqueId());
        if (t != null && System.currentTimeMillis() - t < 3000L) {
            p.sendMessage(ChatUtil.color("&4Nie spamuj!"));
        }
        ReplyCommand.times.put(p.getUniqueId(), System.currentTimeMillis());
        String message = ChatColor.stripColor(ChatUtil.color(StringUtils.join(args, " ")));
        MsgCommand.getLastMsg().put(p.getUniqueId(), o.getUniqueId());
        MsgCommand.getLastMsg().put(o.getUniqueId(), p.getUniqueId());
        p.sendMessage(ChatUtil.color("&9Ja → " + o.getName() + "&9: &7" + message));
        ChatUtil.sendHoverMessageCommand(o, "&9" + p.getName() + " → Ja: &7" + message, "&8(&fKliknij, aby odpisac&8)", "/msg " + p.getName());
    }
}
