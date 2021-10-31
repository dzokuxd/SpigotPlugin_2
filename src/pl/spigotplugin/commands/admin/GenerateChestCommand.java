package pl.spigotplugin.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.utils.RandomUtil;

public class GenerateChestCommand extends PlayerCommand {
    public GenerateChestCommand() {super("generatechest", "generatechest <ilosc>", "spigot.generatechest");}

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length != 1) {
            p.sendMessage("&c/generatechest <ilosc>");
            return;
        }
        int i;
        try {
            i = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException e) {
            p.sendMessage("&c/generatechest <ilosc>");
            return;
        }
        int border = (int) Bukkit.getWorld("world").getWorldBorder().getSize() / 2;
        for (int j = 0; j < i; ++j) {
            int x = RandomUtil.getRandInteger(-border, border);
            int z = RandomUtil.getRandInteger(-border, border);
            this.generatechest(x, z);
            Bukkit.broadcastMessage((String.format("&aNa mapie pojowil sie magiczny drop na: X: %d, Z: %d", x, z)));
        }
    }

    private void generatechest(int x, int z) {
        Location location = Bukkit.getWorld("world").getHighestBlockAt(x, z).getLocation();
        if (!location.getChunk().isLoaded()) {
            location.getWorld().loadChunk(x, z);
        }
        location.setY(location.getY() - 1.0);
        Block block = location.getBlock();
        if (!block.isLiquid() && (block.getType() == Material.GRASS || block.getType() == Material.SAND)) {
            Block relative = block.getRelative(0, 1, 0);
            relative.setType(Material.SPONGE);
        }
    }
}
