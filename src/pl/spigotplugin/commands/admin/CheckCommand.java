package pl.spigotplugin.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.statues;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.menu.CheckMenu;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.CheckUtil;

public class CheckCommand extends PlayerCommand {
    public CheckCommand() { super("check", RankType.HELPER);
    }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length < 1) {
            p.sendMessage(ChatUtil.color("&cPrawidlowe uzycie:"));
            p.sendMessage(ChatUtil.color("&c/sprawdz [nick]"));
            p.sendMessage(ChatUtil.color("&c/sprawdz czysty [nick]"));
            p.sendMessage(ChatUtil.color("&c/sprawdz czity [nick]"));
            return;
        }
        if (args[0].equalsIgnoreCase("czysty")) {
            if (args.length != 2) {
                p.sendMessage(ChatUtil.color("&cPrawidlowe uzycie:"));
                p.sendMessage(ChatUtil.color("&c/sprawdz [nick]"));
                p.sendMessage(ChatUtil.color("&c/sprawdz czysty [nick]"));
                p.sendMessage(ChatUtil.color("&c/sprawdz czity [nick]"));
                return;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                p.sendMessage(ChatUtil.color("&cGracz jest offline"));
                return;
            }
            if (!CheckUtil.checkedPlayers.contains(target)) {
                p.sendMessage(ChatUtil.color("&cTen gracz jest czysty"));
                return;
            }
            CheckUtil.checkedPlayers.remove(target);
            Bukkit.broadcastMessage("&7\u00bb &fGracz &d" + target.getName() + " &fzostal sprawdzony i nie posiadal cheatow!");
        } else if (args[0].equalsIgnoreCase("cheaty")) {
            if (args.length != 2) {
                p.sendMessage(ChatUtil.color("&cPrawidlowe uzycie:"));
                p.sendMessage(ChatUtil.color("&c/sprawdz [nick]"));
                p.sendMessage(ChatUtil.color("&c/sprawdz czysty [nick]"));
                p.sendMessage(ChatUtil.color("&c/sprawdz czity [nick]"));
                return;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                p.sendMessage(ChatUtil.color("&cPodana osoba jest offline."));
                return;
            }
            if (!CheckUtil.checkedPlayers.contains(target)) {
                p.sendMessage(ChatUtil.color("&cOsoba nie jest sprawdzany."));
                return;
            }
            CheckUtil.checkedPlayers.remove(target);
            Bukkit.broadcastMessage("&7\u00bb &fGracz &d" + target.getName() + " &fzostal sprawdzony i zostal zbanowany za cheaty!");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ("ban " + target.getName() + " Cheaty"));
        } else {
            if (args.length != 1) {
                return;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                p.sendMessage(ChatUtil.color("&cPodana osoba jest offline."));
                return;
            }
            if (CheckUtil.checkedPlayers.contains(target)) {
                p.sendMessage(ChatUtil.color("&cOsoba jest juz sprawdzana."));
                return;
            }
            CheckUtil.checkedPlayers.add(target);
            User user = UserManager.getUser(target);
            user.setInBeingChecked(true);
            Bukkit.broadcastMessage("&7\u00bb &fGracz &d" + target.getName() + "&fjest aktualnie sprawdzany przez: &d" + p.getName());
            Bukkit.broadcastMessage("&fCheaty: &dban");
            Bukkit.broadcastMessage("&fLogout: &dban");
            target.sendMessage("&c&lJestes aktualnie sprawdzany! Nie wylogowywuj sie z gry! Wejdz na TS3: &4&lts." + (statues.IP));
            CheckMenu.show(target);
        }
    }
}
