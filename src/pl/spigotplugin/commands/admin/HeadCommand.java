package pl.spigotplugin.commands.admin;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.utils.ItemUtil;

public class HeadCommand extends PlayerCommand {
    public HeadCommand() { super("head", "head <gracz>", "spigot.head");
    }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length == 0) {
            ItemUtil.giveItems(p, ItemUtil.getPlayerHead(p.getName()));
            p.updateInventory();
            p.sendMessage("&aOtrzymales swoja glowe!");
            return;
        }
        ItemUtil.giveItems(p, ItemUtil.getPlayerHead(args[0]));
        p.sendMessage("&fOtrzymales glowe gracza &d" + args[0]);
    }
}
