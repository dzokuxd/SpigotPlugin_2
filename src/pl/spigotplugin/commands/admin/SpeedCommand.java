package pl.spigotplugin.commands.admin;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.utils.ChatUtil;

public class SpeedCommand extends PlayerCommand {
    public SpeedCommand() { super("speed", RankType.HELPER); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length < 1) {
            core.usage(p, "speed <1-10>");
            return;
        }
        float speed = Float.parseFloat(args[0]);
        if (speed > 10) {
            p.sendMessage(ChatUtil.color(core.FLY_POPRAW));
            return;
        } else if (speed < 1) {
            p.sendMessage(ChatUtil.color(core.FLY_POPRAW));
            return;
        }
        float finalSpeed = speed / 10;
        p.setFlySpeed(finalSpeed);
        p.sendMessage(ChatUtil.color(core.FLY_SPEED.replace("{SPEEDFLY}", String.valueOf(finalSpeed))));
    }
}