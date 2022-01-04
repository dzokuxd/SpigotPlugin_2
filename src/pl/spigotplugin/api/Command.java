package pl.spigotplugin.api;

import org.bukkit.command.CommandSender;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.utils.GroupUtil;

import java.util.Arrays;

public abstract class Command extends org.bukkit.command.Command
{
    private String name;
    private RankType permission;

    public Command(String name, RankType permission, String... aliases) {
        super(name, "", "xd", Arrays.asList(aliases));
        this.name = name;
        this.permission = permission;
    }

    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!GroupUtil.have(sender, this.permission)) {
            sender.sendMessage("&cNie masz dostepu!");
            return false;
        }
        this.onExecute(sender, args);
        return true;
    }

    public abstract void onExecute(CommandSender p0, String[] p1);

    public String getName() {
        return this.name;
    }
}
