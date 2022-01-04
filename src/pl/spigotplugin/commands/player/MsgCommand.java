package pl.spigotplugin.commands.player;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.GroupUtil;

import java.util.HashMap;
import java.util.UUID;

public class MsgCommand extends PlayerCommand {
    public MsgCommand() { super("msg", RankType.GRACZ, "tell"); }

    private static final HashMap<UUID, UUID> lastMsg;
    private static final HashMap<UUID, Long> times;

    static {
        lastMsg = new HashMap<>();
        times = new HashMap<>();
    }

    public static HashMap<UUID, UUID> getLastMsg() {
        return MsgCommand.lastMsg;
    }

    public static HashMap<UUID, Long> getTimes() {
        return MsgCommand.times;
    }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length < 2) {
            core.usage(p, "msg <gracz> <wiadomosc>");
            return;
        }
        Player o = Bukkit.getPlayer(args[0]);
        if (o == null) {
            p.sendMessage(ChatUtil.color("&cGracz nie jest online!"));
            return;
        }
        User user = UserManager.getUser(args[0]);
        if (user != null) {
            if (!user.isPrivateMessages()) {
                p.sendMessage(ChatUtil.color("&cTen gracz ma wylaczone prywatne wiadomosci."));
                return;
            }
        }
        if (user != null) {
            if (user.isIgnoreTell(p) && !GroupUtil.have(p, RankType.HELPER)) {
                p.sendMessage(ChatUtil.color("&cTen gracz zablokowal od Ciebie prywatne wiadomosci!"));
                return;
            }
        }
        String nickja = args[0];
        if (nickja.equalsIgnoreCase(p.getName())) {
            p.sendMessage(ChatUtil.color("&cNie mozesz pisac sam do siebie! ;("));
            return;
        }
        Long t = MsgCommand.times.get(p.getUniqueId());
        if (t != null && System.currentTimeMillis() - t < 3000L) {
            p.sendMessage(ChatUtil.color("&4Nie spamuj!"));
            return;
        }
        String message = ChatColor.stripColor(ChatUtil.color(StringUtils.join(args, " ", 1, args.length)));
        MsgCommand.lastMsg.put(p.getUniqueId(), o.getUniqueId());
        MsgCommand.lastMsg.put(o.getUniqueId(), p.getUniqueId());
        MsgCommand.times.put(p.getUniqueId(), System.currentTimeMillis());
        p.sendMessage(ChatUtil.color("&9Ja \u00BB " + o.getName() + "&9:" + message));
        ChatUtil.sendHoverMessageCommand(o, "&9" + p.getName() + " \u00BB Ja: &9" + message, "&8(&fKliknij, aby odpisac&8)", "/msg " + p.getName());
    }
}
