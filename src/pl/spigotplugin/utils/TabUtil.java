package pl.spigotplugin.utils;

import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.configs.guild;
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.managers.TabManager;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.objects.guild.GuildWar;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.protocoltab.ProtocolTabAPI;
import pl.spigotplugin.protocoltab.manager.ProtocolTab;
import ru.tehkode.permissions.PermissionEntity;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.PermissionsData;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TabUtil {
    private final String[][] slot;
    private final WrappedGameProfile[][] entries;
    private final User playerData;

    public TabUtil(Player player) {
        this.playerData = UserManager.getUser(player);
        this.entries = new WrappedGameProfile[4][20];
        this.slot = new String[4][20];
        int base = 97;
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 20; ++j) {
                char first = (char) (base + i);
                char second = (char) (base + j);
                String name = "!!UPDATEMC" + first + "" + second;
                WrappedGameProfile wrappedGameProfile = new WrappedGameProfile(UUID.randomUUID(), name);
                wrappedGameProfile.getProperties().put("textures", new WrappedSignedProperty("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYzNTk3MDcyNDQwNiwKICAicHJvZmlsZUlkIiA6ICJjZGZhNjY2MGVmNjY0ZjAwYmEwODIwOGZiMDRiNjc0NyIsCiAgInByb2ZpbGVOYW1lIiA6ICJkem9rdSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS83YjA0MWQ0MDUyMWE4YTI5NmY0M2QzMDQ2NzY1NDNiZGVlYTkzNTZlM2JlOTkxYTgxM2JiMjYwYzNlOTE0ZTM3IgogICAgfQogIH0KfQ==", "HxdUW6eqq63HzrdCypG3EloBkp//60m/l4IJeL20Tlk4ZCUZ0y9hqsCgMbde9pjVTex5VkYVWjQiArD05P7SdfuPG+vYo5HBTP9yxIK2XFCJlk7nfJCHoNoUS2rUzrKR664O52JVXJRaXZkoTw77vHt5hHp0BBEVnXTeo1KIqbn1X816YAD97LB9lp54RUVX/y3Ovfq2lLQ/aR7taoXbcCUaS4L9Yly8X2qe2cFSuq/NvbaEmJvpAuhXnZdaBRsKsSIRP0PCRNnp3PfbcljTTfXcGsJG03seBDOTPZsVZBlcDZQjXq8F/NxyBDOdvTvOTn01V8V6iaYZOV/vbvw74tEGrnsGPPWtUJ3QhCwv3BC3uBe4SUJx7uDTKONC0BPKWoN8ybl6vs+QYllZhzAahjKmeWW9I0bTsvbh22327kPKtpISPxFi63XuXxieazdT//NJz0U1tB4Px2xebqNnHAWFUD4z6EV0KGe9y9uyzPxnBV2IwPpetgEf8U6y59jRD1MF6NpKoUaBRlTtrWtkvJSDoVj1nhJ2izy1SiZ7TgY16tOCYcu4OG2gemB+4ZG55zAHYaGpwq2XrDcVrqXlm9coY2hjNuFhrYEKSffH+gVrQrZUKCaxsiwY+2CKGm7igBZD6yoFXo9PZEj339W14tNyuBvvzdRMHajXrh2Qp9c="));
                this.entries[i][j] = wrappedGameProfile;
            }
        }
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 20; ++j) {
                this.slot[i][j] = "";
            }
        }
        this.setSLOT(0, 0, "");
        this.setSLOT(0, 1, "&7&lStatystyki");
        this.setSLOT(0, 2, "");
        this.setSLOT(0, 3, "&fNick: &dLoading...");
        this.setSLOT(0, 4, "&fGrupa: &dLoading...");
        this.setSLOT(0, 5, "&fIncognito: &dLoading...");
        this.setSLOT(0, 6, "&fRanking: &dLoading...");
        this.setSLOT(0, 7, "&fZabojstwa: &dLoading...");
        this.setSLOT(0, 8, "&fSmierci: &dLoading...");
        this.setSLOT(0, 9, "&fAsysty: &dLoading...");
        this.setSLOT(0, 10, "&fKD: &dLoading...");
        this.setSLOT(0, 11, "&fKillStreak: &dLoading...");
        this.setSLOT(0, 12, "&fMax KillStreak: &dLoading...");
        this.setSLOT(0, 13, "");
        this.setSLOT(0, 14, "&7&lInformacje");
        this.setSLOT(0, 15, "");
        this.setSLOT(0, 16, "&fGodzina: &dLoading...");
        this.setSLOT(0, 17, "&fPing: &dLoading...");
        this.setSLOT(0, 18, "&fDC: &ddc.easyage.pl");
        this.setSLOT(1, 0, "");
        this.setSLOT(1, 1, "&7&lTopka graczy");
        this.setSLOT(1, 2, "");
        this.setSLOT(1, 3, TabManager.getReplacementR(1));
        this.setSLOT(1, 4, TabManager.getReplacementR(2));
        this.setSLOT(1, 5, TabManager.getReplacementR(3));
        this.setSLOT(1, 6, TabManager.getReplacementR(4));
        this.setSLOT(1, 7, TabManager.getReplacementR(5));
        this.setSLOT(1, 8, TabManager.getReplacementR(6));
        this.setSLOT(1, 9, TabManager.getReplacementR(7));
        this.setSLOT(1, 10, TabManager.getReplacementR(8));
        this.setSLOT(1, 11, TabManager.getReplacementR(9));
        this.setSLOT(1, 12, TabManager.getReplacementR(10));
        this.setSLOT(1, 13, TabManager.getReplacementR(11));
        this.setSLOT(1, 14, TabManager.getReplacementR(12));
        this.setSLOT(1, 15, TabManager.getReplacementR(13));
        this.setSLOT(1, 16, TabManager.getReplacementR(14));
        this.setSLOT(1, 17, TabManager.getReplacementR(15));
        this.setSLOT(1, 18, "");
        this.setSLOT(2, 0, "");
        this.setSLOT(2, 1, "&7&lTopka gildii");
        this.setSLOT(2, 2, "");
        this.setSLOT(2, 3, TabManager.getReplacementG(1));
        this.setSLOT(2, 4, TabManager.getReplacementG(2));
        this.setSLOT(2, 5, TabManager.getReplacementG(3));
        this.setSLOT(2, 6, TabManager.getReplacementG(4));
        this.setSLOT(2, 7, TabManager.getReplacementG(5));
        this.setSLOT(2, 8, TabManager.getReplacementG(6));
        this.setSLOT(2, 9, TabManager.getReplacementG(7));
        this.setSLOT(2, 10, TabManager.getReplacementG(8));
        this.setSLOT(2, 11, TabManager.getReplacementG(9));
        this.setSLOT(2, 12, TabManager.getReplacementG(10));
        this.setSLOT(2, 13, TabManager.getReplacementG(11));
        this.setSLOT(2, 14, TabManager.getReplacementG(12));
        this.setSLOT(2, 15, TabManager.getReplacementG(13));
        this.setSLOT(2, 16, TabManager.getReplacementG(14));
        this.setSLOT(2, 17, TabManager.getReplacementG(15));
        this.setSLOT(2, 18, "");
        Guild g = GuildManager.getGuild(player);
        if (g != null) {
            this.setSLOT(3, 1, "&7&lTwoja Gildia");
            this.setSLOT(3, 2, "");
            this.setSLOT(3, 3, "&fLider: &dLoading...");
            this.setSLOT(3, 4, "&fZastepca/y: &dLoading...");
            this.setSLOT(3, 5, "&fZabojstwa: &dLoading...");
            this.setSLOT(3, 6, "&fSmierci: &dLoading...");
            this.setSLOT(3, 7, "&fRanking: &dLoading...");
            this.setSLOT(3, 8, "&fZycia: &dLoading...");
            this.setSLOT(3, 9, "&fHP: &dLoading...");
            this.setSLOT(3, 10, "&fOnline: &dLoading...");
            this.setSLOT(3, 11, "&fCzlonkow: &dLoading...");
            this.setSLOT(3, 12, "&fTrwajace wojny: ");
            this.setSLOT(3, 13, "&fGildie: &dLoading...");
        } else {
            this.setSLOT(3, 1, "&cNie posiadasz gildii!");
            this.setSLOT(3, 2, "&cAby zalozyc gildie wpisz");
            this.setSLOT(3, 3, "&c/g itemy");
            this.setSLOT(3, 4, "");
            this.setSLOT(3, 5, "");
            this.setSLOT(3, 6, "");
            this.setSLOT(3, 7, "");
            this.setSLOT(3, 8, "");
            this.setSLOT(3, 9, "");
            this.setSLOT(3, 10, "");
            this.setSLOT(3, 11, "");
            this.setSLOT(3, 12, "");
            this.setSLOT(3, 13, "");
        }
    }

    public void update(Player player) {
        PermissionUser uu = PermissionsEx.getUser(player);
        Guild g = GuildManager.getGuild(player);
        this.setSLOT(0, 3, "&fNick: &d"+this.getPlayerData().getName());
        this.setSLOT(0, 4, "&fGrupa: &d"+ Arrays.toString(uu.getGroupsNames()).replace("]", "").replace("[", ""));
        this.setSLOT(0, 5, "&fIncognito: &d"+(this.getPlayerData().isIncognito() ? "&aWlaczone" : "&cWylaczone"));
        this.setSLOT(0, 6, "&fRanking: &d"+this.getPlayerData().getPoints());
        this.setSLOT(0, 7, "&fZabojstwa: &d"+this.getPlayerData().getKills());
        this.setSLOT(0, 8, "&fSmierci: &d"+this.getPlayerData().getDeaths());
        this.setSLOT(0, 9, "&fAsysty: &d"+this.getPlayerData().getAsysty());
        this.setSLOT(0, 10, "&fKD: &d"+this.getPlayerData().getKDR());
        this.setSLOT(0, 11, "&fKillStreak: &d"+this.getPlayerData().getKs());
        this.setSLOT(0, 12, "&fMax KillStreak: &d"+this.getPlayerData().getMaxks());
        this.setSLOT(0, 16, "&fGodzina: &d"+ DataUtil.getTime(System.currentTimeMillis()));
        this.setSLOT(0, 17, "&fPing: &d"+((CraftPlayer) player).getHandle().ping);
        this.setSLOT(1, 3, TabManager.getReplacementR(1));
        this.setSLOT(1, 4, TabManager.getReplacementR(2));
        this.setSLOT(1, 5, TabManager.getReplacementR(3));
        this.setSLOT(1, 6, TabManager.getReplacementR(4));
        this.setSLOT(1, 7, TabManager.getReplacementR(5));
        this.setSLOT(1, 8, TabManager.getReplacementR(6));
        this.setSLOT(1, 9, TabManager.getReplacementR(7));
        this.setSLOT(1, 10, TabManager.getReplacementR(8));
        this.setSLOT(1, 11, TabManager.getReplacementR(9));
        this.setSLOT(1, 12, TabManager.getReplacementR(10));
        this.setSLOT(1, 13, TabManager.getReplacementR(11));
        this.setSLOT(1, 14, TabManager.getReplacementR(12));
        this.setSLOT(1, 15, TabManager.getReplacementR(13));
        this.setSLOT(1, 16, TabManager.getReplacementR(14));
        this.setSLOT(1, 17, TabManager.getReplacementR(15));
        this.setSLOT(2, 3, TabManager.getReplacementG(1));
        this.setSLOT(2, 4, TabManager.getReplacementG(2));
        this.setSLOT(2, 5, TabManager.getReplacementG(3));
        this.setSLOT(2, 6, TabManager.getReplacementG(4));
        this.setSLOT(2, 7, TabManager.getReplacementG(5));
        this.setSLOT(2, 8, TabManager.getReplacementG(6));
        this.setSLOT(2, 9, TabManager.getReplacementG(7));
        this.setSLOT(2, 10, TabManager.getReplacementG(8));
        this.setSLOT(2, 11, TabManager.getReplacementG(9));
        this.setSLOT(2, 12, TabManager.getReplacementG(10));
        this.setSLOT(2, 13, TabManager.getReplacementG(11));
        this.setSLOT(2, 14, TabManager.getReplacementG(12));
        this.setSLOT(2, 15, TabManager.getReplacementG(13));
        this.setSLOT(2, 16, TabManager.getReplacementG(14));
        this.setSLOT(2, 17, TabManager.getReplacementG(15));
        if (g != null) {
            this.setSLOT(3, 1, "&7&lTwoja Gildia");
            this.setSLOT(3, 2, "");
            this.setSLOT(3, 3, "&fLider: &d"+g.getLeader());
            this.setSLOT(3, 4, "&fZastepca/y: &d"+g.getDeputy());
            this.setSLOT(3, 5, "&fZabojstwa: &d"+g.getKills());
            this.setSLOT(3, 6, "&fSmierci: &d"+g.getDeaths());
            this.setSLOT(3, 7, "&fRanking: &d"+g.getPoints());
            this.setSLOT(3, 8, "&fZycia: &d"+g.getLife());
            this.setSLOT(3, 9, "&fHP: &d"+g.getHp());
            this.setSLOT(3, 10, "&fOnline: &d"+g.getOnlineMembers().size());
            this.setSLOT(3, 11, "&fCzlonkow: &d"+g.getMembers());
            this.setSLOT(3, 12, "&fTrwajace wojny: ");
            this.setSLOT(3, 13, "&fGildie: &d"+ StringUtils.join(g.getWars().stream().map(GuildWar::getTag).collect(Collectors.toList()), ", "));
        }
        else {
            this.setSLOT(3, 1, "&cNie posiadasz gildii!");
            this.setSLOT(3, 2, "&cAby zalozyc gildie wpisz");
            this.setSLOT(3, 3, "&c/g itemy");
            this.setSLOT(3, 4, "");
            this.setSLOT(3, 5, "");
            this.setSLOT(3, 6, "");
            this.setSLOT(3, 7, "");
            this.setSLOT(3, 8, "");
            this.setSLOT(3, 9, "");
            this.setSLOT(3, 10, "");
            this.setSLOT(3, 11, "");
            this.setSLOT(3, 12, "");
            this.setSLOT(3, 13, "");
        }
    }

    public WrappedGameProfile[][] getEntries() {
        return this.entries;
    }

    public String[][] getSlot() {
        return this.slot;
    }

    public void setSLOT(int a, int b, String tab) {
        this.slot[a][b] = tab;
    }

    public User getPlayerData() {
        return this.playerData;
    }
}