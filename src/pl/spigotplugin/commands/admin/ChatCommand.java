package pl.spigotplugin.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import pl.spigotplugin.api.Command;
import pl.spigotplugin.configs.statues;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.managers.ChatManager;
import pl.spigotplugin.utils.ChatUtil;

public class ChatCommand extends Command {
    public ChatCommand() {
        super("chat", "chat <cc|on|off|level|vip|slow>", "spigot.chat");
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            core.usage(sender, getUsage());
            return;
        }
        switch (args[0]) {
            case "off": {
                if (!ChatManager.disable) {
                    sender.sendMessage("&7Chat jest wylaczony!");
                    return;
                }
                ChatManager.disable = false;
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("           &d&l" + (statues.IP));
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("&7\u00bb &fChat zostal &cwylaczony");
                Bukkit.broadcastMessage("&7\u00bb &fPrzez &c" + sender.getName() + "");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");
                return;
            }
            case "on": {
                if (!ChatManager.enable) {
                    sender.sendMessage("&cChat jest juz wlaczony!");
                }
                ChatManager.enable = true;
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("           &d&l" + (statues.IP));
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("&7\u00bb &fChat zostal &cwlaczony");
                Bukkit.broadcastMessage("&7\u00bb &fPrzez &c" + sender.getName() + "");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");
                return;
            }
            case "vip": {
                ChatManager.vipChat = !ChatManager.vipChat;
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("           &d&l" + (statues.IP));
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("&7\u00bb &fChat dla vipow " + (ChatManager.vipChat ? "&awlaczony" : "&cwylaczony"));
                Bukkit.broadcastMessage("&7\u00bb &fPrzez &d" + sender.getName() + "");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");
                return;
            }
            case "slow": {
                if (args.length < 2) {
                    sender.sendMessage(("&c/chat slow <czas w sekundach>"));
                    return;
                }
                if (!ChatUtil.isInteger(args[1])) {
                    sender.sendMessage("&cTo nie jest liczba");
                    return;
                }
                int slow = Integer.parseInt(args[1]);
                ChatManager.SLOWMODE = slow;
                statues.saveLang();
                sender.sendMessage("&cUstawiles slow chatu na " + slow + " sekundy!");
                return;

            }
            case "cc": {
                for (int i = 0; i < 100; i++) {
                    Bukkit.broadcastMessage("");
                }
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("           &d&l" + (statues.IP));
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("&7\u00bb &fChat zostal &dwyczyszczony ");
                Bukkit.broadcastMessage("&7\u00bb &fPrzez &d" + sender.getName() + "");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");
                return;
            }
            case "level":
            case "lvl": {
                if (args.length < 2) {
                    sender.sendMessage(("&c/chat lvl <poziom>"));
                    return;
                }
                if (!ChatUtil.isInteger(args[1])) {
                    sender.sendMessage("&cTo nie liczba!");
                    return;
                }
                int i = Integer.parseInt(args[1]);
                statues.LVL = i;
                statues.saveLang();
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("           &d&l" + (statues.IP));
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("&7\u00bb &fChat zostal ustawiony na &d" + i + " &fpoziom ");
                Bukkit.broadcastMessage("&7\u00bb &fPrzez &d" + sender.getName() + "");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");
                return;
            }
            default: {
                core.usage(sender, getUsage());
            }
        }
    }
}
