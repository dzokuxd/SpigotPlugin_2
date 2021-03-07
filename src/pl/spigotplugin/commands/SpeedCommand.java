package pl.spigotplugin.commands;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.GlobalMessage;

public class SpeedCommand extends PlayerCommand {
    public SpeedCommand() { super("speed", "/speed (1-10)", ""); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length < 1) {
            GlobalMessage.usage(p, getUsage());
            return;
        }
        float speed = Float.parseFloat(args[0]);
        if (speed > 10) {
            p.sendMessage(GlobalMessage.MESSAGES_SPEED);
            return;
        } else if (speed < 1) {
            p.sendMessage(GlobalMessage.MESSAGES_SPEED);
            return;
        }
        float finalSpeed = speed / 10;
        p.setFlySpeed(finalSpeed);
        p.sendMessage(GlobalMessage.MESSAGES_SPEEDFLY.replace("{SPEEDFLY}", String.valueOf(finalSpeed)));
    }
}