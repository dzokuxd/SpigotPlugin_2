package pl.spigotplugin.protocoltab.manager;

import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import net.md_5.bungee.api.chat.BaseComponent;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import pl.spigotplugin.protocoltab.packet.PlayerInfoHeaderFooterPacket;
import pl.spigotplugin.protocoltab.packet.PlayerInfoPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ProtocolTab {
    public static String BLANK_TEXT = " ";
    public static char[] ALPHABET;

    static {
        ALPHABET = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T'};
    }

    private Map<Integer, ProtocolSlot> slots;
    private UUID uuid;
    private int ping;
    private String header;
    private String footer;

    public ProtocolTab(UUID uuid, int ping) {
        this.slots = new ConcurrentHashMap<Integer, ProtocolSlot>(80);
        Validate.isTrue(uuid != null, "UUID cannot be null!");
        this.uuid = uuid;
        this.ping = ping;
    }

    public void update(Player player) {
        if (player == null || !player.isOnline()) {
            return;
        }
        PlayerInfoPacket infoPacket = new PlayerInfoPacket();
        infoPacket.setAction(EnumWrappers.PlayerInfoAction.ADD_PLAYER);
        List<PlayerInfoData> infoData = new ArrayList<PlayerInfoData>();
        for (Map.Entry<Integer, ProtocolSlot> entry : this.slots.entrySet()) {
            infoData.add(new PlayerInfoData(entry.getValue().getProfile(), this.ping, EnumWrappers.NativeGameMode.NOT_SET, WrappedChatComponent.fromText(ChatColor.translateAlternateColorCodes('&', entry.getValue().getText()))));
        }
        infoPacket.setData(infoData);
        infoPacket.sendPacket(player);
        PlayerInfoHeaderFooterPacket packet = new PlayerInfoHeaderFooterPacket();
        if (this.header == null) {
            this.header = "";
        }
        if (this.footer == null) {
            this.footer = "";
        }
        packet.setHeader(WrappedChatComponent.fromText(ChatColor.translateAlternateColorCodes('&', this.header)));
        packet.setFooter(WrappedChatComponent.fromText(ChatColor.translateAlternateColorCodes('&', this.footer)));
        packet.sendPacket(player);
    }

    public void update() {
        Player player = Bukkit.getPlayer(this.uuid);
        if (player == null) {
            return;
        }
        this.update(player);
    }

    public ProtocolSlot getSlot(int index) {
        WrappedGameProfile profile = new WrappedGameProfile(UUID.randomUUID(), "!" + this.getSlotName(index));
        return this.slots.computeIfAbsent(index, key -> new ProtocolSlot(profile, BLANK_TEXT));
    }

    public int getPing() {
        return this.ping;
    }

    public void setPing(int ping) {
        Validate.isTrue(ping < 0, "Ping cannot be null!");
        this.ping = ping;
    }

    public String getHeader() {
        return this.header;
    }

    public void setHeader(String header) {
        Validate.isTrue(header != null, "Header cannot be null!");
        this.header = header;
    }

    public void setHeader(BaseComponent... component) {
        Validate.isTrue(component != null, "Component cannot be null!");
        this.setFooter(BaseComponent.toLegacyText(component));
    }

    public String getFooter() {
        return this.footer;
    }

    public void setFooter(String footer) {
        Validate.isTrue(footer != null, "Footer cannot be null!");
        this.footer = footer;
    }

    public void setFooter(BaseComponent... component) {
        Validate.isTrue(component != null, "Component cannot be null!");
        this.setFooter(BaseComponent.toLegacyText(component));
    }

    public void setSlot(int index, String text) {
        Validate.isTrue(text != null, "Text cannot be null!");
        this.getSlot(index).setText(text);
    }

    public void setText(int index, BaseComponent... component) {
        Validate.isTrue(component != null);
        this.getSlot(index).setText(component);
    }

    private String getSlotName(int index) {
        if (index < 20) {
            return "A-" + ProtocolTab.ALPHABET[index];
        }
        if (index < 40) {
            return "B-" + ProtocolTab.ALPHABET[index - 20];
        }
        if (index < 60) {
            return "C-" + ProtocolTab.ALPHABET[index - 40];
        }
        if (index < 80) {
            return "D-" + ProtocolTab.ALPHABET[index - 60];
        }
        return "";
    }
}
