package pl.spigotplugin.helper;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Material;

import java.util.concurrent.ConcurrentHashMap;

public class JSONHelper {
    public static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

    public static String dropsToJsonString(ConcurrentHashMap<Material, Integer> drops) {
        return GSON.toJson(drops);
    }

    public static ConcurrentHashMap<Material, Integer> jsonStringToDrops(String str) {
        return GSON.fromJson(str, new TypeToken<ConcurrentHashMap<Material, Integer>>() {}.getType());
    }
}