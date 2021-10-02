package pl.spigotplugin.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import java.util.concurrent.TimeUnit;

public enum AchievmentType {

    // kamien
    STONE_1(AchievmentTypeName.STONE, "&7&lPoziom I", new ItemStack(Material.STONE), 9, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%mined&7/1000", 1000, new ItemStack(Material.STONE)),
    STONE_2(AchievmentTypeName.STONE, "&7&lPoziom II", new ItemStack(Material.STONE), 10, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%mined&7/5000", 5000, new ItemStack(Material.STONE)),
    STONE_3(AchievmentTypeName.STONE, "&7&lPoziom III", new ItemStack(Material.STONE), 11, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%mined&7/15000", 15000, new ItemStack(Material.STONE)),
    STONE_4(AchievmentTypeName.STONE, "&7&lPoziom IV", new ItemStack(Material.STONE), 12, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%mined&7/35000", 35000, new ItemStack(Material.STONE)),
    STONE_5(AchievmentTypeName.STONE, "&7&lPoziom V", new ItemStack(Material.STONE), 13, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%mined&7/60000", 60000, new ItemStack(Material.STONE)),
    STONE_6(AchievmentTypeName.STONE, "&7&lPoziom VI", new ItemStack(Material.STONE), 14, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%mined&7/100000", 100000, new ItemStack(Material.STONE)),
    STONE_7(AchievmentTypeName.STONE, "&7&lPoziom VII", new ItemStack(Material.STONE), 15, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%mined&7/150000", 150000, new ItemStack(Material.STONE)),
    STONE_8(AchievmentTypeName.STONE, "&7&lPoziom VIII", new ItemStack(Material.STONE), 16, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%mined&7/250000", 250000, new ItemStack(Material.STONE)),
    STONE_9(AchievmentTypeName.STONE, "&7&lPoziom IX", new ItemStack(Material.STONE), 17, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%mined&7/500000", 500000, new ItemStack(Material.STONE)),

    // obs
    OBS_1(AchievmentTypeName.OBSIDIAN, "&7&lPoziom I", new ItemStack(Material.OBSIDIAN), 9, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%obs&7/1000", 1000, new ItemStack(Material.OBSIDIAN)),
    OBS_2(AchievmentTypeName.OBSIDIAN, "&7&lPoziom II", new ItemStack(Material.OBSIDIAN), 10, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%obs&7/5000", 5000, new ItemStack(Material.OBSIDIAN)),
    OBS_3(AchievmentTypeName.OBSIDIAN, "&7&lPoziom III", new ItemStack(Material.OBSIDIAN), 11, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%obs&7/15000", 15000, new ItemStack(Material.OBSIDIAN)),
    OBS_4(AchievmentTypeName.OBSIDIAN, "&7&lPoziom IV", new ItemStack(Material.OBSIDIAN), 12, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%obs&7/35000", 35000, new ItemStack(Material.OBSIDIAN)),
    OBS_5(AchievmentTypeName.OBSIDIAN, "&7&lPoziom V", new ItemStack(Material.OBSIDIAN), 13, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%obs&7/60000", 60000, new ItemStack(Material.OBSIDIAN)),
    OBS_6(AchievmentTypeName.OBSIDIAN, "&7&lPoziom VI", new ItemStack(Material.OBSIDIAN), 14, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%obs&7/100000", 100000, new ItemStack(Material.OBSIDIAN)),
    OBS_7(AchievmentTypeName.OBSIDIAN, "&7&lPoziom VII", new ItemStack(Material.OBSIDIAN), 15, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%obs&7/150000", 150000, new ItemStack(Material.OBSIDIAN)),
    OBS_8(AchievmentTypeName.OBSIDIAN, "&7&lPoziom VIII", new ItemStack(Material.OBSIDIAN), 16, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%obs&7/250000", 250000, new ItemStack(Material.OBSIDIAN)),
    OBS_9(AchievmentTypeName.OBSIDIAN, "&7&lPoziom IX", new ItemStack(Material.OBSIDIAN), 17, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%obs&7/500000", 500000, new ItemStack(Material.OBSIDIAN)),

    // czas
    TIME_1(AchievmentTypeName.TIME, "&7&lPoziom I", new ItemStack(Material.WATCH), 9, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%time&7/1h", (int)TimeUnit.HOURS.toMillis(1), new ItemStack(Material.WATCH)),
    TIME_2(AchievmentTypeName.TIME, "&7&lPoziom II", new ItemStack(Material.WATCH), 10, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%time&7/8h", (int)TimeUnit.HOURS.toMillis(8), new ItemStack(Material.WATCH)),
    TIME_3(AchievmentTypeName.TIME, "&7&lPoziom III", new ItemStack(Material.WATCH), 11, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%time&7/16h", (int)TimeUnit.HOURS.toMillis(16), new ItemStack(Material.WATCH)),
    TIME_4(AchievmentTypeName.TIME, "&7&lPoziom IV", new ItemStack(Material.WATCH), 12, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%time&7/24h", (int)TimeUnit.HOURS.toMillis(24), new ItemStack(Material.WATCH)),
    TIME_5(AchievmentTypeName.TIME, "&7&lPoziom V", new ItemStack(Material.WATCH), 13, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%time&7/40h", (int)TimeUnit.HOURS.toMillis(40), new ItemStack(Material.WATCH)),
    TIME_6(AchievmentTypeName.TIME, "&7&lPoziom VI", new ItemStack(Material.WATCH), 14, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%time&7/48h", (int)TimeUnit.HOURS.toMillis(48), new ItemStack(Material.WATCH)),
    TIME_7(AchievmentTypeName.TIME, "&7&lPoziom VII", new ItemStack(Material.WATCH), 15, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%time&7/96h", (int)TimeUnit.HOURS.toMillis(96), new ItemStack(Material.WATCH)),
    TIME_8(AchievmentTypeName.TIME, "&7&lPoziom VIII", new ItemStack(Material.WATCH), 16, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%time&7/120h", (int)TimeUnit.HOURS.toMillis(120), new ItemStack(Material.WATCH)),
    TIME_9(AchievmentTypeName.TIME, "&7&lPoziom IX", new ItemStack(Material.WATCH), 17, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%time&7/168h", (int)TimeUnit.HOURS.toMillis(168), new ItemStack(Material.WATCH)),

    // kox
    KOX_1(AchievmentTypeName.KOX, "&7&lPoziom I", new ItemStack(Material.GOLDEN_APPLE,1,(short)1), 9, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%kox&7/1", 1, new ItemStack(Material.WATCH)),
    KOX_2(AchievmentTypeName.KOX, "&7&lPoziom II", new ItemStack(Material.GOLDEN_APPLE,1,(short)1), 10, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%kox&7/2", 2, new ItemStack(Material.WATCH)),
    KOX_3(AchievmentTypeName.KOX, "&7&lPoziom III", new ItemStack(Material.GOLDEN_APPLE,1,(short)1), 11, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%kox&7/3", 3, new ItemStack(Material.WATCH)),
    KOX_4(AchievmentTypeName.KOX, "&7&lPoziom IV", new ItemStack(Material.GOLDEN_APPLE,1,(short)1), 12, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%kox&7/4", 4, new ItemStack(Material.WATCH)),
    KOX_5(AchievmentTypeName.KOX, "&7&lPoziom V", new ItemStack(Material.GOLDEN_APPLE,1,(short)1), 13, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%kox&7/5", 5, new ItemStack(Material.WATCH)),
    KOX_6(AchievmentTypeName.KOX, "&7&lPoziom VI", new ItemStack(Material.GOLDEN_APPLE,1,(short)1), 14, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%kox&7/6", 6, new ItemStack(Material.WATCH)),
    KOX_7(AchievmentTypeName.KOX, "&7&lPoziom VII", new ItemStack(Material.GOLDEN_APPLE,1,(short)1), 15, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%kox&7/7", 7, new ItemStack(Material.WATCH)),
    KOX_8(AchievmentTypeName.KOX, "&7&lPoziom VIII", new ItemStack(Material.GOLDEN_APPLE,1,(short)1), 16, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%kox&7/8", 8, new ItemStack(Material.WATCH)),
    KOX_9(AchievmentTypeName.KOX, "&7&lPoziom IX", new ItemStack(Material.GOLDEN_APPLE,1,(short)1), 17, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%kox&7/9", 9, new ItemStack(Material.WATCH)),

    // ref
    REF_1(AchievmentTypeName.REF, "&7&lPoziom I", new ItemStack(Material.GOLDEN_APPLE,1,(short)0), 9, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%ref&7/1", 1, new ItemStack(Material.WATCH)),
    REF_2(AchievmentTypeName.REF, "&7&lPoziom II", new ItemStack(Material.GOLDEN_APPLE,1,(short)0), 10, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%ref&7/2", 2, new ItemStack(Material.WATCH)),
    REF_3(AchievmentTypeName.REF, "&7&lPoziom III", new ItemStack(Material.GOLDEN_APPLE,1,(short)0), 11, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%ref&7/3", 3, new ItemStack(Material.WATCH)),
    REF_4(AchievmentTypeName.REF, "&7&lPoziom IV", new ItemStack(Material.GOLDEN_APPLE,1,(short)0), 12, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%ref&7/4", 4, new ItemStack(Material.WATCH)),
    REF_5(AchievmentTypeName.REF, "&7&lPoziom V", new ItemStack(Material.GOLDEN_APPLE,1,(short)0), 13, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%ref&7/5", 5, new ItemStack(Material.WATCH)),
    REF_6(AchievmentTypeName.REF, "&7&lPoziom VI", new ItemStack(Material.GOLDEN_APPLE,1,(short)0), 14, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%ref&7/6", 6, new ItemStack(Material.WATCH)),
    REF_7(AchievmentTypeName.REF, "&7&lPoziom VII", new ItemStack(Material.GOLDEN_APPLE,1,(short)0), 15, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%ref&7/7", 7, new ItemStack(Material.WATCH)),
    REF_8(AchievmentTypeName.REF, "&7&lPoziom VIII", new ItemStack(Material.GOLDEN_APPLE,1,(short)0), 16, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%ref&7/8", 8, new ItemStack(Material.WATCH)),
    REF_9(AchievmentTypeName.REF, "&7&lPoziom IX", new ItemStack(Material.GOLDEN_APPLE,1,(short)0), 17, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%ref&7/9", 9, new ItemStack(Material.WATCH)),

    // asysty
    ASYSTY_1(AchievmentTypeName.ASYSTY, "&7&lPoziom I", new ItemStack(Material.IRON_SWORD), 9, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%asysty&7/10", 10, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    ASYSTY_2(AchievmentTypeName.ASYSTY, "&7&lPoziom II", new ItemStack(Material.IRON_SWORD), 10, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%asysty&7/25", 25, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    ASYSTY_3(AchievmentTypeName.ASYSTY, "&7&lPoziom III", new ItemStack(Material.IRON_SWORD), 11, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%asysty&7/50", 50, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    ASYSTY_4(AchievmentTypeName.ASYSTY, "&7&lPoziom IV", new ItemStack(Material.IRON_SWORD), 12, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%asysty&7/75", 75, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    ASYSTY_5(AchievmentTypeName.ASYSTY, "&7&lPoziom V", new ItemStack(Material.IRON_SWORD), 13, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%asysty&7/100", 100, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    ASYSTY_6(AchievmentTypeName.ASYSTY, "&7&lPoziom VI", new ItemStack(Material.IRON_SWORD), 14, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%asysty&7/150", 150, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    ASYSTY_7(AchievmentTypeName.ASYSTY, "&7&lPoziom VII", new ItemStack(Material.IRON_SWORD), 15, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%asysty&7/200", 200, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    ASYSTY_8(AchievmentTypeName.ASYSTY, "&7&lPoziom VIII", new ItemStack(Material.IRON_SWORD), 16, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%asysty&7/250", 250, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    ASYSTY_9(AchievmentTypeName.ASYSTY, "&7&lPoziom IX", new ItemStack(Material.IRON_SWORD), 17, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%asysty&7/300", 300, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    // kills
    KILLS_1(AchievmentTypeName.KILLS, "&7&lPoziom I", new ItemStack(Material.DIAMOND_SWORD), 9, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%killed&7/10", 10, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    KILLS_2(AchievmentTypeName.KILLS, "&7&lPoziom II", new ItemStack(Material.DIAMOND_SWORD), 10, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%killed&7/25", 25, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    KILLS_3(AchievmentTypeName.KILLS, "&7&lPoziom III", new ItemStack(Material.DIAMOND_SWORD), 11, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%killed&7/50", 50, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    KILLS_4(AchievmentTypeName.KILLS, "&7&lPoziom IV", new ItemStack(Material.DIAMOND_SWORD), 12, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%killed&7/75", 75, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    KILLS_5(AchievmentTypeName.KILLS, "&7&lPoziom V", new ItemStack(Material.DIAMOND_SWORD), 13, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%killed&7/100", 100, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    KILLS_6(AchievmentTypeName.KILLS, "&7&lPoziom VI", new ItemStack(Material.DIAMOND_SWORD), 14, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%killed&7/150", 150, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    KILLS_7(AchievmentTypeName.KILLS, "&7&lPoziom VII", new ItemStack(Material.DIAMOND_SWORD), 15, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%killed&7/200", 200, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    KILLS_8(AchievmentTypeName.KILLS, "&7&lPoziom VIII", new ItemStack(Material.DIAMOND_SWORD), 16, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%killed&7/250", 250, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    KILLS_9(AchievmentTypeName.KILLS, "&7&lPoziom IX", new ItemStack(Material.DIAMOND_SWORD), 17, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%killed&7/300", 300, new ItemStack(Material.GOLDEN_APPLE,1,(short)1));



    private final AchievmentTypeName type;
    private final String displayName;
    private final ItemStack guiIcon;
    private final int guiSlot;
    private final String guiLore;
    private final int neededAmount;
    private final ItemStack reward;

    AchievmentType(AchievmentTypeName type, String displayName, ItemStack guiIcon, int guiSlot, String guiLore, int neededAmount, ItemStack reward) {
        this.type = type;
        this.displayName = displayName;
        this.guiIcon = guiIcon;
        this.guiSlot = guiSlot;
        this.guiLore = guiLore;
        this.neededAmount = neededAmount;
        this.reward = reward;
    }

    public ItemStack getGuiIcon() { return guiIcon; }

    public int getGuiSlot() {
        return guiSlot;
    }

    public String getGuiLore() {
        return guiLore;
    }

    public int getNeededAmount() {
        return neededAmount;
    }

    public ItemStack getReward() { return reward; }

    public AchievmentTypeName getType() {
        return type;
    }

    public String getDisplayName() {
        return displayName;
    }
}
