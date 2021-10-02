package pl.spigotplugin.commands.premium;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import pl.spigotplugin.SpigotPlugin;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.TagUtil;

public class IncognitoCommand extends PlayerCommand {

    public IncognitoCommand() {super("incognito", "/incognito", "");}

    @Override
    public void onCommand(Player p, String[] args) {
        User u = UserManager.getUser(p);
        u.setIncognito(!u.isIncognito());
        TagUtil.updateBoard(p);

        GameProfile gameProfile = ((CraftPlayer) p).getProfile();
        gameProfile.getProperties().removeAll("textures");

        String value = "ewogICJ0aW1lc3RhbXAiIDogMTU4ODcxNzc1NDk0NywKICAicHJvZmlsZUlkIiA6ICIyYTkxYWIzYTI3NjU0ZmNkYjA1ZWRhMTRmNzA5ZGU2OCIsCiAgInByb2ZpbGVOYW1lIiA6ICJkem9rdiIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS83OGNhNDRlMzczMDJjZDM2ZmI5N2UyOWJjZGI0OGE0MmQxM2NhMDQzMDZhNThhODQ3ZmFlYjhkOTYxMDBlZmE3IgogICAgfQogIH0KfQ==";
        String signature = "prf+juB8tFg3dZNFfMp61P8YlBxYcpG1Z7eiSqikIOjBy8SoJjeqF+PuX+EyNZ8j3Qna8kIgLrBTLIzziFAz+nzEL+xAh8F75DMa0RwawBga9/2YMnuu9xYAgzmPq8vcvT3lAzLUausnmd7X6vFkTdVJg7u4SEX+6NNTuyHM3XAmpBurcgw5f6NqUhP0uOGkAqpVh7Rw5l4jaAUESG3cZClHvlBq4xL4Dsoy/H2xthE3C3ISgnu5AJIwgoD4ee4rBq7ZaZTK3+aHg6SYUBfXHHE6a1iM0k1hjWh0YtcHsTM6sPnXneH/buejrIbgV8+YYwr9Rfn2lY7iwYtsSU9RLBHBOfuBOvQbCibxZS7yBvlueBV08ghG0Z63A3wZ7FzWkGyqzFhTijG5gsn39ix89NhS9JOnNY55SwsXqX/BOG/mn7InNmcQBhulItStBtwvnSqLOrmPY6Ib12jbwJVq8deKvR18Y/2lMxmKWJZyYYvg2qWVds1+yATQD/dfJSelS0LCZNguSPCu7JGd8tqEmsn+CA6h4Nb7KxEzHWl1sjPVQYjIeh+8GiAYf3VbteEr08j8Trbd3p8DC+CvtRD2WYDpRe+ntJWJrqMeD6d4kxwLkzujnEQLIVWka2fdXqrO7emMK5a/qyT8v3CUeuNAthqgIOmuFiqCnmL0WRVCpEE=";

        gameProfile.getProperties().put("textures", new Property("textures", value, signature));

        Bukkit.getScheduler().runTaskLater(SpigotPlugin.getPlugin(), () -> {
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.hidePlayer(p);
            }

        }, 1);

        Bukkit.getScheduler().runTaskLater(SpigotPlugin.getPlugin(), () -> {
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.showPlayer(p);
            }

        }, 20);

        p.sendMessage((u.isIncognito() ? "&7\u00bb &6Tryb incognito zostal: &aWlaczony" : "&7\u00bb &6Tryb incognito zostal: &cWylaczony"));
    }
}
