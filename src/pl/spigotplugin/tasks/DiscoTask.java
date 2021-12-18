package pl.spigotplugin.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;
import pl.spigotplugin.enums.ArmorType;
import pl.spigotplugin.managers.DataManager;
import pl.spigotplugin.utils.ColorUtils;
import pl.spigotplugin.utils.PacketEquipment;

public class DiscoTask extends BukkitRunnable
{
    public void run() {
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getOnlinePlayers().toArray(new Player[0])).length, k = 0; k < length; ++k) {
            Player player = onlinePlayers[k];
            ArmorType armor = DataManager.getDisco().get(player.getName());
            if (armor != null) {
                switch (armor) {
                    case RANDOM: {
                        Color color = ColorUtils.randomColor();
                        for (int i = 1; i < 5; ++i) {
                            ItemStack item = new ItemStack(Material.getMaterial(297 + i), 1);
                            LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();
                            meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
                            meta.setColor(color);
                            item.setItemMeta(meta);
                            Player[] onlinePlayers2;
                            for (int length2 = (onlinePlayers2 = Bukkit.getOnlinePlayers().toArray(new Player[0])).length, l = 0; l < length2; ++l) {
                                Player p = onlinePlayers2[l];
                                if (!p.getName().equals(player.getName())) {
                                    PacketEquipment.sendEquipment(p, player.getEntityId(), i, item);
                                }
                            }
                        }
                        if (player.isSneaking() && DataManager.getShiftArmor().containsKey(player.getName())) {
                            for (int i = 0; i < 4; ++i) {
                                ItemStack item = new ItemStack(Material.getMaterial(298 + i), 1);
                                LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();
                                meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
                                meta.setColor(color);
                                item.setItemMeta(meta);
                                player.getInventory().setItem(36 + i, item);
                            }
                            break;
                        }
                        break;
                    }
                    case ULTRA: {
                        for (int j = 1; j < 5; ++j) {
                            ItemStack item2 = new ItemStack(Material.getMaterial(297 + j), 1);
                            LeatherArmorMeta meta2 = (LeatherArmorMeta)item2.getItemMeta();
                            meta2.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
                            meta2.setColor(ColorUtils.randomColor());
                            item2.setItemMeta(meta2);
                            Player[] onlinePlayers3;
                            for (int length3 = (onlinePlayers3 = Bukkit.getOnlinePlayers().toArray(new Player[0])).length, n = 0; n < length3; ++n) {
                                Player p2 = onlinePlayers3[n];
                                if (!p2.getName().equals(player.getName())) {
                                    PacketEquipment.sendEquipment(p2, player.getEntityId(), j, item2);
                                }
                            }
                        }
                        if (player.isSneaking() && DataManager.getShiftArmor().containsKey(player.getName())) {
                            for (int j = 0; j < 4; ++j) {
                                ItemStack item2 = new ItemStack(Material.getMaterial(298 + j), 1);
                                LeatherArmorMeta meta2 = (LeatherArmorMeta)item2.getItemMeta();
                                meta2.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
                                meta2.setColor(ColorUtils.randomColor());
                                item2.setItemMeta(meta2);
                                player.getInventory().setItem(36 + j, item2);
                            }
                            break;
                        }
                        break;
                    }
                    case SMOOTH: {
                        Color color = ColorUtils.nextColor(DataManager.getLastColor().get(player.getName()));
                        DataManager.getLastColor().put(player.getName(), color);
                        for (int i = 1; i < 5; ++i) {
                            ItemStack item = new ItemStack(Material.getMaterial(297 + i), 1);
                            LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();
                            meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
                            meta.setColor(color);
                            item.setItemMeta(meta);
                            Player[] onlinePlayers4;
                            for (int length4 = (onlinePlayers4 = Bukkit.getOnlinePlayers().toArray(new Player[0])).length, n2 = 0; n2 < length4; ++n2) {
                                Player p = onlinePlayers4[n2];
                                if (!p.getName().equals(player.getName())) {
                                    PacketEquipment.sendEquipment(p, player.getEntityId(), i, item);
                                }
                            }
                        }
                        if (player.isSneaking() && DataManager.getShiftArmor().containsKey(player.getName())) {
                            for (int i = 0; i < 4; ++i) {
                                ItemStack item = new ItemStack(Material.getMaterial(298 + i), 1);
                                LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();
                                meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
                                meta.setColor(color);
                                item.setItemMeta(meta);
                                player.getInventory().setItem(36 + i, item);
                            }
                            break;
                        }
                        break;
                    }
                    case GRAY: {
                        Color color = ColorUtils.nextColor(DataManager.getLastColor().get(player.getName()));
                        DataManager.getLastColor().put(player.getName(), color);
                        color = Color.fromRGB(color.getRed(), color.getRed(), color.getRed());
                        for (int i = 1; i < 5; ++i) {
                            ItemStack item = new ItemStack(Material.getMaterial(297 + i), 1);
                            LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();
                            meta.setColor(color);
                            item.setItemMeta(meta);
                            Player[] onlinePlayers5;
                            for (int length5 = (onlinePlayers5 = Bukkit.getOnlinePlayers().toArray(new Player[0])).length, n3 = 0; n3 < length5; ++n3) {
                                Player p = onlinePlayers5[n3];
                                if (!p.getName().equals(player.getName())) {
                                    PacketEquipment.sendEquipment(p, player.getEntityId(), i, item);
                                }
                            }
                        }
                        if (player.isSneaking() && DataManager.getShiftArmor().containsKey(player.getName())) {
                            for (int i = 0; i < 4; ++i) {
                                ItemStack item = new ItemStack(Material.getMaterial(298 + i), 1);
                                LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();
                                meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
                                meta.setColor(color);
                                item.setItemMeta(meta);
                                player.getInventory().setItem(36 + i, item);
                            }
                            break;
                        }
                        break;
                    }
                }
            }
        }
    }
}
