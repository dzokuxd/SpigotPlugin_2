package pl.spigotplugin.tasks;

import pl.spigotplugin.holder.SaveHolder;
import pl.spigotplugin.objects.guild.Guild;

public class SaveTask implements Runnable {
    @Override
    public void run() {
        for (Guild value : SaveHolder.GUILDS.values()) {
            value.save();
        }
        SaveHolder.GUILDS.clear();
    }
}
