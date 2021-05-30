package pl.spigotplugin.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import java.util.concurrent.TimeUnit;

public enum AchievmentType {

    // kamien
    STONE_1(AchievmentTypeName.STONE, "&7&lPoziom I", new ItemStack(Material.STONE), 9, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%mined", 1000, new ItemStack(Material.STONE)),
    STONE_2(AchievmentTypeName.STONE, "&7&lPoziom II", new ItemStack(Material.STONE), 10, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%mined", 5000, new ItemStack(Material.STONE)),
    STONE_3(AchievmentTypeName.STONE, "&7&lPoziom III", new ItemStack(Material.STONE), 11, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%mined", 15000, new ItemStack(Material.STONE)),
    STONE_4(AchievmentTypeName.STONE, "&7&lPoziom IV", new ItemStack(Material.STONE), 12, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%mined", 35000, new ItemStack(Material.STONE)),
    STONE_5(AchievmentTypeName.STONE, "&7&lPoziom V", new ItemStack(Material.STONE), 13, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%mined", 60000, new ItemStack(Material.STONE)),
    STONE_6(AchievmentTypeName.STONE, "&7&lPoziom VI", new ItemStack(Material.STONE), 14, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%mined", 100000, new ItemStack(Material.STONE)),
    STONE_7(AchievmentTypeName.STONE, "&7&lPoziom VII", new ItemStack(Material.STONE), 15, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%mined", 150000, new ItemStack(Material.STONE)),
    STONE_8(AchievmentTypeName.STONE, "&7&lPoziom VIII", new ItemStack(Material.STONE), 16, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%mined", 250000, new ItemStack(Material.STONE)),
    STONE_9(AchievmentTypeName.STONE, "&7&lPoziom IX", new ItemStack(Material.STONE), 17, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%mined", 500000, new ItemStack(Material.STONE)),

    // obs
    OBS_1(AchievmentTypeName.OBSIDIAN, "&7&lPoziom I", new ItemStack(Material.STONE), 9, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%obs", 1000, new ItemStack(Material.OBSIDIAN)),
    OBS_2(AchievmentTypeName.OBSIDIAN, "&7&lPoziom II", new ItemStack(Material.STONE), 10, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%obs", 5000, new ItemStack(Material.OBSIDIAN)),
    OBS_3(AchievmentTypeName.OBSIDIAN, "&7&lPoziom III", new ItemStack(Material.STONE), 11, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%obs", 15000, new ItemStack(Material.OBSIDIAN)),
    OBS_4(AchievmentTypeName.OBSIDIAN, "&7&lPoziom IV", new ItemStack(Material.STONE), 12, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%obs", 35000, new ItemStack(Material.OBSIDIAN)),
    OBS_5(AchievmentTypeName.OBSIDIAN, "&7&lPoziom V", new ItemStack(Material.STONE), 13, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%obs", 60000, new ItemStack(Material.OBSIDIAN)),
    OBS_6(AchievmentTypeName.OBSIDIAN, "&7&lPoziom VI", new ItemStack(Material.STONE), 14, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%obs", 100000, new ItemStack(Material.OBSIDIAN)),
    OBS_7(AchievmentTypeName.OBSIDIAN, "&7&lPoziom VII", new ItemStack(Material.STONE), 15, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%obs", 150000, new ItemStack(Material.OBSIDIAN)),
    OBS_8(AchievmentTypeName.OBSIDIAN, "&7&lPoziom VIII", new ItemStack(Material.STONE), 16, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%obs", 250000, new ItemStack(Material.OBSIDIAN)),
    OBS_9(AchievmentTypeName.OBSIDIAN, "&7&lPoziom IX", new ItemStack(Material.STONE), 17, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%obs", 500000, new ItemStack(Material.OBSIDIAN)),

    // czas
    TIME_1(AchievmentTypeName.TIME, "&7&lPoziom I", new ItemStack(Material.WATCH), 9, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%time", (int)TimeUnit.HOURS.toMillis(1), new ItemStack(Material.WATCH)),
    TIME_2(AchievmentTypeName.TIME, "&7&lPoziom II", new ItemStack(Material.WATCH), 10, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%time", (int)TimeUnit.HOURS.toMillis(8), new ItemStack(Material.WATCH)),
    TIME_3(AchievmentTypeName.TIME, "&7&lPoziom III", new ItemStack(Material.WATCH), 11, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%time", (int)TimeUnit.HOURS.toMillis(16), new ItemStack(Material.WATCH)),
    TIME_4(AchievmentTypeName.TIME, "&7&lPoziom IV", new ItemStack(Material.WATCH), 12, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%time", (int)TimeUnit.HOURS.toMillis(24), new ItemStack(Material.WATCH)),
    TIME_5(AchievmentTypeName.TIME, "&7&lPoziom V", new ItemStack(Material.WATCH), 13, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%time", (int)TimeUnit.HOURS.toMillis(40), new ItemStack(Material.WATCH)),
    TIME_6(AchievmentTypeName.TIME, "&7&lPoziom VI", new ItemStack(Material.WATCH), 14, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%time", (int)TimeUnit.HOURS.toMillis(48), new ItemStack(Material.WATCH)),
    TIME_7(AchievmentTypeName.TIME, "&7&lPoziom VII", new ItemStack(Material.WATCH), 15, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%time", (int)TimeUnit.HOURS.toMillis(96), new ItemStack(Material.WATCH)),
    TIME_8(AchievmentTypeName.TIME, "&7&lPoziom VIII", new ItemStack(Material.WATCH), 16, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%time", (int)TimeUnit.HOURS.toMillis(120), new ItemStack(Material.WATCH)),
    TIME_9(AchievmentTypeName.TIME, "&7&lPoziom IX", new ItemStack(Material.WATCH), 17, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%time", (int)TimeUnit.HOURS.toMillis(168), new ItemStack(Material.WATCH)),

    // kox
    KOX_1(AchievmentTypeName.KOX, "&7&lPoziom I", new ItemStack(Material.GOLDEN_APPLE,1,(short)1), 9, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%kox", 1, new ItemStack(Material.WATCH)),
    KOX_2(AchievmentTypeName.KOX, "&7&lPoziom II", new ItemStack(Material.GOLDEN_APPLE,1,(short)1), 10, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%kox", 2, new ItemStack(Material.WATCH)),
    KOX_3(AchievmentTypeName.KOX, "&7&lPoziom III", new ItemStack(Material.GOLDEN_APPLE,1,(short)1), 11, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%kox", 3, new ItemStack(Material.WATCH)),
    KOX_4(AchievmentTypeName.KOX, "&7&lPoziom IV", new ItemStack(Material.GOLDEN_APPLE,1,(short)1), 12, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%kox", 4, new ItemStack(Material.WATCH)),
    KOX_5(AchievmentTypeName.KOX, "&7&lPoziom V", new ItemStack(Material.GOLDEN_APPLE,1,(short)1), 13, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%kox", 5, new ItemStack(Material.WATCH)),
    KOX_6(AchievmentTypeName.KOX, "&7&lPoziom VI", new ItemStack(Material.GOLDEN_APPLE,1,(short)1), 14, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%kox", 6, new ItemStack(Material.WATCH)),
    KOX_7(AchievmentTypeName.KOX, "&7&lPoziom VII", new ItemStack(Material.GOLDEN_APPLE,1,(short)1), 15, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%kox", 7, new ItemStack(Material.WATCH)),
    KOX_8(AchievmentTypeName.KOX, "&7&lPoziom VIII", new ItemStack(Material.GOLDEN_APPLE,1,(short)1), 16, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%kox", 8, new ItemStack(Material.WATCH)),
    KOX_9(AchievmentTypeName.KOX, "&7&lPoziom IX", new ItemStack(Material.GOLDEN_APPLE,1,(short)1), 17, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%kox", 9, new ItemStack(Material.WATCH)),

    // ref
    REF_1(AchievmentTypeName.REF, "&7&lPoziom I", new ItemStack(Material.GOLDEN_APPLE), 9, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%ref", 1, new ItemStack(Material.WATCH)),
    REF_2(AchievmentTypeName.REF, "&7&lPoziom II", new ItemStack(Material.GOLDEN_APPLE), 10, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%ref", 2, new ItemStack(Material.WATCH)),
    REF_3(AchievmentTypeName.REF, "&7&lPoziom III", new ItemStack(Material.GOLDEN_APPLE), 11, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%ref", 3, new ItemStack(Material.WATCH)),
    REF_4(AchievmentTypeName.REF, "&7&lPoziom IV", new ItemStack(Material.GOLDEN_APPLE), 12, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%ref", 4, new ItemStack(Material.WATCH)),
    REF_5(AchievmentTypeName.REF, "&7&lPoziom V", new ItemStack(Material.GOLDEN_APPLE), 13, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%ref", 5, new ItemStack(Material.WATCH)),
    REF_6(AchievmentTypeName.REF, "&7&lPoziom VI", new ItemStack(Material.GOLDEN_APPLE), 14, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%ref", 6, new ItemStack(Material.WATCH)),
    REF_7(AchievmentTypeName.REF, "&7&lPoziom VII", new ItemStack(Material.GOLDEN_APPLE), 15, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%ref", 7, new ItemStack(Material.WATCH)),
    REF_8(AchievmentTypeName.REF, "&7&lPoziom VIII", new ItemStack(Material.GOLDEN_APPLE), 16, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%ref", 8, new ItemStack(Material.WATCH)),
    REF_9(AchievmentTypeName.REF, "&7&lPoziom IX", new ItemStack(Material.GOLDEN_APPLE), 17, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%ref", 9, new ItemStack(Material.WATCH)),

    // asysty
    ASYSTY_1(AchievmentTypeName.ASYSTY, "&7&lPoziom I", new ItemStack(Material.IRON_SWORD), 9, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%asysty", 10, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    ASYSTY_2(AchievmentTypeName.ASYSTY, "&7&lPoziom II", new ItemStack(Material.IRON_SWORD), 10, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%asysty", 25, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    ASYSTY_3(AchievmentTypeName.ASYSTY, "&7&lPoziom III", new ItemStack(Material.IRON_SWORD), 11, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%asysty", 50, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    ASYSTY_4(AchievmentTypeName.ASYSTY, "&7&lPoziom IV", new ItemStack(Material.IRON_SWORD), 12, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%asysty", 75, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    ASYSTY_5(AchievmentTypeName.ASYSTY, "&7&lPoziom V", new ItemStack(Material.IRON_SWORD), 13, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%asysty", 100, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    ASYSTY_6(AchievmentTypeName.ASYSTY, "&7&lPoziom VI", new ItemStack(Material.IRON_SWORD), 14, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%asysty", 150, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    ASYSTY_7(AchievmentTypeName.ASYSTY, "&7&lPoziom VII", new ItemStack(Material.IRON_SWORD), 15, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%asysty", 200, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    ASYSTY_8(AchievmentTypeName.ASYSTY, "&7&lPoziom VIII", new ItemStack(Material.IRON_SWORD), 16, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%asysty", 250, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    ASYSTY_9(AchievmentTypeName.ASYSTY, "&7&lPoziom IX", new ItemStack(Material.IRON_SWORD), 17, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%asysty", 300, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    // kills
    KILLS_1(AchievmentTypeName.KILLS, "&7&lPoziom I", new ItemStack(Material.DIAMOND_SWORD), 9, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%killed", 10, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    KILLS_2(AchievmentTypeName.KILLS, "&7&lPoziom II", new ItemStack(Material.DIAMOND_SWORD), 10, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%killed", 25, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    KILLS_3(AchievmentTypeName.KILLS, "&7&lPoziom III", new ItemStack(Material.DIAMOND_SWORD), 11, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%killed", 50, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    KILLS_4(AchievmentTypeName.KILLS, "&7&lPoziom IV", new ItemStack(Material.DIAMOND_SWORD), 12, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%killed", 75, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    KILLS_5(AchievmentTypeName.KILLS, "&7&lPoziom V", new ItemStack(Material.DIAMOND_SWORD), 13, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%killed", 100, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    KILLS_6(AchievmentTypeName.KILLS, "&7&lPoziom VI", new ItemStack(Material.DIAMOND_SWORD), 14, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%killed", 150, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    KILLS_7(AchievmentTypeName.KILLS, "&7&lPoziom VII", new ItemStack(Material.DIAMOND_SWORD), 15, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%killed", 200, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    KILLS_8(AchievmentTypeName.KILLS, "&7&lPoziom VIII", new ItemStack(Material.DIAMOND_SWORD), 16, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%killed", 250, new ItemStack(Material.GOLDEN_APPLE,1,(short)1)),
    KILLS_9(AchievmentTypeName.KILLS, "&7&lPoziom IX", new ItemStack(Material.DIAMOND_SWORD), 17, "&7Nagroda: &cxD;:;;:;&7Posiadasz: &c%killed", 300, new ItemStack(Material.GOLDEN_APPLE,1,(short)1));



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
