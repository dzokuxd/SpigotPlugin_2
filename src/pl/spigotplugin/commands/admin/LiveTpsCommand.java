package pl.spigotplugin.commands.admin;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.enums.RankType;

import java.util.ArrayList;
import java.util.List;

public class LiveTpsCommand extends PlayerCommand {
    public LiveTpsCommand() {
        super("livetps",  RankType.ADMIN);
    }

    public static final List<Player> using = new ArrayList<>();

    @Override
    public void onCommand(Player p, String[] args) {
        if (using.contains(p)) {
            using.remove(p);
        } else {
            using.add(p);
        }
    }
}
