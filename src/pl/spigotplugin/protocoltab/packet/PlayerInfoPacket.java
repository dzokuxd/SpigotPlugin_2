package pl.spigotplugin.protocoltab.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import org.apache.commons.lang.Validate;

import java.util.List;

public class PlayerInfoPacket extends Packet {
    public static PacketType TYPE;

    static {
        TYPE = PacketType.Play.Server.PLAYER_INFO;
    }

    public PlayerInfoPacket() {
        super(new PacketContainer(PlayerInfoPacket.TYPE), PlayerInfoPacket.TYPE);
    }

    public PlayerInfoPacket(PacketContainer packet) {
        super(packet, PlayerInfoPacket.TYPE);
    }

    public EnumWrappers.PlayerInfoAction getAction() {
        return this.getHandle().getPlayerInfoAction().read(0);
    }

    public void setAction(EnumWrappers.PlayerInfoAction value) {
        Validate.isTrue(value != null, "Value cannot be null!");
        this.getHandle().getPlayerInfoAction().write(0, value);
    }

    public List<PlayerInfoData> getData() {
        return this.getHandle().getPlayerInfoDataLists().read(0);
    }

    public void setData(List<PlayerInfoData> value) {
        Validate.isTrue(value != null, "Value cannot be null!");
        this.getHandle().getPlayerInfoDataLists().write(0, value);
    }
}
