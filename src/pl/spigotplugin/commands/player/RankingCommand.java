package pl.spigotplugin.commands.player;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.RandomUtil;

public class RankingCommand extends PlayerCommand {
    public RankingCommand() {super("ranking", "ranking <gracz>", "");}

    @Override
    public void onCommand(Player player, String[] args) {
        User u = null;
        if (args.length == 0 ) {
            u = UserManager.getUser(player);
        } else {
            u = UserManager.getUser(args[0]);
        }
        if (u == null) {
            player.sendMessage("&cGracz nie istnieje!");
        }
        if (u == null) {
            if (u.getName().equalsIgnoreCase(player.getName())) {
                player.sendMessage("&6Gracz posiada:");
                player.sendMessage("1 pozycje w rankingu");
                player.sendMessage("&c" + u.getPoints() + " rankingu");
                player.sendMessage("&c" + u.getKills() + " zabojstw");
            }
        }
        player.sendMessage("");
        player.sendMessage("&6Gracz &c"+u.getName()+ " &6posiada:");
        player.sendMessage("1 pozycje w rankingu");
        player.sendMessage("&c"+u.getPoints()+" &6rankingu");
        player.sendMessage("&c"+u.getKills()+" &6zabojstw");
        player.sendMessage("");
        int plusRank = (int)(155.0 + (u.getPoints()) * -0.15);
        if (plusRank <= 0) {
            plusRank = RandomUtil.getRandInteger(7, 30);
        }
        int loseRank = plusRank / 7 * 3;
        player.sendMessage("&6Za zabicie tego gracza otrzymasz &a" +plusRank+ " &6a stracisz&c " +loseRank);
        player.sendMessage("");
    }
}
