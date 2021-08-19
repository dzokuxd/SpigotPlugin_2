package pl.spigotplugin.commands.player;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.menu.AchievmentMenu;

public class AchievementCommand extends PlayerCommand {
    public AchievementCommand() { super("osiagniecia", "os", "", "os"); }

    @Override
    public void onCommand(Player p, String[] args) {
        AchievmentMenu.open(p);
    }
}
