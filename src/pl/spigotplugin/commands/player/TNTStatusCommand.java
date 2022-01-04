package pl.spigotplugin.commands.player;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.guild;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.TNTUtil;

public class TNTStatusCommand extends PlayerCommand {
    public TNTStatusCommand() { super("tnt", RankType.GRACZ); }

    @Override
    public void onCommand(Player p, String[] args) {
        p.sendMessage(ChatUtil.color("&dTnT &fdziala w godzinach od:&d "+ guild.CUBOID_TNT_OD + " &fdo: &c" + guild.CUBOID_TNT_DO));
        p.sendMessage(ChatUtil.color("&dTnT &fwybucha ponizej: &d50 poziomu"));
        p.sendMessage(ChatUtil.color("&fStatus: " + (TNTUtil.isBetween() ? "&aWlaczone" : "&cWylaczone")));
    }
}
