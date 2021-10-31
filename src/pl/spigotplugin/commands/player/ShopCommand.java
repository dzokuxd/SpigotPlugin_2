package pl.spigotplugin.commands.player;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.statues;
import pl.spigotplugin.menu.ShopMenu;

public class ShopCommand extends PlayerCommand {
    public ShopCommand() { super("sklep", "sklep", ""); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (!statues.MANAGE_SHOP) {
            p.sendMessage("&cSklep jest aktualnie wylaczony!");
        }
        ShopMenu.show(p);
    }
}
