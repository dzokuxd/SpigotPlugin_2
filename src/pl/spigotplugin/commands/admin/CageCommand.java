package pl.spigotplugin.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.managers.CageManager;

public class CageCommand extends PlayerCommand {

    public CageCommand() { super("cage", "cage nick-nick diamond/iron", "spigot.cage"); }


    private final Location cage1 = new Location(Bukkit.getWorlds().get(0), 0.0, 70.0, 0.0);
    private final Location cage2 = new Location(Bukkit.getWorlds().get(0), 0.0, 70.0, 0.0);

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length < 2) {
            core.usage(p, getUsage());
            return;
        }
        Player target = Bukkit.getPlayerExact(args[0]);
        Player target2 = Bukkit.getPlayerExact(args[1]);
        if (target == null || target2 == null) {
            p.sendMessage("&cJeden z tych graczy jest offline.");
            return;
        }
        if (CageManager.getList().contains(target) || CageManager.getList().contains(target2)) {
         p.sendMessage("&cJeden z tych graczy juz walczy.");
         return;
        }
        String lowerCase = args[2].toLowerCase();
        switch (lowerCase) {
            case "diamond": {
                CageManager.prepareInventory(CageManager.type.DIAMOND, target.getInventory());
                CageManager.prepareInventory(CageManager.type.DIAMOND, target2.getInventory());
                CageManager.setup(target);
                CageManager.setup(target2);
                target.teleport(this.cage1);
                target2.teleport(this.cage2);
                CageManager.getList().add(target);
                CageManager.getList().add(target2);
                break;
            }
            case "iron": {
                CageManager.prepareInventory(CageManager.type.IRON, target.getInventory());
                CageManager.prepareInventory(CageManager.type.IRON, target2.getInventory());
                CageManager.setup(target);
                CageManager.setup(target2);
                target.teleport(this.cage1);
                target2.teleport(this.cage2);
                CageManager.getList().add(target);
                CageManager.getList().add(target2);
                break;
            }
            default: {
                p.sendMessage("&cDostepne argumenty: diamond,iron");
                break;
            }
        }
    }
}