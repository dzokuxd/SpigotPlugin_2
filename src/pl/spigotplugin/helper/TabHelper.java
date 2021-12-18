package pl.spigotplugin.helper;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.entity.Player;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.TabUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TabHelper {
    private static final ConcurrentHashMap<UUID, TabUtil> cache = new ConcurrentHashMap<>();

    public static boolean isTab(UUID uuid) {
        return TabHelper.cache.containsKey(uuid);
    }

    public static TabUtil getTab(UUID uuid) {
        return TabHelper.cache.get(uuid);
    }

    public static void executeCreate(Player player) {
        if (isTab(player.getUniqueId())) {
            return;
        }
        TabUtil t = new TabUtil(player);
        PacketContainer container = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        PacketContainer pc = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
        List<PlayerInfoData> list = new ArrayList<>();
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 20; ++j) {
                PlayerInfoData playerInfoData = new PlayerInfoData(t.getEntries()[i][j], 0, EnumWrappers.NativeGameMode.SURVIVAL, WrappedChatComponent.fromText(ChatUtil.color(t.getSlot()[i][j])));
                list.add(playerInfoData);
            }
        }
        EnumWrappers.PlayerInfoAction action = EnumWrappers.PlayerInfoAction.ADD_PLAYER;
        container.getPlayerInfoAction().write(0, action);
        container.getPlayerInfoDataLists().write(0, list);
        pc.getChatComponents().write(0, WrappedChatComponent.fromText(ChatUtil.color("&7&l&m---------- &7&l ( easyage.pl &7&l) &7&l&m----------"))).write(1, WrappedChatComponent.fromText(ChatUtil.color("&7&l&m---------- &7&l ( easyage.pl &7&l) &7&l&m----------")));
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, container);
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, pc);
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        TabHelper.cache.put(player.getUniqueId(), t);
    }

    public static void executeRemove(Player player) {
        if (!isTab(player.getUniqueId())) {
            return;
        }
        PacketContainer container = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        EnumWrappers.PlayerInfoAction action = EnumWrappers.PlayerInfoAction.REMOVE_PLAYER;
        container.getPlayerInfoAction().write(0, action);
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, container);
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        TabHelper.cache.remove(player.getUniqueId());
    }

    public static void update(Player player) {
        TabUtil t = getTab(player.getUniqueId());
        if (t == null) {
            return;
        }
        t.update(player);
        PacketContainer container = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        EnumWrappers.PlayerInfoAction action = EnumWrappers.PlayerInfoAction.UPDATE_DISPLAY_NAME;
        container.getPlayerInfoAction().write(0, action);
        List<PlayerInfoData> list = new ArrayList<PlayerInfoData>();
        for (int i = 0; i < 16; ++i) {
            list.add(new PlayerInfoData(t.getEntries()[0][i + 3], 0, EnumWrappers.NativeGameMode.SURVIVAL, WrappedChatComponent.fromText(ChatUtil.color(t.getSlot()[0][i + 3]))));
        }
        for (int i = 0; i < 16; ++i) {
            list.add(new PlayerInfoData(t.getEntries()[1][i + 3], 0, EnumWrappers.NativeGameMode.SURVIVAL, WrappedChatComponent.fromText(ChatUtil.color(t.getSlot()[1][i + 3]))));
        }
        for (int i = 0; i < 16; ++i) {
            list.add(new PlayerInfoData(t.getEntries()[2][i + 3], 0, EnumWrappers.NativeGameMode.SURVIVAL, WrappedChatComponent.fromText(ChatUtil.color(t.getSlot()[2][i + 3]))));
        }
        for (int i = 0; i < 16; ++i) {
            list.add(new PlayerInfoData(t.getEntries()[3][i + 3], 0, EnumWrappers.NativeGameMode.SURVIVAL, WrappedChatComponent.fromText(ChatUtil.color(t.getSlot()[3][i + 3]))));
        }
        container.getPlayerInfoDataLists().write(0, list);
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, container);
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
