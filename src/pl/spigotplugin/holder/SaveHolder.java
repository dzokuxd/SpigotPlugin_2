package pl.spigotplugin.holder;

import pl.spigotplugin.objects.guild.Guild;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SaveHolder {

    public static final Map<String, Guild> GUILDS = new ConcurrentHashMap<>();

}
