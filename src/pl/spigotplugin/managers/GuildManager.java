package pl.spigotplugin.managers;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCClickEvent;
import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import pl.spigotplugin.SpigotPlugin;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.SpaceUtil;
import pl.spigotplugin.utils.TagUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    public static Guild getGuildByLoc(Location loc) {
        for (Guild g : GuildManager.guilds.values()) {
            if (g.getRegion().isInCuboidByLoc(loc)) {
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
                TopsManager.guildRankings.add(g);
                spawnNpc(g);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static final Object SYNCHRONIZE = new Object();

    private static void spawnNpc(Guild guild) {
        synchronized (SYNCHRONIZE) {
            NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "&6Gildia: &c" +guild.getTag());
            npc.setProtected(true);
            npc.data().set(NPC.PLAYER_SKIN_UUID_METADATA, guild.getLeader());
            Location location = guild.getRegion().getLocation().clone();
            npc.spawn(location);
            guild.id = npc.getId();
        }
    }

    public static Guild createGuild(String tag, String name, Player owner, Location home) {
        Guild g = new Guild(tag, name, owner, home);

        guilds.put(tag, g);
        TopsManager.guildRankings.add(g);
        Bukkit.getScheduler().runTask(SpigotPlugin.getPlugin(), () -> createRoomGuild(g,owner));
        return g;
    }

    public static void deleteGuild(Guild g) {
        Bukkit.getScheduler().runTask(SpigotPlugin.getPlugin(), () -> deleteRoom(g));

        for (String memberName : g.getMembers()) {
            User memberUser = UserManager.getUser(memberName);
            memberUser.setGuild("");
        }

        guilds.remove(g.getTag());
        TopsManager.guildRankings.remove(g);
        SpigotPlugin.getMySQL().update("DELETE FROM `{P}guilds` WHERE `tag` = '" + g.getTag() + "'");
        SpigotPlugin.getMySQL().update("DELETE FROM `{P}savedGuilds` WHERE `tag` = '" + g.getTag() + "'");
        for (String aly : g.getAlly()) {
            Guild a = GuildManager.getGuild(aly);
            if (a != null) {
                a.removeAlly(aly);
            }
        }
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
        NPC byId = CitizensAPI.getNPCRegistry().getById(g.id);
        byId.destroy();
    }

    private static void createRoomGuild(Guild g, Player owner) {
        Location c = g.getRegion().getLocation().clone();
        c.setY(29.0);
        for (Location loc : SpaceUtil.getSquare(c, 1, 0)) {
            loc.getBlock().setType(Material.OBSIDIAN);
        }
        c.getBlock().setType(Material.BEDROCK);
        c.setY(30.0);
        for (Location loc : SpaceUtil.getSquare(c, 1, 0)) {
            loc.getBlock().setType(Material.AIR);
        }
        c.setY(31);
        spawnNpc(g);
        owner.teleport(c);
        for (Location loc : SpaceUtil.getSquare(c, 1, 1)) {
            loc.getBlock().setType(Material.AIR);
        }
    }

}
