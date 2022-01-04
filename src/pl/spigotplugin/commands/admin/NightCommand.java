package pl.spigotplugin.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import pl.spigotplugin.api.Command;
import pl.spigotplugin.enums.RankType;

public class NightCommand extends Command {
    public NightCommand() { super("night", RankType.HELPER); }

    @Override
    public void onExecute(CommandSender p, String[] args) {
        for (World w : Bukkit.getWorlds()) {
            w.setTime(18000);
            w.setStorm(false);
            w.setThundering(false);
        }
    }
}
