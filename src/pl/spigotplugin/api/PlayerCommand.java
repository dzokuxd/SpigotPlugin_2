package pl.spigotplugin.api;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class PlayerCommand extends Command
{
    public PlayerCommand(String name, String usage, String permission, String... aliases) {
        super(name, usage, permission, aliases);
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("&cTej komendy nie mozesz uzyc z poziomu konsoli!");
            return;
        }
       this.onCommand((Player)sender, args);
    }

    public abstract void onCommand(Player p0, String[] p1);
}
