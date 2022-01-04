package pl.spigotplugin.commands.player;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.utils.CraftingUtil;

public class CraftingiCommand extends PlayerCommand {
    public CraftingiCommand() { super("craft",  RankType.GRACZ, "craftingi"); }

    @Override
    public void onCommand(Player p, String[] args) {
        CraftingUtil.openMenu(p);
    }
}
