package pl.spigotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.GlobalMessage;

public class GamemodeCommand extends PlayerCommand {
    public GamemodeCommand() { super("gamemode", "gamemode (gracz | tryb)", "", "gm");
    }

    @Override
    public void onCommand(Player p, String[] args) {
        switch (args.length) {
            case 1:{
                GameMode mode = getMode(args[0]);
                if (mode == null) {
                    p.sendMessage("&cNie odnaleziono tego trybu gry!");
                    return;
                }
                p.setGameMode(mode);
                p.sendMessage(GlobalMessage.MESSAGES_GAMEMODE.replace("{GAMEMODE}",mode.name()));
                break;
            }
            case 2:{
                GameMode mode = getMode(args[0]);
                if (mode == null) {
                    p.sendMessage("&cNie odnaleziono tego trybu gry!");
                    return;
                }
                Player x = Bukkit.getPlayer(args[1]);
                if (x == null) {
                    p.sendMessage("&cGracz jest offline");
                    return;
                }
                x.setGameMode(mode);
                x.sendMessage(GlobalMessage.MESSAGES_GAMEMODEPLAYER1.replace("{GAMEMODESTATUS}",mode.name().replace("{GAMEMODEPLAYER1}",p.getName())));
                p.sendMessage(GlobalMessage.MESSAGES_GAMEMODEPLAYER2.replace("{GAMEMODESTATUS}",mode.name().replace("{GAMEMODEPLAYER2}",x.getName())));
                break;
            }
            default: {
                GlobalMessage.usage(p, getUsage());
                break;
            }
        }
    }//TODO naprawa gmplayer

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
