package pl.spigotplugin.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.ItemUtil;

public class GiveCommand extends PlayerCommand {
    public GiveCommand() { super("give", "give <gracz> <id[:base]> [ilosc]", "spigot.give"); }

    @Override
    public void onCommand(Player sender, String[] args) {
        if (args.length < 2) {
            core.usage(sender, getUsage());
            return;
        }
        Player p = Bukkit.getPlayer(args[0]);
        String[] datas = args[1].split(":");
        Material m = ItemUtil.getMaterial(datas[0]);
        short data = 0;
        if (datas.length > 1) {
            data = Short.parseShort(datas[1]);
        }
        ItemStack item = null;
        if (p == null) {
            sender.sendMessage("&cGracz jest offline");
            return;
        }
        if (m == null) {
            sender.sendMessage("&cNazwa lub ID przedmiotu jest bledne!");
            return;
        }
        if (args.length == 2) {
            item = new ItemStack(m, 1, data);
        } else if (args.length == 3) {
            item = new ItemStack(m, ChatUtil.isInteger(args[2]) ? Integer.parseInt(args[2]) : 1, data);
        }
        if (item == null) {
            sender.sendMessage("&cWystapil blad podczas dawania przedmiotu!");
            return;
        }
        ItemUtil.giveItems(p, item);
        p.updateInventory();
        sender.sendMessage("&7\u00bb &fDales &d" + m.name() + "&7:&d" + data + " &7(&d" + item.getAmount() + "&7) &fgraczowi &d" + p.getName());
        sender.sendMessage("&7\u00bb &fOtrzymales &d" + m.name() + "&7:&d" + data + " &7(&d" + item.getAmount());
    }
}
