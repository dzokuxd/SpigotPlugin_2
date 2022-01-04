package pl.spigotplugin.listeners;

import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Button;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import pl.spigotplugin.configs.statues;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.holder.LocationHolder;
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.objects.guild.GuildWar;
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
        if (e.getClickedBlock() != null) {
            if (clickedBlock.getType() == Material.SPONGE) {
                clickedBlock.setType(Material.AIR);
                ItemStack randomDrop = BossUtil.drops.get(RandomUtil.getRandInteger(0, BossUtil.drops.size() - 1));
                ItemUtil.giveItems(p, randomDrop);
                Bukkit.broadcastMessage("&aGracz " + p.getName() + " otworzyl magiczny drop i otrzymal " + randomDrop.getType() + "x" + randomDrop.getAmount());
                clickedBlock.getWorld().playEffect(clickedBlock.getLocation(), Effect.EXPLOSION_HUGE, 10);
                p.setVelocity(new Vector(0,30,0));
                return;
            }
        }
        if (p.getItemInHand().isSimilar(VoucherUtil.vip)) {
            if (!GroupUtil.have(p, RankType.VIP)) {
                p.sendMessage(ChatUtil.color("&cPosiadasz ta lub lepsza range!"));
                return;
            }
            Bukkit.broadcastMessage(ChatUtil.color("&aGracz " + p.getName() + " aktywowal Voucher na range VIP!"));
            ItemUtil.removeItems(p, VoucherUtil.vip);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user" + p.getName() + " group set vip");
            return;
        }
        if (p.getItemInHand().isSimilar(VoucherUtil.svip)) {
            if (!GroupUtil.have(p, RankType.SVIP)) {
                p.sendMessage(ChatUtil.color("&cPosiadasz ta lub lepsza range!"));
                return;
            }
            Bukkit.broadcastMessage(ChatUtil.color("Gracz " + p.getName() + " aktywowal Voucher na range SVIP!"));
            ItemUtil.removeItems(p, VoucherUtil.svip);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user" + p.getName() + " group set svip");
            return;
        }
        if (u == null) {
            return;
        }
        if (p.getItemInHand().isSimilar(VoucherUtil.turbo)) {
            Bukkit.broadcastMessage(ChatUtil.color("Gracz " + p.getName() + " aktywowal Voucher na TURBODROP 10M!"));
            ItemUtil.removeItems(p, VoucherUtil.turbo);
            long turboDropHave = 0L;
            long currentTurboDrop = u.getTurboDrop();
            if (currentTurboDrop > System.currentTimeMillis()) {
                turboDropHave = currentTurboDrop - System.currentTimeMillis();
            }
            long givenTurboDrop = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10);
            u.setTurboDrop(givenTurboDrop + turboDropHave);
            return;
        }
        if (clickedBlock != null) {
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
            World gtp = Bukkit.getWorld("gtp");
            for (Player players : this.getPlayersInRadius(clickedBlock.getLocation(), 3)) {
                ++i;
                Location location = new Location(gtp, x, gtp.getHighestBlockYAt(x, z), z);
                if (i > 2) continue;
                p.teleport(location);
                Location ploc = p.getLocation().clone();
                ploc.setY(p.getLocation().getY());
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
        if (strzalyw > statues.LIMIT_STRZAL) {
            ItemStack item = new ItemStack(Material.ARROW, (strzalyw - statues.LIMIT_STRZAL), (short) 0);
            int added = ItemUtil.remove(item, p, statues.LIMIT_STRZAL);
            u.addStrzaly(added);
            u.addarrowsShoten(1);
            p.sendMessage(ChatUtil.color("&cPosiadasz przy sobie wiecej niz " + statues.LIMIT_STRZAL + " strzal! (" + added + " strzaly zostaja odlozone do twojego schowka)"));
        }
        if (s.getType() == Material.ENDER_PEARL) {
            int pearl = ItemUtil.getItemAmount(Material.ENDER_PEARL, p, (short) 0);
            if (pearl > statues.LIMIT_PEARL) {
                ItemStack item = new ItemStack(Material.ENDER_PEARL, (pearl - statues.LIMIT_PEARL), (short) 0);
                int added = ItemUtil.remove(item, p, statues.LIMIT_PEARL);
                u.addPerly(added);
                u.addpearlThrown(1);
                p.sendMessage(ChatUtil.color("&cPosiadasz przy sobie wiecej niz " + statues.LIMIT_PEARL + " perel! (" + added + " perly zostaja odlozone do twojego schowka)"));
            }
        }
        if (p.getItemInHand().isSimilar(new ItemStack(Material.PUMPKIN_PIE))) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 4, 3));
            p.playSound(p.getLocation(), Sound.EAT, 2.0f, 0.8f);
            e.setCancelled(true);
            if (e.getPlayer().getItemInHand().getAmount() > 1) {
                e.getPlayer().getItemInHand().setAmount(e.getPlayer().getItemInHand().getAmount() - 1);
            } else {
                e.getPlayer().setItemInHand(null);
            }
        }
        if (e.getMaterial().equals(Material.NAME_TAG)) {
            if (s.getItemMeta() != null && s.getItemMeta().getDisplayName() != null && e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.color("&6&lAnty Nogi"))) {
                Guild g = GuildManager.getGuild(p);
                if (g == null) {
                    p.sendMessage(ChatUtil.color("&cNie posiadasz gildi!"));
                    return;
                }
                Guild gcub = GuildManager.getGuild(p.getLocation());
                if (gcub == null) {
                    p.sendMessage(ChatUtil.color("&cNie ma tutaj zadnego cuboida!"));
                    return;
                }
                if (gcub.getTag().equalsIgnoreCase(g.getTag())) {
                    p.sendMessage(ChatUtil.color("&cNie mozesz tego zrobic na swoim cuboidzie!"));
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
                    p.sendMessage(ChatUtil.color("&cAby uwolnic kolege z nozek musisz stac od niego 20 kratek!"));
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
        ItemStack k = e.getPlayer().getItemInHand();
        if (k.getType().equals(Material.DIAMOND_PICKAXE) && k.getDurability() == 1559) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,100, 100));
            p.sendMessage(ChatUtil.color("&cTwoj kilof ma jedno uzycie, napraw go :)"));
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
            p.sendMessage(ChatUtil.color("&cOdczekaj chwile przed nastepnym uzyciem!"));
            e.setCancelled(true);
            return;
        }
        PlayerInteractListener.leaver.put(p.getUniqueId(), System.currentTimeMillis());
    }
    @EventHandler
    public void onEntityCreatePortal(EntityCreatePortalEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void NetherPortal(PlayerPortalEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {
            event.setCancelled(true);
            Player p = event.getPlayer();
            User user = UserManager.getUser(p);
            Guild g = GuildManager.getGuild(p);
            World end = Bukkit.getWorld("end");
            if (user.getGuild().isEmpty()) {
                p.sendMessage(ChatUtil.color("&cNie posiadasz gildii!"));
                return;
            }
            if (g == null) {
                return;
            }
            p.teleport(new Location(end, 0, 66, 0));
        }
    }
    @EventHandler
    public void EndPortal(PlayerPortalEvent event) {
        Player p = event.getPlayer();
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL) {
            event.setCancelled(true);
            if (p.getWorld().getName().equals("world")) {
                Location location = p.getLocation();
                Guild g = GuildManager.getGuild(p);
                User user = UserManager.getUser(p);
                if (user.getGuild().isEmpty()) {
                    p.sendMessage(ChatUtil.color("&cNie posiadasz gildii!"));
                    p.teleport(LocationHolder.SPAWN);
                    p.playSound(location, Sound.VILLAGER_NO, 1f, 1f);
                    return;
                }
                if (g == null) {
                    return;
                }
                p.playSound(location, Sound.VILLAGER_YES, 1f, 1f);
                p.teleport(g.getHome());
                return;
            }
            if (p.getWorld().getName().equals("end")) {
                World world = Bukkit.getWorld("world");
                p.teleport(new Location(world, 150, 65, 0));
            }
        }
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
            Long guildclick = PlayerInteractListener.guildclick.get(clickedPlayer.getUniqueId());
            if (guildclick != null && System.currentTimeMillis() - guildclick < 1000L) {
                event.setCancelled(true);
                return;
            }
            PlayerInteractListener.guildclick.put(clickedPlayer.getUniqueId(), System.currentTimeMillis());
            int loseRank = plusRank / 7 * 3;
            event.getPlayer().sendMessage(ChatUtil.color("&fZa zabicie tego gracza otrzymasz &a" +plusRank+ " &fa stracisz &c" +loseRank));
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
            p.sendMessage(ChatUtil.color("&cMusisz posiadac gildie aby podbic inna gildie!"));
            return;
        }
        if (g.isMember(p.getName())) {
            return;
        }
        if (g.getAlly().contains(gg.getTag())) {
            p.sendMessage(ChatUtil.color("&cNie mozesz atakowac sojuszy!"));
            return;
        }
        if (!gg.hasWar(g.getTag())) {
            p.sendMessage(ChatUtil.color("&cZeby podbic gildie musisz miec z nimi wojne! /g wojna " + g.getTag()));
            return;
        }
        /*if (g.getHpLastAttack() > System.currentTimeMillis()) {
            p.sendMessage(ChatUtil.color("&cGildie mozesz podbic za " + DataUtil.secondsToString(g.getHpLastAttack())));
            return;
        }*/
        /*if (!TNTUtil.isBetween()){
            p.sendMessage(ChatUtil.color("&cGildie mozna podbic tylko gdy tnt jest wlaczone!"));
            return;
        }*/
        if (p.getLocation().distance(g.getRegion().getLocation()) > 3.0) {
            p.sendMessage(ChatUtil.color("&cMusisz byc blizej jajka gildii!"));
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
            g.message(ChatUtil.color("&cTwoja gildia jest atakowana przez: "+gg.getTag()+ " pozostale hp to: "+g.getHp()));
            gg.message(ChatUtil.color("&cPozostale hp gildii "+g.getTag()+ " to " +g.getHp()));
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 10, 3));
            if (RandomUtil.getChance(35.0)) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 10, 2));
                return;
            }
            if (RandomUtil.getChance(50.0)) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 10, 2));
                return;
            }
            if (RandomUtil.getChance(50.0)) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 10, 2));
                return;
            }
            if (RandomUtil.getChance(40.0)) {
                p.setVelocity(new Vector(5,0,5));
                return;
            }
            return;
        }
        if (g.getLife() == 1) {
            gg.getWars().remove(new GuildWar(g.getTag(), null));
            g.getWars().remove(new GuildWar(gg.getTag(), null));
            Bukkit.broadcastMessage(ChatUtil.color("&7[&c" + g.getTag() + "&7] [&c-100&7]&c " + g.getName() + " &6zostala zniszczona przez &7[&c" + gg.getTag() + "&7] [&c+100&7]&c " + p.getName()));
            ChatUtil.sendTitleMessage(p, "&c&lWOJNY", "&c"+gg.getTag()+" +100 &6Wygrala wojne z gildia&c "+g.getTag()+" -100", 30, 70, 40);
            p.sendMessage(ChatUtil.color("&aZa podbicie gildii otrzymales x8 Skrzyn " + statues.IP));
            DajUtil.giveWithAmount("easycase", 8, p);
            GuildManager.deleteGuild(g);
        }
        else {
            g.setLife(g.getLife() - 1);
            g.setHp(50);
            gg.getWars().remove(new GuildWar(g.getTag(), null));
            g.getWars().remove(new GuildWar(gg.getTag(), null));
            g.setHpLastAttack(System.currentTimeMillis() + TimeUtil.HOUR.getTime(12));
            Bukkit.broadcastMessage(ChatUtil.color("&7[&c" + g.getTag() + "&7] [&c-100&7]&c " + g.getName() + " &6zostala podbita przez &7[&c" + gg.getTag() + "&7] [&c+100&7]&c " + p.getName()));
            Bukkit.broadcastMessage(ChatUtil.color("&6Zostalo jej &c" + g.getLife() + " &6zyc"));
            ChatUtil.sendTitleMessage(p, "&c&lWOJNY", "&c"+gg.getTag()+" +100 &6Wygrala wojne z gildia&c "+g.getTag()+" -100", 30, 70, 40);
            p.sendMessage(ChatUtil.color("&aZa podbicie gildii otrzymales x8 Skrzyn " + statues.IP));
            DajUtil.giveWithAmount("easycase", 8, p);
        }
        gg.setLife(gg.getLife() +1);
        gg.putForSave();
        g.putForSave();
    }

    @EventHandler
    public void sing(SignChangeEvent e) {
        if (!e.getPlayer().hasPermission("spigot.sign")) {
            return;
        }
        for (int i = 0; i <= 3; ++i) {
            e.setLine(i, ChatUtil.color(e.getLine(i)));
        }
    }

    @EventHandler
    private void clickInv(final InventoryClickEvent e) {
        final Player p = (Player) e.getWhoClicked();
        if (e.isCancelled()) {
            return;
        }
        if (e.getInventory().getName().contains("Ender Chest") && !GroupUtil.have(p, RankType.VIP) && e.getSlot() >= 27 && e.getSlot() <= 44) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
        }
    }
    @EventHandler
    private void onInventoryCloseEvent(final InventoryCloseEvent e) {
        final Player p = (Player)e.getPlayer();
        if (e.getInventory().getName().contains("Ender Chest: ")) {
            final String name = e.getInventory().getName().substring(13);
            if (e.getInventory().getSize() < 36) {
                return;
            }
            final ItemBuilder szklo = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short)14).setTitle(ChatUtil.color("&cDOSTEPNE DLA RANG PREMIUM"));
            e.getInventory().remove(szklo.build());
            p.playSound(e.getPlayer().getLocation(), Sound.CHEST_CLOSE, 1.0f, 1.0f);
            EnderChestUtil.close(p, e.getInventory());
        }
        else {
            if (!e.getInventory().getName().contains("Ender Chest")) {
                return;
            }
            if (e.getInventory().getSize() < 36) {
                return;
            }
            p.playSound(e.getPlayer().getLocation(), Sound.CHEST_CLOSE, 1.0f, 1.0f);
            final ItemBuilder szklo2 = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short)14).setTitle(ChatUtil.color("&cDOSTEPNE DLA RANG PREMIUM"));
            e.getInventory().remove(szklo2.build());
            EnderChestUtil.close(p, e.getInventory());
        }
    }
    @EventHandler
    public void lagFix(final PlayerInteractEvent e) {
        final Block clicked = e.getClickedBlock();
        final Player p = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (clicked.getType() == Material.ENDER_CHEST) {
                e.setCancelled(true);
                if (p.isSneaking()) {
                    return;
                }
                p.getWorld().playSound(e.getPlayer().getLocation(), Sound.CHEST_OPEN, 1.0f, 1.0f);
                if (!GroupUtil.have(p, RankType.VIP)) {
                    EnderChestUtil.open(p);
                } else {
                    EnderChestUtil.openGracz(p);
                }
            }
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
            p.sendMessage(ChatUtil.color( "&cCraftowanie wagonikow jest wylaczone!"));
            e.setCancelled(true);
            e.setCurrentItem(null);
            e.setResult(Event.Result.DENY);
            return;
        }
        if (item.getType() == Material.FISHING_ROD) {
            p.sendMessage(ChatUtil.color( "&cCraftowanie wedek jest wylaczone!"));
            e.setCancelled(true);
            e.setCurrentItem(null);
            e.setResult(Event.Result.DENY);
            return;
        }
        if (!statues.MANAGE_DIAMOND && (item.getType() == Material.DIAMOND_HELMET || item.getType() == Material.DIAMOND_CHESTPLATE || item.getType() == Material.DIAMOND_LEGGINGS || item.getType() == Material.DIAMOND_BOOTS || item.getType() == Material.DIAMOND_SWORD)) {
            p.sendMessage(ChatUtil.color("&cCraftowanie diamentowych itemow jest wylaczone!"));
            e.setCancelled(true);
            e.setCurrentItem(null);
            e.setResult(Event.Result.DENY);
        }
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
        if (i > statues.LIMIT_KOX) {
            ItemStack item = new ItemStack(Material.GOLDEN_APPLE, (i - statues.LIMIT_KOX), (short) 1);
            int added = ItemUtil.remove(item, p, statues.LIMIT_KOX);
            u.addKoxy(added);
            p.sendMessage(ChatUtil.color("&cPosiadasz przy sobie wiecej niz " + statues.LIMIT_KOX + " koxy! (" + added + " koxy zostaja odlozone do twojego schowka)"));
        }
        int ii = ItemUtil.getItemAmount(Material.GOLDEN_APPLE, p, (short) 0);
        if (ii > statues.LIMIT_REFILE) {
            ItemStack item = new ItemStack(Material.GOLDEN_APPLE, (ii - statues.LIMIT_REFILE), (short) 0);
            int added = ItemUtil.remove(item, p, statues.LIMIT_REFILE);
            u.addRefile(added);
            p.sendMessage(ChatUtil.color("&cPosiadasz przy sobie wiecej niz " + statues.LIMIT_REFILE + " refile! (" + added + " refile zostaja odlozone do twojego schowka)"));
        }
        if (is.getDurability() == 1) {
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
        int x = RandomUtil.getRandInt(-statues.BORDER_WORLD, statues.BORDER_WORLD);
        int z = RandomUtil.getRandInt(-statues.BORDER_WORLD, statues.BORDER_WORLD);
        double y = player.getWorld().getHighestBlockYAt(x,z);
        Location location = new Location(player.getWorld(), x,y,z);
        Biome biome = location.getBlock().getBiome();
        if (biome == Biome.OCEAN || biome == Biome.DEEP_OCEAN || GuildManager.getGuild(location) != null) {
            player.sendMessage(ChatUtil.color("&cTrafiles na gildie lub ocean!"));
            return;
        }
        player.teleport(location);
    }
}