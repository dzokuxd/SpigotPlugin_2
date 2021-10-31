package pl.spigotplugin.objects.guild;

import org.bukkit.Location;

public class GuildWar {

    private final String tag;
    private int points = 0;
    private int kills = 0;
    private int deaths = 0;
    private Location home;

    public GuildWar(String tag, Location home) {
        this.tag = tag;
        this.home = home;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void setHome(Location home) {
        this.home = home;
    }

    public String getTag() {
        return tag;
    }

    public int getPoints() {
        return points;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public Location getHome() {
        return home;
    }

}
