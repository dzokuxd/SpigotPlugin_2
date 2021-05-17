package pl.spigotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.menu.CheckMenu;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.CheckUtil;

public class CheckCommand extends PlayerCommand {
    public CheckCommand() { super("check", "check nick \ncheck czysty nick \ncheck cheaty nick", "", "check");
    }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length < 1) {
            p.sendMessage("&7\u00bb &6Prawidlowe uzycie:");
            p.sendMessage("&c/sprawdz [nick]");
            p.sendMessage("&c/sprawdz czysty [nick]");
            p.sendMessage("&c/sprawdz czity [nick]");
            return;
        }
        if (args[0].equalsIgnoreCase("czysty")) {
            if (args.length != 2) {
                p.sendMessage("&7\u00bb &6Prawidlowe uzycie:");
                p.sendMessage("&c/sprawdz [nick]");
                p.sendMessage("&c/sprawdz czysty [nick]");
                p.sendMessage("&c/sprawdz czity [nick]");
                return;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                p.sendMessage("&4Blad: &cGracz jest offline");
                return;
            }
            if (!CheckUtil.checkedPlayers.contains(target)) {
                p.sendMessage("&4Blad: &cTen gracz jest czysty");
                return;
            }
            CheckUtil.checkedPlayers.remove(target);
            Bukkit.broadcastMessage("&7\u00bb &6Gracz &c" + target.getName() + " &6zostal sprawdzony i nie posiadal cheatow!");
        } else if (args[0].equalsIgnoreCase("cheaty")) {
            if (args.length != 2) {
                p.sendMessage("&7\u00bb &6Prawidlowe uzycie:");
                p.sendMessage("&c/sprawdz [nick]");
                p.sendMessage("&c/sprawdz czysty [nick]");
                p.sendMessage("&c/sprawdz czity [nick]");
                return;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                p.sendMessage("&4Blad: &cPodana osoba jest offline.");
                return;
            }
            if (!CheckUtil.checkedPlayers.contains(target)) {
                p.sendMessage("&4Blad: &cOsoba nie jest sprawdzany.");
                return;
            }
            CheckUtil.checkedPlayers.remove(target);
            Bukkit.broadcastMessage("&7\u00bb &6Gracz &c" + target.getName() + " &6zostal sprawdzony i zostal zbanowany za cheaty!");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ("ban " + target.getName() + " Cheaty"));
        } else {
            if (args.length != 1) {
                return;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                p.sendMessage("&4Blad &cPodana osoba jest offline.");
                return;
            }
            if (CheckUtil.checkedPlayers.contains(target)) {
                p.sendMessage("&4Blad: &cOsoba jest juz sprawdzana.");
                return;
            }
            CheckUtil.checkedPlayers.add(target);
            User user = UserManager.getUser(target);
            user.setInBeingChecked(true);
            Bukkit.broadcastMessage("&7\u00bb &6Gracz &c " + target.getName() + "&6jest aktualnie sprawdzany przez: &c" + p.getName());
            Bukkit.broadcastMessage("&7Cheaty: &cban");
            Bukkit.broadcastMessage("&7Logout: &cban");
            target.sendMessage("&c&lJestes aktualnie sprawdzany! Nie wylogowywuj sie z gry! Wejdz na TS3: &4&lts." + (Config.IP));
            CheckMenu.show(p);
        }
    }
}
