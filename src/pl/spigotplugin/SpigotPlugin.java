package pl.spigotplugin;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.spigotplugin.api.*;
import pl.spigotplugin.commands.*;
import pl.spigotplugin.configs.*;
import pl.spigotplugin.listeners.*;
import pl.spigotplugin.managers.BanManager;
import pl.spigotplugin.managers.MuteManager;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.mysql.MySQL;
import pl.spigotplugin.tasks.AutoMsgTask;

import java.sql.SQLException;

public class SpigotPlugin extends JavaPlugin {
    private static MySQL mySQL;
    private static SpigotPlugin plugin;

    public static SpigotPlugin getPlugin(){
        return SpigotPlugin.plugin;
    }
    public static MySQL getMySQL() { return mySQL; }

    @Override
    public void onLoad() {
        plugin = this;
    }

    public void onEnable(){
        try {
            mySQL = new MySQL();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        GlobalMessage.reloadLang();
        Config.reloadConfig();
        registerManager();
        registerListeners(getServer().getPluginManager());
        registerCommands();
        registerTasks();
    }
    private void registerManager() {
        UserManager.loadUsers();
        BanManager.loadBans();
        MuteManager.loadMutes();
    }

    public void onDisable(){
        super.onDisable();
    }

    private void registerListeners(PluginManager pm) {
        pm.registerEvents(new PlayerInteractListener(), this);
        pm.registerEvents(new BlockPlaceListener(), this);
        pm.registerEvents(new BorderListener(), this);
        pm.registerEvents(new RainListener(), this);
        pm.registerEvents(new CreatureSpawnListener(), this);
        pm.registerEvents(new PlayerQuitJoinListener(), this);
        pm.registerEvents(new InventoryListener(), this);
        pm.registerEvents(new PlayerDeathListener(), this);
        pm.registerEvents(new CommandListener(), this);
        pm.registerEvents(new EnityDamageListener(), this);
        pm.registerEvents(new PlayerMoveListener(), this);
    }
    private void registerTasks() {
        new AutoMsgTask().runTaskTimerAsynchronously(this, 1200L, 1200L);
    }

    private void registerCommands() {
        registerCommand(new HelpCommand());
        registerCommand(new GuildCommand());
        registerCommand(new YouTubeCommand());
        registerCommand(new WorkbenchCommand());
        registerCommand(new VipCommand());
        registerCommand(new SvipCommand());
        registerCommand(new TopCommand());
        registerCommand(new SpeedCommand());
        registerCommand(new TrashCommand());
        registerCommand(new RepairCommand());
        registerCommand(new ClearCommand());
        registerCommand(new DayCommand());
        registerCommand(new NightCommand());
        registerCommand(new FlyCommand());
        registerCommand(new GamemodeCommand());
        registerCommand(new VanishCommand());
        registerCommand(new VoucherCommand());
        registerCommand(new BorderCommand());
        registerCommand(new TNTStatusCommand());
        registerCommand(new RenameCommand());
        registerCommand(new MoreCommand());
        registerCommand(new EventsCommand());
        registerCommand(new EventCommand());
        registerCommand(new AutoMsgCommand());
        registerCommand(new ChatManagerCommand());
        registerCommand(new BackupCommand());
        registerCommand(new BanCommand());
        registerCommand(new UnbanCommand());
        registerCommand(new AutocxCommand());
        registerCommand(new BroadcastCommand());
        registerCommand(new CageCommand());
        registerCommand(new CobblexCommand());
        registerCommand(new CheckCommand());
        registerCommand(new MuteCommand());
        registerCommand(new UnmuteCommand());
        registerCommand(new MsgCommand());
        registerCommand(new ReplyCommand());
        registerCommand(new TpaCommand());
        registerCommand(new TpacceptCommand());
        registerCommand(new StpCommand());
        registerCommand(new SpawnCommand());
        registerCommand(new OpenCommand());
        registerCommand(new ChatCommand());
        registerCommand(new EnchantCommand());
        registerCommand(new ConfigCommand());
        registerCommand(new GiveCommand());
        registerCommand(new KickCommand());
        registerCommand(new KickAllCommand());
        registerCommand(new HeadCommand());
        registerCommand(new HealCommand());
        registerCommand(new KitCommand());
        registerCommand(new CraftingiCommand());
        registerCommand(new HelpOpCommand());
        registerCommand(new WyjebaneCommand());
        registerCommand(new ItemShopCommand());
        registerCommand(new DajCommand());
        registerCommand(new StoneCommand());
    }

    private void registerCommand(Command command){
        CommandManager.register(command);
    }
}
