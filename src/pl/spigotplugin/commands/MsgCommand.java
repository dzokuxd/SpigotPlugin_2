package pl.spigotplugin.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.GlobalMessage;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.ChatUtil;

import java.util.HashMap;
import java.util.UUID;

public class MsgCommand extends PlayerCommand {
    public MsgCommand() { super("msg", "msg <gracz> <wiadomosc>", "", "tell"); }

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
        if (args.length != 1) {
            GlobalMessage.usage(p, getUsage());
            return;
        }
        Player o = Bukkit.getPlayer(args[0]);
        if (o == null) {
            p.sendMessage("&4Blad: &cGracz nie jest online!");
            return;
        }
        User user = UserManager.getUser(args[0]);
        if (user != null) {
            if (!user.isPrivateMessages()) {
                p.sendMessage("&4Blad: &cTen gracz ma wylaczone prywatne wiadomosci.");
                return;
            }
        }
        if (user != null) {
            if (user.isIgnoreTell(p) && !p.hasPermission("core.tell.ignore")) {
                p.sendMessage("&4Blad: &cTen gracz zablokowal od Ciebie prywatne wiadomosci!");
                return;
            }
        }
        String nickja = args[0];
        if (nickja.equalsIgnoreCase(p.getName())) {
            p.sendMessage("&4Blad: &cNie mozesz pisac sam do siebie! ;(");
            return;
        }
        Long t = MsgCommand.times.get(p.getUniqueId());
        if (t != null && System.currentTimeMillis() - t < 3000L) {
            p.sendMessage("&4Nie spamuj!");
            return;
        }
        String message = ChatColor.stripColor(ChatUtil.color(StringUtils.join(args, " ", 1, args.length)));
        MsgCommand.lastMsg.put(p.getUniqueId(), o.getUniqueId());
        MsgCommand.lastMsg.put(o.getUniqueId(), p.getUniqueId());
        MsgCommand.times.put(p.getUniqueId(), System.currentTimeMillis());
        p.sendMessage("&9Ja → " + o.getName() + "&9: &7" + message);
        ChatUtil.sendHoverMessageCommand(o, "&9" + p.getName() + " → Ja: &7" + message, "&8(&fKliknij, aby odpisac&8)", "/msg " + p.getName());
    }
}
