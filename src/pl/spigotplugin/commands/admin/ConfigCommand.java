package pl.spigotplugin.commands.admin;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutCustomPayload;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import pl.spigotplugin.api.Command;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.configs.DropFile;
import pl.spigotplugin.configs.GlobalMessage;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.ItemUtil;

import java.util.ArrayList;

public class ConfigCommand extends Command {
    public ConfigCommand() { super("config", "config <reload/book>", "", "cfg");
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            GlobalMessage.usage(sender, getUsage());
            return;
        }
        switch (args[0]) {
            case "book": {
                ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
                BookMeta bookMeta = (BookMeta)book.getItemMeta();
                int uamount = UserManager.getUsers1().size();
                /*int gamount = GuildManager.getguilds().size();*/
                bookMeta.setAuthor("dzokv");
                bookMeta.setTitle(ChatUtil.color("&7&lSpigotPlugin"));
                ArrayList<String> pages = new ArrayList<>();
                pages.add(ChatUtil.color("&6Statystyki\n &8» &6Gracze: &c" + uamount + "\n &8» &6Gildie: &c" + uamount));
                bookMeta.setPages(pages);
                book.setItemMeta(bookMeta);
                this.openBook(book, (Player) sender);
                return;
            }//TODO widac
            case "reload": {
                Config.reloadConfig();
                GlobalMessage.reloadLang();
                DropFile.reloadConfig();
                sender.sendMessage("&8\u00bb &aConfig save!");
                return;
            }
            default: {
                sender.sendMessage(GlobalMessage.USAGE);
            }
        }
    }
    public void openBook(ItemStack book, Player p) {
        int slot = p.getInventory().getHeldItemSlot();
        ItemStack old = p.getInventory().getItem(slot);
        p.getInventory().setItem(slot, book);
        ByteBuf buf = Unpooled.buffer(256);
        buf.setByte(0, 0);
        buf.writerIndex(1);
        PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload("MC|BOpen", new PacketDataSerializer(buf));
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
        p.getInventory().setItem(slot, old);
    }
}
