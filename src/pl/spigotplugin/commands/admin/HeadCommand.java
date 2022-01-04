package pl.spigotplugin.commands.admin;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.ItemUtil;

public class HeadCommand extends PlayerCommand {
    public HeadCommand() { super("head", RankType.MOD);
    }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length == 0) {
            ItemUtil.giveItems(p, ItemUtil.getPlayerHead(p.getName()));
            p.updateInventory();
            p.sendMessage(ChatUtil.color("&aOtrzymales swoja glowe!"));
            return;
        }
        ItemUtil.giveItems(p, ItemUtil.getPlayerHead(args[0]));
        p.sendMessage(ChatUtil.color("&fOtrzymales glowe gracza &d" + args[0]));
    }
}
