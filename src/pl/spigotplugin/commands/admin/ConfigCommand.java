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
import pl.spigotplugin.configs.statues;
import pl.spigotplugin.configs.DropFile;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.configs.guild;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.managers.BanManager;
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.managers.MuteManager;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.utils.ChatUtil;

import java.util.ArrayList;

public class ConfigCommand extends Command {
    public ConfigCommand() { super("config", RankType.PREZES, "cfg");
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            core.usage(sender, "config <reload/book>");
            return;
        }
        switch (args[0]) {
            case "book": {
                ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
                BookMeta bookMeta = (BookMeta)book.getItemMeta();
                int uamount = UserManager.getUsers1().size();
                int gamount = GuildManager.getGuilds().size();
                int bamount = BanManager.getBans().size();
                int mamount = MuteManager.getMutes().size();
                bookMeta.setAuthor("dzokv");
                bookMeta.setTitle(ChatUtil.color("&d&lSpigotPlugin"));
                ArrayList<String> pages = new ArrayList<>();
                pages.add(ChatUtil.color("&6Statystyki" +
                        "\n &8» &fGracze: &d" + uamount +
                        "\n &8» &fGildie: &d" + gamount+
                        "\n &8» &fBany: &d"+bamount+
                        "\n &8» &fMuty: &d"+mamount+
                        "\n &8» &fStoniarki: &d"+
                        "\n \n&dZlimitowane bloki" +
                        "\n &8» &fEnchanty: &d"+
                        "\n &8» &fPistony: &d"+
                        "\n &8» &fPlytki naciskowe: &d"+
                        "\n &8» &fCraftingi: &d"+
                        "\n &8» &fGuziki: &d"+
                        "\n &8» &fPiece: &d"));
                bookMeta.setPages(pages);
                book.setItemMeta(bookMeta);
                this.openBook(book, (Player) sender);
                return;
            }
            case "reload": {
                statues.reloadLang();
                core.reloadLang();
                DropFile.reloadConfig();
                guild.reloadLang();
                sender.sendMessage("&8\u00bb &aConfig save!");
                return;
            }
            default: {
                sender.sendMessage(core.USAGE);
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
