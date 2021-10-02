package pl.spigotplugin.listeners;

import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Button;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class PlayerInteractListener implements Listener {
    public static Map<UUID, Long> leaver = new HashMap<>();
    public static Map<UUID, Long> guildclick = new HashMap<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack s = e.getItem();
        Action ea = e.getAction();
        User u = UserManager.getUser(p);
        Block clickedBlock = e.getClickedBlock();
        if (p.getItemInHand().isSimilar(VoucherUtil.vip)) {
            if (p.hasPermission("vip")) {
                p.sendMessage("&cPosiadasz ta lub lepsza range!");
                return;
            }
            Bukkit.broadcastMessage("&6Gracz &c" + p.getName() + " &6aktywowal Voucher na range &cVIP!");
            ItemUtil.removeItems(p, VoucherUtil.vip);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user" + p.getName() + " group set vip");
            return;
        }
        if (p.getItemInHand().isSimilar(VoucherUtil.svip)) {
            if (p.hasPermission("svip")) {
                p.sendMessage("&cPosiadasz ta lub lepsza range!");
                return;
            }
            Bukkit.broadcastMessage("&6Gracz &c" + p.getName() + " &6aktywowal Voucher na range &cSVIP!");
            ItemUtil.removeItems(p, VoucherUtil.svip);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user" + p.getName() + " group set svip");
            return;
        }
        if (u == null) {
            return;
        }
        if (p.getItemInHand().isSimilar(VoucherUtil.turbo)) {
            Bukkit.broadcastMessage("&6Gracz &c" + p.getName() + " &6aktywowal Voucher na &cTURBODROP 10M!");
            ItemUtil.removeItems(p, VoucherUtil.turbo);
            long turboDropHave = 0L;
            long currentTurboDrop = u.getTurboDrop();
            if(currentTurboDrop >System.currentTimeMillis()){
                turboDropHave = currentTurboDrop-System.currentTimeMillis();
            }
            long givenTurboDrop = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10);
            u.setTurboDrop(givenTurboDrop+turboDropHave);
            return;
        }
        if(clickedBlock != null){
            if (clickedBlock.getType() == Material.STONE_BUTTON) {
                if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
                    return;
                Button button = (Button) clickedBlock.getState().getData();
                Block relative = clickedBlock.getRelative(button.getAttachedFace());
                if (relative.getType() != Material.JUKEBOX)
                    return;
                randomTP(p);
                return;
            }
        }
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.WOOD_BUTTON) {
            Button button = (Button) clickedBlock.getState().getData();
            Block base = clickedBlock.getRelative(button.getAttachedFace());
            if (base.getType() != Material.JUKEBOX) {
                return;
            }
            int x = RandomUtil.getRandInt(-90, 90);
            int z = RandomUtil.getRandInt(-90, 90);
            int i = 0;
            World world_nether = Bukkit.getWorld("gtp");
            for (Player players : this.getPlayersInRadius(clickedBlock.getLocation(), 3)) {
                ++i;
                Location location = new Location(world_nether,x, world_nether.getHighestBlockYAt(x,z),z);
                if (i > 2) continue;
                p.teleport(location);
                Location ploc =p.getLocation().clone();
                ploc.setY(p.getLocation().getY() + 5.0);
                p.teleport(ploc);
                players.teleport(p.getLocation());
                for (PotionEffect activePotionEffect : players.getActivePotionEffects()) {
                    players.removePotionEffect(activePotionEffect.getType());
                }
            }
        }
        if (s == null) {
            return;
        }
        int strzalyw = ItemUtil.getItemAmount(Material.ARROW, p, (short) 0);
        if (strzalyw > Config.LIMIT_STRZAL) {
            ItemStack item = new ItemStack(Material.ARROW, (strzalyw - Config.LIMIT_STRZAL), (short) 0);
            int added = ItemUtil.remove(item, p, Config.LIMIT_STRZAL);
            u.addStrzaly(added);
            u.addarrowsShoten(1);
            p.sendMessage("&6Posiadasz przy sobie wiecej niz &c" + Config.LIMIT_STRZAL + " &6strzal! &7(&c" + added + " &6strzaly zostaja odlozone do twojego schowka&7)");
        }
        if (s.getType() == Material.ENDER_PEARL) {
            int pearl = ItemUtil.getItemAmount(Material.ENDER_PEARL, p, (short) 0);
            if (pearl > Config.LIMIT_PEARL) {
                ItemStack item = new ItemStack(Material.ENDER_PEARL, (pearl - Config.LIMIT_PEARL), (short) 0);
                int added = ItemUtil.remove(item, p, Config.LIMIT_PEARL);
                u.addPerly(added);
                u.addpearlThrown(1);
                p.sendMessage("&6Posiadasz przy sobie wiecej niz &c" + Config.LIMIT_PEARL + " &6perel! &7(&c" + added + " &6perly zostaja odlozone do twojego schowka&7)");
            }
        }
        if ((ea.equals(Action.RIGHT_CLICK_AIR)) || (ea.equals(Action.RIGHT_CLICK_BLOCK))) {
            if (e.getMaterial().equals(Material.PUMPKIN_PIE)) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 4, 3));
                p.getInventory().removeItem(new ItemStack(Material.PUMPKIN_PIE, 1));
            }
            if (e.getMaterial().equals(Material.NAME_TAG)) {
                if (s.getItemMeta() != null && s.getItemMeta().getDisplayName() != null && e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.color("&6&lAnty Nogi"))) {
                    Guild g = GuildManager.getGuild(p);
                    if (g == null) {
                        p.sendMessage("&cNie posiadasz gildi!");
                        return;
                    }
                    Guild gcub = GuildManager.getGuild(p.getLocation());
                    if (gcub == null) {
                        p.sendMessage("&cNie ma tutaj zadnego cuboida!");
                        return;
                    }
                    if (gcub.getTag().equalsIgnoreCase(g.getTag())) {
                        p.sendMessage("&cNie mozesz tego zrobic na swoim cuboidzie!");
                        return;
                    }
                    ArrayList<Player> gra = new ArrayList<>();
                    for (Player w : getPlayersInRadius(p.getLocation(), 20)) {
                        Guild gw = GuildManager.getGuild(w);
                        if (gw == null) {
                            continue;
                        }
                        if (!(g == gw)) {
                            continue;
                        }
                        if (p.getName().equals(w.getName())) {
                            continue;
                        }
                        gra.add(w);
                    }
                    if (gra.size() == 0) {
                        p.sendMessage("&cAby uwolnic kolege z nozek musisz stac od niego 20 kratek!");
                        return;
                    }
                    Inventory inv = Bukkit.createInventory(p, 27, (ChatUtil.color("&7Gracze do uratowania")));
                    for (int i = 0; i < gra.size(); i++) {
                        ItemBuilder gracz = new ItemBuilder(Material.SKULL_ITEM, (short) 3).setTitle(ChatUtil.color("&6" + gra.get(i).getName())).addLore(ChatUtil.color("&8\u00bb &7Aby uratowac tego gracz musisz kliknac w jego glowe!"));
                        inv.setItem(i, gracz.build());
                    }
                    p.openInventory(inv);
                }
            }
        }
        ItemStack k = e.getPlayer().getItemInHand();
        if (k.getType().equals(Material.DIAMOND_PICKAXE) && k.getDurability() == 1559) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,100, 100));
            p.sendMessage("&cTwoj kilof ma jedno uzycie, napraw go :)");
            ChatUtil.sendTitleMessage(p, "&cTwoj kilof ma jedno uzycie!", "&cIdz go napraw!", 30, 70, 40);
            e.setCancelled(true);
        }
        if (e.isCancelled()) {
            return;
        }
        if(e.getClickedBlock() == null)
            return;
        if (e.getClickedBlock().getType() != Material.LEVER) {
            return;
        }
        Long t = PlayerInteractListener.leaver.get(p.getUniqueId());
        if (t != null && System.currentTimeMillis() - t < 3000L) {
            p.sendMessage("&cOdczekaj chwile przed nastepnym uzyciem!");
            e.setCancelled(true);
            return;
        }
        PlayerInteractListener.leaver.put(p.getUniqueId(), System.currentTimeMillis());
    }

    @EventHandler
    public void onInteractAtEntity(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked() instanceof Player) {
            User user = UserManager.getUser(event.getPlayer());
            Player clickedPlayer = (Player)event.getRightClicked();
            User clickedUser = UserManager.getUser(clickedPlayer);
            int plusRank = (int)(155.0 + (user.getPoints() - clickedUser.getPoints()) * -0.15);
            if (plusRank <= 0) {
                plusRank = RandomUtil.getRandInteger(7, 30);
            }
            int loseRank = plusRank / 7 * 3;
            event.getPlayer().sendMessage("&6Za zabicie tego gracza otrzymasz &a" +plusRank+ " &6a stracisz&c " +loseRank);
        }
    }
    @EventHandler
    public void onLeftClick(NPCLeftClickEvent event) {
        NPC npc = event.getNPC();
        Player p = event.getClicker();
        if (event.isCancelled()) {
            return;
        }
        if (npc == null) {
            return;
        }
        Guild g = GuildManager.getGuild(npc.getEntity().getLocation());
        Guild gg = GuildManager.getGuild(p);
        if (g == null) {
            return;
        }
        if (gg == null) {
            p.sendMessage("&cMusisz posiadac gildie aby podbic inna gildie!");
            return;
        }
        if (g.isMember(p.getName())) {
            return;
        }
        if (g.getAlly().contains(gg.getTag())) {
            p.sendMessage("&cNie mozesz atakowac sojuszy!");
            return;
        }
        boolean guildwar = false;
        for (String s : g.getGuildWar()) {
            String[] splitter = s.split("@");
            guildwar = splitter[0].contains(gg.getTag());
        }
        if (!guildwar) {
            p.sendMessage("&cZeby podbic gildie musisz miec z nimi wojne! /g wojna " + g.getTag());
            return;
        }
        if (g.getHpLastAttack() > System.currentTimeMillis()) {
            p.sendMessage("&cGildie mozesz podbic za " + DataUtil.secondsToString(g.getHpLastAttack()));
            return;
        }
        /*if (!TNTUtil.isBetween()){
            p.sendMessage("&cGildie mozna podbic tylko gdy tnt jest wlaczone!");
            return;
        }*/
        if (p.getLocation().distance(g.getRegion().getLocation()) > 2.0) {
            p.sendMessage("&cMusisz byc blizej jajka gildii!");
            return;
        }
        if (g.getHp() > 0) {
            Long guildclick = PlayerInteractListener.guildclick.get(p.getUniqueId());
            if (guildclick != null && System.currentTimeMillis() - guildclick < 1000L) {
                event.setCancelled(true);
                return;
            }
            PlayerInteractListener.guildclick.put(p.getUniqueId(), System.currentTimeMillis());
            g.setHp(g.getHp() - 1);
            g.message("twoja gildia jest atakowana "+g.getHp());
            gg.message("&cPozostale hp gildii "+g.getTag()+ " to " +g.getHp());
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 10, 3));
            return;
        }
        if (g.getLife() == 1) {
            gg.getGuildWar().remove(g.getTag());
            g.getGuildWar().remove(gg.getTag());
            Bukkit.broadcastMessage("&7[&c" + g.getTag() + "&7] [&c-100&7]&c " + g.getName() + " &6zostala zniszczona przez &7[&c" + gg.getTag() + "&7] [&c+100&7]&c " + p.getName());
            ChatUtil.sendTitleMessage(p, "&c&lWOJNY", "&c"+gg.getTag()+" +100 &6Wygrala wojne z gildia&c "+g.getTag()+" -100", 30, 70, 40);
            p.sendMessage("&6Za podbicie gildie otrzymales &7x&c8 Skrzyn " + Config.IP);
            DajUtil.giveWithAmount("easycase", 8, p);
            GuildManager.deleteGuild(g);
        }
        else {
            g.setLife(g.getLife() - 1);
            g.setHp(50);
            gg.getGuildWar().remove(g.getTag());
            g.getGuildWar().remove(gg.getTag());
            g.setHpLastAttack(System.currentTimeMillis() + TimeUtil.HOUR.getTime(12));
            Bukkit.broadcastMessage("&7[&c" + g.getTag() + "&7] [&c-100&7]&c " + g.getName() + " &6zostala podbita przez &7[&c" + gg.getTag() + "&7] [&c+100&7]&c " + p.getName());
            Bukkit.broadcastMessage("&6Zostalo jej &c" + g.getLife() + " &6zyc");
            ChatUtil.sendTitleMessage(p, "&c&lWOJNY", "&c"+gg.getTag()+" +100 &6Wygrala wojne z gildia&c "+g.getTag()+" -100", 30, 70, 40);
            p.sendMessage("&6Za podbicie gildie otrzymales &7x&c8 Skrzyn " + Config.IP);
            DajUtil.giveWithAmount("easycase", 8, p);
        }
        gg.setLife(gg.getLife() +1);
        gg.putForSave();
        g.putForSave();
    }

    @EventHandler
    public void sing(SignChangeEvent e) {
        if (!e.getPlayer().hasPermission("spigotplugin.sign")) {
            return;
        }
        for (int i = 0; i <= 3; ++i) {
            e.setLine(i, ChatUtil.color(e.getLine(i)));
        }
    }
    @EventHandler
    public void onCraft(CraftItemEvent e) {
        ItemStack item = e.getRecipe().getResult();
        Player p = (Player) e.getWhoClicked();
        if (e.getInventory().getType().equals(InventoryType.WORKBENCH) && e.getSlotType().equals(InventoryType.SlotType.RESULT) && e.getCurrentItem().getType().name().equalsIgnoreCase("JUKEBOX")) {
            e.setCancelled(true);
            e.setCurrentItem(null);
            e.setResult(Event.Result.DENY);
            return;
        }
        if (item.getType() == Material.MINECART) {
            p.sendMessage( "&cCraftowanie wagonikow jest wylaczone!");
            e.setCancelled(true);
            e.setCurrentItem(null);
            e.setResult(Event.Result.DENY);
            return;
        }
        if (!Config.MANAGE_DIAMOND && (item.getType() == Material.DIAMOND_HELMET || item.getType() == Material.DIAMOND_CHESTPLATE || item.getType() == Material.DIAMOND_LEGGINGS || item.getType() == Material.DIAMOND_BOOTS || item.getType() == Material.DIAMOND_SWORD)) {
            p.sendMessage(ChatUtil.color("&cCraftowanie diamentowych itemow jest wylaczone!"));
            e.setCancelled(true);
            e.setCurrentItem(null);
            e.setResult(Event.Result.DENY);
        }
    }

    @EventHandler
    public void EndPortal(PlayerPortalEvent event) {
        event.setCancelled(true);
        Player p = event.getPlayer();
        Location location = p.getLocation();
        Guild g = GuildManager.getGuild(p);
        User user = UserManager.getUser(p);
        if (user.getGuild().isEmpty()) {
            p.sendMessage("&cNie posiadasz gildii!");
            p.playSound(location, Sound.VILLAGER_NO, 1f, 1f);
            return;
        }
        if (g == null) {
            return;
        }
        p.setVelocity(location.getDirection().multiply(1));
        p.playSound(location, Sound.VILLAGER_YES, 1f, 1f);
        p.teleport(g.getHome());
    }

    @EventHandler
    public void PlayerItemConsume(PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();
        User u = UserManager.getUser(p);
        ItemStack is = e.getItem();
        if (!is.getType().equals(Material.GOLDEN_APPLE)) {
            return;
        }
        int i = ItemUtil.getItemAmount(Material.GOLDEN_APPLE, p, (short) 1);
        if (i > Config.LIMIT_KOX) {
            ItemStack item = new ItemStack(Material.GOLDEN_APPLE, (i - Config.LIMIT_KOX), (short) 1);
            int added = ItemUtil.remove(item, p, Config.LIMIT_KOX);
            u.addKoxy(added);
            p.sendMessage("&6Posiadasz przy sobie wiecej niz &c" + Config.LIMIT_KOX + " &6koxy! &7(&c" + added + " &6koxy zostaja odlozone do twojego schowka&7)");
        }
        int ii = ItemUtil.getItemAmount(Material.GOLDEN_APPLE, p, (short) 0);
        if (ii > Config.LIMIT_REFILE) {
            ItemStack item = new ItemStack(Material.GOLDEN_APPLE, (ii - Config.LIMIT_REFILE), (short) 0);
            int added = ItemUtil.remove(item, p, Config.LIMIT_REFILE);
            u.addRefile(added);
            p.sendMessage("&6Posiadasz przy sobie wiecej niz &c" + Config.LIMIT_REFILE + " &6refile! &7(&c" + added + " &6refile zostaja odlozone do twojego schowka&7)");
        }
        if (is.getDurability() == 1) {
            e.setCancelled(true);
            p.getInventory().removeItem(new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1));

            p.removePotionEffect(PotionEffectType.ABSORPTION);
            p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2410, 1));

            p.removePotionEffect(PotionEffectType.REGENERATION);
            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 15, 4));

            u.addkoxEaten(1);

        } else {
            p.removePotionEffect(PotionEffectType.REGENERATION);
            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 3, 2));

            p.removePotionEffect(PotionEffectType.ABSORPTION);
            p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2410, 1));
            u.addrefilEaten(1);

        }
    }
    private List<Player> getPlayersInRadius(Location location, int size) {
        List<Player> players = new ArrayList<>();
        for (Player p : location.getWorld().getPlayers()) {
            if (location.distance(p.getLocation()) <= size) {
                players.add(p);
            }
        }
        return players;
    }

    private void randomTP(Player player) {
        int x = RandomUtil.getRandInt(-Config.BORDER_WORLD, Config.BORDER_WORLD);
        int z = RandomUtil.getRandInt(-Config.BORDER_WORLD, Config.BORDER_WORLD);
        double y = player.getWorld().getHighestBlockYAt(x,z) + 1.5f;
        Location location = new Location(player.getWorld(), x,y,z);
        player.teleport(location);
        GuildManager.getGuild(location);
    }
}