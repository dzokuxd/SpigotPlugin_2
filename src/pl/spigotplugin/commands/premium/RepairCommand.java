package pl.spigotplugin.commands.premium;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.GroupUtil;

public class RepairCommand extends PlayerCommand {
    public RepairCommand() { super("repair", RankType.VIP); }

    @Override
    public void onCommand(Player p, String[] args) {
        ItemStack is = p.getItemInHand();
        if (is.getType().isBlock() || is.getType() == Material.AIR || is.getType() == Material.GOLDEN_APPLE) {
            p.sendMessage(ChatUtil.color("&cTego przedmiotu nie mozesz naprawic!"));
            return;
        }
        if (args.length == 1) {
            if (GroupUtil.have(p, RankType.SVIP)) {
                if (args[0].equalsIgnoreCase("all")){
                    if (is.getDurability() == 0) {
                        p.sendMessage(ChatUtil.color("&cWszystkie przedmioty sa naprawione!"));
                        return;
                    }
                    ItemStack[] contents;
                    for (int length = (contents = p.getInventory().getContents()).length, i = 0; i < length; ++i) {
                        ItemStack itemStack = contents[i];
                        if (itemStack != null) {
                            itemStack.setDurability((short) 0);
                        }
                    }
                    ItemStack[] armorContents;
                    for (int length2 = (armorContents = p.getEquipment().getArmorContents()).length, j = 0; j < length2; ++j){
                        ItemStack itemStack2 = armorContents[j];
                        if (itemStack2 != null) {
                            itemStack2.setDurability((short) 0);
                        }
                    }
                    p.playSound(p.getLocation(), Sound.ANVIL_USE, 5.0f, 3.0f);
                    p.sendMessage(ChatUtil.color("&aNaprawiono wszystkie przedmioty!"));
                }
            }
        }
        if (args.length == 0) {
            p.sendMessage(is.getDurability()+"");
            if (is.getDurability() == 0) {
                p.sendMessage(ChatUtil.color("&cTen przedmiot jest naprawiony!"));
                return;
            }
            is.setDurability((short) 0);
            p.playSound(p.getLocation(), Sound.ANVIL_USE, 5.0f, 3.0f);
            p.sendMessage(ChatUtil.color("&fNaprawiles przedmiot &d" + (is.getType())));
        }
    }
}
