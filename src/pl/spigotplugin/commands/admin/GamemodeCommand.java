package pl.spigotplugin.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.utils.ChatUtil;

public class GamemodeCommand extends PlayerCommand {
    public GamemodeCommand() { super("gamemode", RankType.MOD, "gm");
    }

    @Override
    public void onCommand(Player p, String[] args) {
        switch (args.length) {
            case 1:{
                GameMode mode = getMode(args[0]);
                if (mode == null) {
                    p.sendMessage(ChatUtil.color("&cNie odnaleziono tego trybu gry!"));
                    return;
                }
                p.setGameMode(mode);
                p.sendMessage(ChatUtil.color(core.GAMEMODE_GM.replace("{GAMEMODE}",mode.name())));
                break;
            }
            case 2:{
                GameMode mode = getMode(args[0]);
                if (mode == null) {
                    p.sendMessage(ChatUtil.color("&cNie odnaleziono tego trybu gry!"));
                    return;
                }
                Player x = Bukkit.getPlayer(args[1]);
                if (x == null) {
                    p.sendMessage(ChatUtil.color("&cGracz jest offline"));
                    return;
                }
                x.setGameMode(mode);
                x.sendMessage(ChatUtil.color(core.GAMEMODE_PLAYER.replace("{GAMEMODESTATUS}",mode.name()).replace("{GAMEMODEPLAYER}",p.getName())));
                p.sendMessage(ChatUtil.color(core.GAMEMODE_YOU.replace("{GAMEMODESTATUS}",mode.name()).replace("{GAMEMODEPLAYER}",x.getName())));
                break;
            }
            default: {
                core.usage(p, "gamemode <gracz> <tryb>");
                break;
            }
        }
    }

    private GameMode getMode(String args) {
        if (args.equalsIgnoreCase("1") || args.equalsIgnoreCase("creative") || args.equalsIgnoreCase("true")) {
            return GameMode.CREATIVE;
        } else if (args.equalsIgnoreCase("0") || args.equalsIgnoreCase("survival") || args.equalsIgnoreCase("false")) {
            return GameMode.SURVIVAL;
        } else if (args.equalsIgnoreCase("2") || args.equalsIgnoreCase("adventure")) {
            return GameMode.ADVENTURE;
        }
        return null;
    }
}
