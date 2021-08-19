package pl.spigotplugin.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.SpigotPlugin;
import pl.spigotplugin.configs.GuildConfig;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.SpaceUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GuildManager {
    private static final Map<String, Guild> guilds = new ConcurrentHashMap<>();

    public static Guild getGuild(String str) { return guilds.get(str.toUpperCase()); }

    public static Map<String, Guild> getGuilds() {
        return guilds;
    }

    public static Guild getGuild(Player p) {
        for (Guild g : GuildManager.guilds.values()) {
            if (g.isMember(p.getName())) {
                return g;
            }
        }
        return null;
    }

    public static void loadGuilds() {
        try {
            ResultSet rs = SpigotPlugin.getMySQL().query("SELECT * FROM `{P}guilds`");
            while (rs.next()) {
                Guild g = new Guild(rs);
                GuildManager.guilds.put(g.getTag(), g);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean canCreateGuildBySpawn(Location loc) {
        int spawnX = loc.getWorld().getSpawnLocation().getBlockX();
        int spawnZ = loc.getWorld().getSpawnLocation().getBlockZ();
        return Math.abs(loc.getBlockX() - spawnX) >= 250 || Math.abs(loc.getBlockZ() - spawnZ) >= 250;
    }

    public static Guild createGuild(String tag, String name, Player owner, Location home) {
        Guild g = new Guild(tag, name, owner, home);

        guilds.put(tag, g);//TODO xd
        /*RankingManager.addRanking(g);*/
        Bukkit.getScheduler().runTask(SpigotPlugin.getPlugin(), () -> createRoomGuild(g,owner));
        return g;
    }

    public static void deleteGuild(Guild g) {
        //RankingManager.removeRanking(g);
        Bukkit.getScheduler().runTask(SpigotPlugin.getPlugin(), () -> deleteRoom(g));

        for (String memberName : g.getMembers()) {
            User memberUser = UserManager.getUser(memberName);
            memberUser.setGuild("");
        }

        guilds.remove(g.getTag());
        SpigotPlugin.getMySQL().update("DELETE FROM `{P}guilds` WHERE `tag` = '" + g.getTag() + "'");
        //SpigotPlugin.getMySQL().update("DELETE FROM `{P}savedGuilds` WHERE `tag` = '" + g.getTag() + "'");
        /*for (String aly : g.getAlly()) {
            Guild a = GuildManager.getGuild(aly);
            if (a != null) {
                a.removeAlly(g.getTag());
            }
        }*/
    }

    public static Guild getGuild(Location loc) {
        for (Guild g : GuildManager.guilds.values()) {
            if (g.getRegion().isInCuboid(loc)) {
                return g;
            }
        }
        return null;
    }

    private static void deleteRoom(Guild g) {
        Location c = g.getRegion().getLocation().clone();
        c.setY(30);
        c.getBlock().setType(Material.AIR);
        c.setY(29);
        c.getBlock().setType(Material.AIR);
        //NPC byId = CitizensAPI.getNPCRegistry().getById(g.id);
        //byId.destroy();
    }

    private static void createRoomGuild(Guild g, Player owner) {
        Location c = g.getRegion().getLocation().clone();
        c.setY(29.0);
        for (Location loc : SpaceUtil.getSquare(c, 2, 0)) {
            loc.getBlock().setType(Material.OBSIDIAN);
        }
        c.getBlock().setType(Material.BEDROCK);
        c.setY(30.0);
        for (Location loc : SpaceUtil.getSquare(c, 1, 0)) {
            loc.getBlock().setType(Material.AIR);
        }
        /*NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "�6Gildia: �c"+g.getTag());
        npc.setProtected(true);
        npc.data().set(NPC.PLAYER_SKIN_UUID_METADATA, owner.getName());
        Location location = owner.getLocation();
        location.setYaw(-135.0f);
        location.setPitch(-1.0f);
        npc.spawn(location);
        g.id = npc.getId();*/
        c.setY(31);
        owner.teleport(c);
        for (Location loc : SpaceUtil.getSquare(c, 1, 1)) {
            loc.getBlock().setType(Material.AIR);
        }
    }

}
