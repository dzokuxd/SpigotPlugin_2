package pl.spigotplugin.managers;

public class ChatManager {
    public static boolean enable;
    public static boolean disable;
    public static boolean vipChat;
    public static int SLOWMODE;

    static {
        enable = true;
        disable = true;
        vipChat = true;
        SLOWMODE = 10;
    }
}