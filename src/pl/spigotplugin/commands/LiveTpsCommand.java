package pl.spigotplugin.commands;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;

import java.util.ArrayList;
import java.util.List;

public class LiveTpsCommand extends PlayerCommand {
    public LiveTpsCommand() {
        super("livetps", "livetps", "");
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
