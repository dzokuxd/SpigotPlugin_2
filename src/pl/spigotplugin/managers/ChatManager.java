package pl.spigotplugin.managers;

public class ChatManager {
    public static boolean enable;
    public static boolean vipChat;
    public static int SLOWMODE;

    static {
        enable = true;
        vipChat = false;
        SLOWMODE = 10;
    }
}