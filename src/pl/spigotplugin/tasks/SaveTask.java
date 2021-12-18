package pl.spigotplugin.tasks;

import pl.spigotplugin.holder.SaveHolder;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.objects.user.User;

public class SaveTask implements Runnable {
    @Override
    public void run() {
        for (Guild value : SaveHolder.GUILDS.values()) {
            value.save();
        }
        for (User value : SaveHolder.USERS.values()) {
            value.save();
        }
        SaveHolder.GUILDS.clear();
        SaveHolder.USERS.clear();

    }
}
