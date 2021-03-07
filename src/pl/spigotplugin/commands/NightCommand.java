package pl.spigotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import pl.spigotplugin.api.Command;

public class NightCommand extends Command {
    public NightCommand() { super("night", "", ""); }

    @Override
    public void onExecute(CommandSender p, String[] args) {
        for (World w : Bukkit.getWorlds()) {
            w.setTime(18000);
        }
    }
}
