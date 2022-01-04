package pl.spigotplugin.commands.player;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.managers.TabManager;
import pl.spigotplugin.managers.TopsManager;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.RandomUtil;

public class RankingCommand extends PlayerCommand {
    public RankingCommand() {super("ranking", RankType.GRACZ);}

    @Override
    public void onCommand(Player player, String[] args) {
        User u = null;
        if (args.length == 0) {
            u = UserManager.getUser(player);
        } else {
            u = UserManager.getUser(args[0]);
        }
        if (u == null) {
            player.sendMessage(ChatUtil.color("&cGracz nie istnieje!"));
        }
        if (u == null) {
            if (u.getName().equalsIgnoreCase(player.getName())) {
                player.sendMessage(ChatUtil.color("&fPosiadasz:"));
                player.sendMessage(ChatUtil.color("&d " + TopsManager.getPlaceUser(u) + " &fpozycja w rankingu"));
                player.sendMessage(ChatUtil.color("&d" + u.getPoints() + " &frankingu"));
                player.sendMessage(ChatUtil.color("&d" + u.getKills() + " &fzabojstwa"));
            }else {
                player.sendMessage("");
                player.sendMessage(ChatUtil.color("&fGracz &d"+u.getName()+ " &fposiada:"));
                player.sendMessage(ChatUtil.color("&d "+ TopsManager.getPlaceUser(u)+ " &fpozycja w rankingu"));
                player.sendMessage(ChatUtil.color("&d"+u.getPoints()+" &frankingu"));
                player.sendMessage(ChatUtil.color("&d"+u.getKills()+" &fzabojstwa"));
                player.sendMessage("");
                int plusRank = (int)(155.0 + (u.getPoints()) * -0.15);
                if (plusRank <= 0) {
                    plusRank = RandomUtil.getRandInteger(7, 30);
                }
                int loseRank = plusRank / 7 * 3;
                player.sendMessage(ChatUtil.color("&fZa zabicie tego gracza otrzymasz &a" +plusRank+ " &fa stracisz&c " +loseRank));
            }
        }
    }
}
