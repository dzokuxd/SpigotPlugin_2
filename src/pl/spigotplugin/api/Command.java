package pl.spigotplugin.api;

import org.bukkit.command.CommandSender;

import java.util.Arrays;

public abstract class Command extends org.bukkit.command.Command
{
    private String name;
    private String usage;
    private String permission;

    public Command(String name, String usage, String permission, String... aliases) {
        super(name, "", usage, Arrays.asList(aliases));
        this.name = name;
        this.usage = usage;
        this.permission = permission;
    }

    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!this.permission.isEmpty() && !sender.hasPermission(this.permission)) {
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

    public String getUsage() {
        return this.usage;
    }

    public String getPermission() {
        return this.permission;
    }
}
