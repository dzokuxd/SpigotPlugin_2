package pl.spigotplugin.enums;

public enum RankType
{
    GRACZ(0),
    VIP(10),
    SVIP(20),
    EASY(21),
    HELPER(50),
    MOD(55),
    ADMIN(55),
    HA(60),
    PREZES(100);

    private int priority;

    private RankType(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return this.priority;
    }
}
