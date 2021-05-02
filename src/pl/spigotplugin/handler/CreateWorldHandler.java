package pl.spigotplugin.handler;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

public class CreateWorldHandler {
    public static void handleCreateWorld(String name) {
        WorldCreator worldCreator = new WorldCreator(name);
        worldCreator.type(WorldType.FLAT);
        worldCreator.generateStructures(false);
        worldCreator.environment(World.Environment.NORMAL);
        worldCreator.createWorld();
    }
}

