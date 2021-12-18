package pl.spigotplugin;

import com.comphenix.protocol.ProtocolLibrary;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.spigotplugin.api.*;
import pl.spigotplugin.commands.admin.*;
import pl.spigotplugin.commands.player.*;
import pl.spigotplugin.commands.premium.*;
import pl.spigotplugin.configs.*;
import pl.spigotplugin.handler.CreateWorldHandler;
import pl.spigotplugin.holder.ItemHolder;
import pl.spigotplugin.listeners.*;
import pl.spigotplugin.managers.*;
import pl.spigotplugin.mysql.MySQL;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.protocoltab.manager.ProtocolTabManager;
import pl.spigotplugin.tasks.*;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.CraftingUtil;

import java.sql.SQLException;

public class SpigotPlugin extends JavaPlugin {
    private static MySQL mySQL;
    private static SpigotPlugin plugin;
    public static SpigotPlugin getPlugin(){
        return SpigotPlugin.plugin;
    }
    public static MySQL getMySQL() { return mySQL; }
    private ProtocolTabManager manager;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable(){
        CitizensAPI.getNPCRegistry().deregisterAll();
        core.reloadLang();
        guild.reloadLang();
        statues.reloadLang();
        DropFile.reloadConfig();
        Settings.loadMaterials();
        try {
            mySQL = new MySQL();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        registerManager();
        registerListeners(getServer().getPluginManager());
        registerCommands();
        registerTasks();
        manager = new ProtocolTabManager(1);
        CraftingUtil.registerRecipe();
        getServer().getScheduler().runTaskLater(this, () -> CreateWorldHandler.handleCreateWorld("gtp"), 100);
        getServer().getScheduler().runTaskLater(this, () -> CreateWorldHandler.jebanyend("end"), 100);
        ProtocolLibrary.getProtocolManager().addPacketListener(new AntyMacroListener(this));
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        TopsManager.sortUser();
        TopsManager.sortGuild();
        ItemHolder.init();
        initBukkitSettings();


    }
    private void registerManager() {
        UserManager.loadUsers();
        BanManager.loadBans();
        MuteManager.loadMutes();
        DropFile.saveDefaultConfig();
        DropManager.setup();
        GuildManager.loadGuilds();
    }

    private void initBukkitSettings() {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "timings on");
        for (World world : Bukkit.getWorlds()) {
            world.setStorm(false);
            world.setThundering(false);
            world.setTime(1000L);
            world.setDifficulty(Difficulty.NORMAL);
            world.setGameRuleValue("doDaylightCycle", "false");
            world.setGameRuleValue("doFireTick", "false");
        }
    }

    @Override
    public void onDisable(){
        Bukkit.getScheduler().cancelTasks(this);
        CitizensAPI.getNPCRegistry().deregisterAll();
        CombatManager.getFightMap().clear();
        for (Player p : Bukkit.getOnlinePlayers()) {
            UserManager.getUser(p).saveSync();
            p.kickPlayer(ChatUtil.color("&cRestart serwera! Zaraz wracamy :)"));
        }
        for (Guild value : GuildManager.getGuilds().values()) {
            value.saveGold(value.getGold());
            value.saveSync();
        }
    }

    private void registerListeners(PluginManager pm) {
        pm.registerEvents(new PlayerInteractListener(), this);
        pm.registerEvents(new PistonListener(), this);
        pm.registerEvents(new BlockPlaceListener(), this);
        pm.registerEvents(new BlockBreakListener(), this);
        pm.registerEvents(new BorderListener(), this);
        pm.registerEvents(new RainListener(), this);
        pm.registerEvents(new CreatureSpawnListener(), this);
        pm.registerEvents(new PlayerQuitJoinListener(), this);
        pm.registerEvents(new InventoryListener(), this);
        pm.registerEvents(new PlayerDeathListener(), this);
        pm.registerEvents(new CommandListener(), this);
        pm.registerEvents(new EnityDamageListener(), this);
        pm.registerEvents(new PlayerMoveListener(), this);
        pm.registerEvents(new CommandListener(), this);
        pm.registerEvents(new TradeListener(), this);
        pm.registerEvents(new PlayerChatListener(), this);
        pm.registerEvents(new DamageShowListener(), this);
        pm.registerEvents(new EntityExplodeListener(), this);
        pm.registerEvents(new PlayerBucketFillListener(), this);
        pm.registerEvents(new EnchantListener(), this);
        pm.registerEvents(new ToggleListener(), this);
    }
    private void registerTasks() {
        new AutoMsgTask().runTaskTimerAsynchronously(this, 1200L, 1200L);
        new TurboTask().runTaskTimerAsynchronously(this, 20L, 20L);
        new CombatTask().runTaskTimer(this, 40L, 20L);
        new LiveTpsTask().runTaskTimerAsynchronously(this, 20L, 20L);
        new CheckValidityTask().runTaskTimer(this, 60, 1200);
        new BarTask().runTaskTimerAsynchronously(this, 20, 20);
        new DiscoTask().runTaskTimerAsynchronously(this, 20, 1L);
        this.getServer().getScheduler().runTaskTimerAsynchronously(this, new SaveTask(), 120, 120);
    }

    private void registerCommands() {
        registerCommand(new DiscoCommand());
        registerCommand(new SpawnerCommand());
        registerCommand(new ResetujRankingCommand());
        registerCommand(new BlocksCommand());
        registerCommand(new FocusCommand());
        registerCommand(new EnderchestCommand());
        registerCommand(new SpawnBossCommand());
        registerCommand(new GenerateChestCommand());
        registerCommand(new ProfilCommand());
        registerCommand(new CheckbanCommand());
        registerCommand(new GuildAdminCommand());
        registerCommand(new HelpCommand());
        registerCommand(new GuildCommand());
        registerCommand(new GuildInfoCommand());
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
        registerCommand(new OdbierzCommand());
        registerCommand(new LiveTpsCommand());
        registerCommand(new GameplayCommand());
        registerCommand(new IncognitoCommand());
        registerCommand(new TpCommand());
        registerCommand(new LevelCommand());
        registerCommand(new TopkiCommand());
        registerCommand(new SchowekCommand());
        registerCommand(new GroupCommand());
        registerCommand(new AchievementCommand());
        registerCommand(new ManageCommand());
        registerCommand(new RankingCommand());
    }
    public ProtocolTabManager getManager() {
        return manager;
    }
    private void registerCommand(Command command){
        CommandManager.register(command);
    }
}
