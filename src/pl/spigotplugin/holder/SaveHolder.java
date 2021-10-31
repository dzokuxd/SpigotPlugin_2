package pl.spigotplugin.holder;

import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.objects.guild.GuildWar;
import pl.spigotplugin.objects.user.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SaveHolder {

    public static final Map<String, Guild> GUILDS = new ConcurrentHashMap<>();

    public static final Map<String, GuildWar> GUILDSWAR = new ConcurrentHashMap<>();

    public static final Map<String, User> USERS = new ConcurrentHashMap<>();

}
