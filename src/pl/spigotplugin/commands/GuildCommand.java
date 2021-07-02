package pl.spigotplugin.commands;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.configs.GlobalMessage;
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.objects.guild.Guild;

public class GuildCommand extends PlayerCommand {
    public GuildCommand() { super("gildie", "gildie", "","g"); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length < 1) {
            GlobalMessage.usage(p, getUsage());
            return;
        }
        switch (args[0].toLowerCase()){
            case "zaloz": {
                if (args.length !=3) {
                    p.sendMessage("&7Prawidlowe uzycie: &c/g zaloz <tag> <pelna nazwa>");
                    return;
                }
                if (!p.hasPermission("spigotplugin.manage") && !Config.MANAGE_GUILDCREATE) {
                    p.sendMessage("&cZakladanie gildii jest tymczasowo wylaczone!");
                    return;
                }
                Guild guild = GuildManager.getGuild(p);
                if (guild !=null)  {
                    p.sendMessage("&cPosiadasz juz gildie!");
                    return;
                }
                String tag = args[1].toUpperCase();
                String name = args[2];
                if (tag.length() >5 || tag.length() <2 || name.length() > 32 || name.length() <4){
                    p.sendMessage("&cTag gildi musi zawierac 2-5 zankow, nawzwa 4-32 znakow");
                    return;
                }
                if (GuildManager.getGuild(tag) !=null) {
                    p.sendMessage("&cIstenieje juz gildia o takim tagu!");
                    return;
                }
                break;
            }
            case "zapros": {
                p.sendMessage("jestes idiota");
                break;
            }
            case "wyrzuc": {
break;
            }
            case "itemy": {

            }
            case "usun": {

            }
            case "dolacz": {

            }
            case "dom": {

            }
            case "ustawdom": {

            }
            case "lider": {

            }
            case "zastepca": {

            }
            case "odnow": {

            }
            case "opusc": {

            }
            case "pvp": {

            }
            case "panel": {

            }
            case "regeneruj": {

            }
            case "zapisz": {

            }
            case "lista": {

            }
            case "wojny": {

            }
            case "sojusz": {

            }
        }
    }
}
