package pl.spigotplugin.objects.drop;

import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.NetherWartsState;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.CocoaPlant;
import org.bukkit.material.Crops;
import org.bukkit.material.NetherWarts;
import pl.spigotplugin.utils.DropUtil;
import pl.spigotplugin.utils.RandomUtil;

import java.util.ArrayList;
import java.util.List;

public class NormalDropData implements DropData{
    public void breakBlock(Block block, Player player, ItemStack is) {
        List<ItemStack> drops = this.getDrops(block, is);
        DropUtil.addItemsToPlayer(player, drops, block);
        DropUtil.recalculateDurability(player, is);
        block.setType(Material.AIR);
    }

    public List<ItemStack> getDrops(Block block, ItemStack item) {
        List<ItemStack> items = new ArrayList<ItemStack>();
        Material type = block.getType();
        int amount = 1;
        short data = block.getData();
        switch (type) {
            case NETHER_WARTS: {
                NetherWarts warts = (NetherWarts) block.getState().getData();
                amount = (warts.getState().equals(NetherWartsState.RIPE) ? (RandomUtil.getRandInt(0, 2) + 2) : 1);
                items.add(new ItemStack(Material.NETHER_STALK, amount));
                break;
            }
            case COCOA: {
                CocoaPlant plant = (CocoaPlant) block.getState().getData();
                amount = (plant.getSize().equals(CocoaPlant.CocoaPlantSize.LARGE) ? 3 : 1);
                items.add(new ItemStack(Material.INK_SACK, amount, (short) 3));
                break;
            }
            case PUMPKIN_STEM: {
                items.add(new ItemStack(Material.PUMPKIN_SEEDS, 1));
                break;
            }
            case MELON_STEM: {
                items.add(new ItemStack(Material.MELON_SEEDS, 1));
                break;
            }
            case CARROT: {
                data = block.getState().getData().getData();
                switch (data) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6: {
                        amount = 1;
                        break;
                    }
                    case 7: {
                        amount = RandomUtil.getRandInt(1, 3);
                        break;
                    }
                }
                items.add(new ItemStack(Material.CARROT_ITEM, amount));
                break;
            }
            case POTATO: {
                data = block.getState().getData().getData();
                switch (data) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6: {
                        amount = 1;
                        break;
                    }
                    case 7: {
                        if (RandomUtil.getChance(15)) {
                            items.add(new ItemStack(Material.POISONOUS_POTATO, 1));
                            break;
                        }
                        amount = RandomUtil.getRandInt(1, 3);
                        break;
                    }
                }
                items.add(new ItemStack(Material.POTATO_ITEM, amount));
                break;
            }
            case CROPS: {
                Crops wheat = (Crops) block.getState().getData();
                int seedamount = 1;
                if (wheat.getState() == CropState.RIPE) {
                    items.add(new ItemStack(Material.WHEAT, RandomUtil.getRandInt(1, 2)));
                    seedamount = 1 + RandomUtil.getRandInt(0, 2);
                }
                items.add(new ItemStack(Material.SEEDS, seedamount));
                break;
            }
            case SUGAR_CANE_BLOCK: {
                amount = 1;
                items.add(new ItemStack(Material.SUGAR_CANE, amount));
                break;
            }
            case DOUBLE_PLANT:
            case REDSTONE_WIRE:
            case WOODEN_DOOR:
            case IRON_DOOR:
            case TRIPWIRE:
            case LEVER:
            case WOOD_BUTTON:
            case STONE_BUTTON:
            case DIODE_BLOCK_ON:
            case DIODE_BLOCK_OFF:
            case REDSTONE_COMPARATOR_OFF:
            case REDSTONE_COMPARATOR_ON:
            case DAYLIGHT_DETECTOR:
            case REDSTONE_ORE: {
                items.addAll(block.getDrops(item));
                break;
            }
            default: {
                if (item.containsEnchantment(Enchantment.SILK_TOUCH) && block.getType().isBlock()) {
                    items.add(new ItemStack(block.getType(), 1, block.getData()));
                    break;
                }
                items.addAll(block.getDrops(item));
                break;
            }
        }
        return items;
    }

    public DropType getDropType() {
        return DropType.NORMAL_DROP;
    }
}
