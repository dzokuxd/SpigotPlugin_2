package pl.spigotplugin.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import pl.spigotplugin.api.Command;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.configs.GlobalMessage;
import pl.spigotplugin.managers.ChatManager;
import pl.spigotplugin.utils.ChatUtil;

public class ChatCommand extends Command {
    public ChatCommand() {
        super("chat", "chat <cc|on|off|level|vip|slow>", "");
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            GlobalMessage.usage(sender, getUsage());
            return;
        }
        switch (args[0]) {
            case "off": {
                if (!ChatManager.enable) {
                    sender.sendMessage("&7cChat jest wylaczony!");
                    return;
                }
                ChatManager.enable = false;
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("           &c&l" + (Config.IP));
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("&7\u00bb &6Chat zostal &cwylaczony");
                Bukkit.broadcastMessage("&7\u00bb &6Przez &c" + sender.getName() + "");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");
                return;
            }
            case "on": {
                if (ChatManager.enable) {
                    sender.sendMessage("&cChat jest juz wlaczony!");
                }
                ChatManager.enable = true;
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("           &c&l" + (Config.IP));
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("&7\u00bb &6Chat zostal &cwlaczony");
                Bukkit.broadcastMessage("&7\u00bb &6Przez &c" + sender.getName() + "");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");
                return;
            }
            case "vip": {
                ChatManager.vipChat = !ChatManager.vipChat;
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("           &c&l" + (Config.IP));
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("&7\u00bb &6Chat dla vipow " + (ChatManager.vipChat ? "&awlaczony" : "&cwylaczony"));
                Bukkit.broadcastMessage("&7\u00bb &6Przez &c" + sender.getName() + "");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");
                return;
            }
            case "slow": {
                if (args.length < 2) {
                    sender.sendMessage(("/chat slow <czas w sekundach>"));
                    return;
                }
                if (!ChatUtil.isInteger(args[1])) {
                    sender.sendMessage("&cTo nie jest liczba");
                    return;
                }
                int slow = Integer.parseInt(args[1]);
                ChatManager.SLOWMODE = slow;
                Config.saveConfig();
                sender.sendMessage("&7\u00bb &6Ustawiles slow chatu na &c" + slow + " &6sekundy!");
                return;

            }
            case "cc": {
                for (int i = 0; i < 100; i++) {
                    Bukkit.broadcastMessage("");
                }
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("           &c&l" + (Config.IP));
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("&7\u00bb &6Chat zostal &cwyczyszczony ");
                Bukkit.broadcastMessage("&7\u00bb &6Przez &c" + sender.getName() + "");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");
                return;
            }
            case "level":
            case "lvl": {
                if (args.length < 2) {
                    sender.sendMessage(("/chat lvl <poziom>"));
                    return;
                }
                if (!ChatUtil.isInteger(args[1])) {
                    sender.sendMessage("&cTo nie liczba!");
                    return;
                }
                int i = Integer.parseInt(args[1]);
                Config.LVL = i;
                Config.saveConfig();
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("           &c&l" + (Config.IP));
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("&7\u00bb &6Chat zostal ustawiony na &c" + i + " &6poziom ");
                Bukkit.broadcastMessage("&7\u00bb &6Przez &c" + sender.getName() + "");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");
                return;
            }
            default: {
                GlobalMessage.usage(sender, getUsage());
            }
        }
    }
}
